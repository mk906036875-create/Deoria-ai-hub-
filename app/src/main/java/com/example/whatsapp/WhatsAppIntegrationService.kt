package com.example.whatsapp

import android.util.Log
import com.example.util.AiLeadAssistant
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.util.UUID

object WhatsAppIntegrationService {

    private const val TAG = "WhatsAppIntegration"

    private val _configState = MutableStateFlow(WhatsAppPlatformConfig())
    val configState: StateFlow<WhatsAppPlatformConfig> = _configState.asStateFlow()

    private val _conversationHistory = MutableStateFlow<List<WhatsAppConversationEntry>>(emptyList())
    val conversationHistory: StateFlow<List<WhatsAppConversationEntry>> = _conversationHistory.asStateFlow()

    /**
     * Webhook Verification Handshake
     * Complies with Meta WhatsApp Business Platform webhook verification protocol.
     */
    fun verifyWebhook(request: WebhookVerificationRequest, configuredVerifyToken: String?): String? {
        Log.d(TAG, "Incoming Webhook verification challenge received: mode=${request.mode}")
        if (configuredVerifyToken.isNullOrBlank()) {
            Log.w(TAG, "Webhook verification failed: Server verify token is not configured in environment.")
            return null
        }
        return if (request.mode == "subscribe" && request.token == configuredVerifyToken) {
            Log.i(TAG, "Webhook verification handshake successful!")
            request.challenge
        } else {
            Log.w(TAG, "Webhook verification mismatch: Invalid token supplied.")
            null
        }
    }

    /**
     * Complete Message Processing Pipeline:
     * Customer -> WhatsApp webhook -> Save message -> AI assistant -> Generate reply -> WhatsApp API -> Customer receives reply -> Save conversation
     */
    fun processIncomingWebhookMessage(
        message: WhatsAppWebhookMessage
    ): Pair<WhatsAppConversationEntry, WhatsAppConversationEntry?> {
        val currentConfig = _configState.value

        // 1. Record incoming customer message
        val incomingEntry = WhatsAppConversationEntry(
            id = message.id.ifBlank { UUID.randomUUID().toString() },
            senderNumber = message.from,
            senderName = message.senderName.ifBlank { "Customer" },
            messageText = message.text,
            isIncoming = true,
            timestamp = System.currentTimeMillis()
        )

        // 2. Check Human Handoff status
        if (currentConfig.isHumanHandoffActive) {
            Log.i(TAG, "Human handoff active: Diverting message to human agent queue. AI Auto-reply paused.")
            val handoffNotice = WhatsAppConversationEntry(
                id = UUID.randomUUID().toString(),
                senderNumber = message.from,
                senderName = "Deoria AI Hub Support (Human)",
                messageText = "नमस्ते! आपके संदेश को हमारे सपोर्ट एजेंट के पास भेज दिया गया है। हमारे प्रतिनिधि जल्द ही आपसे संपर्क करेंगे।",
                isIncoming = false,
                isHumanHandoff = true
            )
            appendConversation(incomingEntry)
            appendConversation(handoffNotice)
            return Pair(incomingEntry, handoffNotice)
        }

        // 3. AI Assistant Analysis & Reply Generation
        val analysis = AiLeadAssistant.analyzeLead(
            requirement = message.text,
            budget = "Standard",
            business = "Customer Business",
            service = "WhatsApp Automation",
            name = message.senderName
        )

        val updatedIncoming = incomingEntry.copy(aiClassification = analysis.temperature)
        appendConversation(updatedIncoming)

        // 4. Outgoing AI Reply (if AI Auto Reply is enabled)
        var outgoingReply: WhatsAppConversationEntry? = null
        if (currentConfig.isAiAutoReplyEnabled) {
            outgoingReply = WhatsAppConversationEntry(
                id = UUID.randomUUID().toString(),
                senderNumber = message.from,
                senderName = "Deoria AI Hub Bot",
                messageText = analysis.suggestedReply,
                isIncoming = false,
                aiClassification = analysis.temperature,
                isHumanHandoff = false
            )
            appendConversation(outgoingReply)
            Log.i(TAG, "AI Auto-reply generated and routed through WhatsApp API mock pipeline: ${outgoingReply.messageText}")
        }

        return Pair(updatedIncoming, outgoingReply)
    }

    private fun appendConversation(entry: WhatsAppConversationEntry) {
        _conversationHistory.value = _conversationHistory.value + entry
    }

    fun setAiAutoReply(enabled: Boolean) {
        _configState.value = _configState.value.copy(isAiAutoReplyEnabled = enabled)
    }

    fun setHumanHandoff(active: Boolean) {
        _configState.value = _configState.value.copy(isHumanHandoffActive = active)
    }

    fun clearConversationHistory() {
        _conversationHistory.value = emptyList()
    }
}
