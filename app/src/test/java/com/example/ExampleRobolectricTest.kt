package com.example

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.example.data.local.LeadDatabase
import com.example.data.local.LeadEntity
import com.example.model.BusinessConstants
import com.example.model.LeadFormData
import com.example.model.LeadTemperature
import com.example.model.NextAction
import com.example.util.AiLeadAssistant
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import java.io.IOException

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [36])
class ExampleRobolectricTest {

    private lateinit var db: LeadDatabase

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, LeadDatabase::class.java)
            .allowMainThreadQueries()
            .build()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    fun `read string from context`() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        val appName = context.getString(R.string.app_name)
        assertEquals("Deoria AI Hub", appName)
    }

    @Test
    fun `test whatsapp message generation format`() {
        val lead = LeadFormData(
            name = "Ramesh Kumar",
            mobileNumber = "9919900020",
            businessType = "Retail / Shop",
            requestedService = "WhatsApp Chatbot",
            budget = "₹9,999 (Business)",
            requirement = "Customer enquiry auto-reply"
        )
        val msg = BusinessConstants.buildHindiWhatsAppMessage(lead)
        assertTrue(msg.contains("नमस्ते Deoria AI Hub, मेरा नाम Ramesh Kumar है।"))
        assertTrue(msg.contains("मुझे WhatsApp Chatbot की जरूरत है।"))
        assertTrue(msg.contains("मेरा Business Retail / Shop है।"))
        assertTrue(msg.contains("कृपया मुझे जानकारी दें।"))
    }

    @Test
    fun `test user prompt example AI analysis`() {
        // Example: Lead: "Mujhe apne business ke liye WhatsApp chatbot chahiye. Price kya hai?"
        val analysis = AiLeadAssistant.analyzeLead(
            requirement = "Mujhe apne business ke liye WhatsApp chatbot chahiye. Price kya hai?",
            budget = "₹9,999",
            business = "Retail Store",
            service = "WhatsApp Chatbot",
            name = "अमित"
        )

        assertEquals(LeadTemperature.HOT, analysis.temperature)
        assertTrue(analysis.reason.contains("price", ignoreCase = true))
        assertTrue(analysis.suggestedReply.contains("WhatsApp Chatbot") || analysis.suggestedReply.contains("Package"))
        assertEquals(NextAction.SEND_QUOTATION, analysis.nextAction)
    }

    @Test
    fun `test warm and cold lead analysis`() {
        val warmAnalysis = AiLeadAssistant.analyzeLead(
            requirement = "Mujhe apne coaching ke liye chatbot ka demo dekhna hai features kaise hain",
            budget = "₹9,999",
            business = "Coaching",
            service = "WhatsApp Chatbot"
        )
        assertEquals(LeadTemperature.WARM, warmAnalysis.temperature)
        assertEquals(NextAction.SEND_DEMO, warmAnalysis.nextAction)

        val coldAnalysis = AiLeadAssistant.analyzeLead(
            requirement = "Just asking general question kya hota hai",
            budget = "₹4,999",
            business = "Other",
            service = "AI Automation"
        )
        assertEquals(LeadTemperature.COLD, coldAnalysis.temperature)
        assertEquals(NextAction.FOLLOW_UP_LATER, coldAnalysis.nextAction)
    }

    @Test
    fun `test Room database insert update status search and delete`() = runBlocking {
        val leadDao = db.leadDao()
        val leadId = "test_lead_123"
        val entity = LeadEntity(
            id = leadId,
            name = "सुरेश यादव",
            mobile = "9876543210",
            businessType = "Retail / Shop",
            serviceRequired = "WhatsApp Chatbot",
            budget = "₹9,999",
            message = "दुकान के लिए ऑटो-रिप्लाई बॉट चाहिए",
            leadTemperature = "Warm Lead",
            status = "New",
            aiReason = "Customer needs demo",
            nextAction = "Send demo",
            createdAt = System.currentTimeMillis()
        )

        // 1. Insert lead
        leadDao.insertLead(entity)
        val leads = leadDao.getAllLeads().first()
        assertEquals(1, leads.size)
        assertEquals("सुरेश यादव", leads[0].name)
        assertEquals("9876543210", leads[0].mobile)
        assertEquals("New", leads[0].status)
        assertEquals("Send demo", leads[0].nextAction)

        // 2. Update status
        leadDao.updateStatus(leadId, "Contacted")
        val updatedLeads = leadDao.getAllLeads().first()
        assertEquals("Contacted", updatedLeads[0].status)

        // 3. Search by name or mobile
        val searchByName = leadDao.searchLeads("सुरेश").first()
        assertEquals(1, searchByName.size)

        val searchByMobile = leadDao.searchLeads("98765").first()
        assertEquals(1, searchByMobile.size)

        // 4. Delete lead
        leadDao.deleteLeadById(leadId)
        val remainingLeads = leadDao.getAllLeads().first()
        assertEquals(0, remainingLeads.size)
    }

    @Test
    fun `test WhatsApp webhook verification and pipeline`() {
        val request = com.example.whatsapp.WebhookVerificationRequest(
            mode = "subscribe",
            token = "my_secret_verify_token",
            challenge = "challenge_12345"
        )
        val challengeResult = com.example.whatsapp.WhatsAppIntegrationService.verifyWebhook(
            request = request,
            configuredVerifyToken = "my_secret_verify_token"
        )
        assertEquals("challenge_12345", challengeResult)

        // Test incoming webhook message processing
        val webhookMsg = com.example.whatsapp.WhatsAppWebhookMessage(
            from = "919919900020",
            id = "msg_001",
            timestamp = "1720000000",
            text = "Mujhe apne coaching ke liye WhatsApp chatbot chahiye. Price kya hai?",
            senderName = "Vikram"
        )
        val (incoming, outgoing) = com.example.whatsapp.WhatsAppIntegrationService.processIncomingWebhookMessage(webhookMsg)
        assertEquals(LeadTemperature.HOT, incoming.aiClassification)
        assertTrue(outgoing != null)
        assertTrue(outgoing!!.messageText.contains("WhatsApp Chatbot") || outgoing.messageText.contains("Package"))
    }
}
