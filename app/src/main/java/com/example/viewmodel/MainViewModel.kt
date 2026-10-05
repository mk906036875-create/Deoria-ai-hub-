package com.example.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.data.local.LeadDatabase
import com.example.data.local.LeadEntity
import com.example.data.mapper.LeadMapper
import com.example.data.repository.LeadRepository
import com.example.model.BusinessConstants
import com.example.model.ChatMessage
import com.example.model.LeadFormData
import com.example.model.LeadStatus
import com.example.model.LeadTemperature
import com.example.model.ManagedLead
import com.example.model.MessageSender
import com.example.util.AiLeadAssistant
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.UUID

enum class AppSection(val labelEn: String, val labelHi: String) {
    HOME("Home", "होम"),
    SERVICES("Services", "सेवाएं"),
    HOW_IT_WORKS("Process", "कैसे काम करता है"),
    PACKAGES("Packages", "प्लान्स"),
    CHATBOT_DEMO("Demo Bot", "डेमो चैटबॉट"),
    LEAD_FORM("Enquiry", "पूछताछ फॉर्म"),
    LEAD_DASHBOARD("Leads CRM", "लीड्स डैशबोर्ड"),
    WHATSAPP_INTEGRATION("WhatsApp API", "व्हाट्सएप इंटीग्रेशन")
}

data class DashboardStats(
    val totalLeads: Int = 0,
    val newLeads: Int = 0,
    val hotLeads: Int = 0,
    val warmLeads: Int = 0,
    val coldLeads: Int = 0,
    val convertedLeads: Int = 0
)

data class DatabaseInfo(
    val engineName: String = "Room SQLite (Local Persistent Database)",
    val databaseName: String = "deoria_leads.db",
    val tableName: String = "customer_leads",
    val isLocalConnected: Boolean = true,
    val cloudSyncStatus: String = "Not configured (Local storage only)",
    val requiredCloudKeys: List<String> = listOf("CLOUD_DB_ENDPOINT", "CLOUD_DB_API_KEY")
)

data class UiState(
    val currentSection: AppSection = AppSection.HOME,
    val leadForm: LeadFormData = LeadFormData(),
    val isFormSubmitted: Boolean = false,
    val formErrorMessage: String? = null,
    val submittedMessageText: String = "",
    val chatMessages: List<ChatMessage> = emptyList(),
    val isBotTyping: Boolean = false,
    val quickChips: List<String> = listOf(
        "Website बनवानी है",
        "WhatsApp Chatbot चाहिए",
        "AI Automation चाहिए",
        "Price जानना है",
        "Free Demo चाहिए"
    ),
    val leads: List<ManagedLead> = emptyList(),
    val selectedStatusFilter: LeadStatus? = null,
    val selectedTempFilter: LeadTemperature? = null,
    val leadSearchQuery: String = "",
    val dbInfo: DatabaseInfo = DatabaseInfo(),
    val isDatabaseInitialized: Boolean = false
) {
    val stats: DashboardStats
        get() = DashboardStats(
            totalLeads = leads.size,
            newLeads = leads.count { it.status == LeadStatus.NEW },
            hotLeads = leads.count { it.temperature == LeadTemperature.HOT },
            warmLeads = leads.count { it.temperature == LeadTemperature.WARM },
            coldLeads = leads.count { it.temperature == LeadTemperature.COLD },
            convertedLeads = leads.count { it.status == LeadStatus.CONVERTED }
        )

    val filteredLeads: List<ManagedLead>
        get() = leads.filter { lead ->
            val matchesStatus = selectedStatusFilter == null || lead.status == selectedStatusFilter
            val matchesTemp = selectedTempFilter == null || lead.temperature == selectedTempFilter
            val matchesQuery = leadSearchQuery.isBlank() ||
                    lead.name.contains(leadSearchQuery, ignoreCase = true) ||
                    lead.mobile.contains(leadSearchQuery, ignoreCase = true) ||
                    lead.business.contains(leadSearchQuery, ignoreCase = true) ||
                    lead.serviceRequired.contains(leadSearchQuery, ignoreCase = true)
            matchesStatus && matchesTemp && matchesQuery
        }
}

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: LeadRepository
    private val _uiState = MutableStateFlow(UiState())
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    init {
        val db = LeadDatabase.getDatabase(application)
        repository = LeadRepository(db.leadDao())

        initChatMessages()
        observeDatabaseLeads()
    }

    private fun getCurrentTime(): String {
        return SimpleDateFormat("hh:mm a", Locale.getDefault()).format(Date())
    }

    private fun initChatMessages() {
        val welcome = ChatMessage(
            id = UUID.randomUUID().toString(),
            sender = MessageSender.BOT,
            text = "नमस्ते! 🙏 Deoria AI Hub में आपका स्वागत है।\n\nहम आपके बिज़नेस के लिए WhatsApp Chatbot, Business Websites और AI Automation बनाते हैं।\n\nआप नीचे दिए गए विकल्पों में से चुन सकते हैं या अपना सवाल लिख सकते हैं:",
            timestamp = getCurrentTime()
        )
        _uiState.value = _uiState.value.copy(chatMessages = listOf(welcome))
    }

    private fun observeDatabaseLeads() {
        viewModelScope.launch {
            repository.allLeads.collectLatest { entityList ->
                val mapped = entityList.map { LeadMapper.toManagedLead(it) }
                _uiState.value = _uiState.value.copy(
                    leads = mapped,
                    isDatabaseInitialized = true
                )
            }
        }
    }

    fun setSection(section: AppSection) {
        _uiState.value = _uiState.value.copy(currentSection = section)
    }

    fun updateName(name: String) {
        _uiState.value = _uiState.value.copy(
            leadForm = _uiState.value.leadForm.copy(name = name),
            formErrorMessage = null
        )
    }

    fun updateMobile(mobile: String) {
        val filtered = mobile.filter { it.isDigit() }.take(10)
        _uiState.value = _uiState.value.copy(
            leadForm = _uiState.value.leadForm.copy(mobileNumber = filtered),
            formErrorMessage = null
        )
    }

    fun updateBusinessType(type: String) {
        _uiState.value = _uiState.value.copy(
            leadForm = _uiState.value.leadForm.copy(businessType = type)
        )
    }

    fun updateRequestedService(service: String) {
        _uiState.value = _uiState.value.copy(
            leadForm = _uiState.value.leadForm.copy(requestedService = service)
        )
    }

    fun updateBudget(budget: String) {
        _uiState.value = _uiState.value.copy(
            leadForm = _uiState.value.leadForm.copy(budget = budget)
        )
    }

    fun updateRequirement(requirement: String) {
        _uiState.value = _uiState.value.copy(
            leadForm = _uiState.value.leadForm.copy(requirement = requirement)
        )
    }

    fun selectPackageForEnquiry(packageId: String) {
        val pkg = when (packageId) {
            "starter" -> "₹4,999 (Starter)"
            "business" -> "₹9,999 (Business)"
            else -> "₹19,999 (AI Automation)"
        }
        _uiState.value = _uiState.value.copy(
            leadForm = _uiState.value.leadForm.copy(
                requestedService = when (packageId) {
                    "starter" -> "Business Website"
                    "business" -> "WhatsApp Chatbot"
                    else -> "AI Automation"
                },
                budget = pkg
            ),
            currentSection = AppSection.LEAD_FORM,
            isFormSubmitted = false,
            formErrorMessage = null
        )
    }

    fun selectServiceForEnquiry(serviceTitle: String) {
        _uiState.value = _uiState.value.copy(
            leadForm = _uiState.value.leadForm.copy(requestedService = serviceTitle),
            currentSection = AppSection.LEAD_FORM,
            isFormSubmitted = false,
            formErrorMessage = null
        )
    }

    fun submitLeadForm(): Boolean {
        val form = _uiState.value.leadForm
        if (form.name.trim().length < 2) {
            _uiState.value = _uiState.value.copy(formErrorMessage = "कृपया अपना सही नाम दर्ज करें।")
            return false
        }
        if (form.mobileNumber.length < 10) {
            _uiState.value = _uiState.value.copy(formErrorMessage = "कृपया 10 अंकों का सही मोबाइल नंबर दर्ज करें।")
            return false
        }

        val generatedMessage = BusinessConstants.buildHindiWhatsAppMessage(form)
        val analysis = AiLeadAssistant.analyzeLead(
            requirement = form.requirement,
            budget = form.budget,
            business = form.businessType,
            service = form.requestedService,
            name = form.name
        )

        val entity = LeadEntity(
            id = UUID.randomUUID().toString(),
            name = form.name.trim(),
            mobile = form.mobileNumber.trim(),
            businessType = form.businessType,
            serviceRequired = form.requestedService,
            budget = form.budget,
            message = form.requirement.ifBlank { "विस्तृत बातचीत एवं डेमो की आवश्यकता है।" },
            leadTemperature = analysis.temperature.label,
            status = LeadStatus.NEW.label,
            aiReason = analysis.reason,
            nextAction = analysis.nextAction.label,
            createdAt = System.currentTimeMillis()
        )

        // Requirement 1: Save every submitted lead to the persistent Room database
        viewModelScope.launch {
            repository.insertLead(entity)
        }

        _uiState.value = _uiState.value.copy(
            isFormSubmitted = true,
            formErrorMessage = null,
            submittedMessageText = generatedMessage
        )
        return true
    }

    fun resetLeadForm() {
        _uiState.value = _uiState.value.copy(
            isFormSubmitted = false,
            formErrorMessage = null,
            leadForm = LeadFormData()
        )
    }

    // Lead CRM Actions
    fun setLeadFilterStatus(status: LeadStatus?) {
        _uiState.value = _uiState.value.copy(selectedStatusFilter = status)
    }

    fun setLeadFilterTemperature(temp: LeadTemperature?) {
        _uiState.value = _uiState.value.copy(selectedTempFilter = temp)
    }

    fun setLeadSearchQuery(query: String) {
        _uiState.value = _uiState.value.copy(leadSearchQuery = query)
    }

    // Requirement 4: Allow status updates (New, Contacted, Interested, Converted, Not Interested)
    fun updateLeadStatus(leadId: String, newStatus: LeadStatus) {
        viewModelScope.launch {
            repository.updateStatus(leadId, newStatus.label)
        }
    }

    // Requirement 5: Allow deleting a lead
    fun deleteLead(leadId: String) {
        viewModelScope.launch {
            repository.deleteLead(leadId)
        }
    }

    // Interactive Chatbot methods
    fun onQuickChipSelected(chip: String) {
        addUserChatMessage(chip)
        handleBotResponse(chip)
    }

    fun sendUserCustomChat(text: String) {
        val trimmed = text.trim()
        if (trimmed.isEmpty()) return
        addUserChatMessage(trimmed)
        handleBotResponse(trimmed)
    }

    private fun addUserChatMessage(text: String) {
        val userMsg = ChatMessage(
            id = UUID.randomUUID().toString(),
            sender = MessageSender.USER,
            text = text,
            timestamp = getCurrentTime()
        )
        _uiState.value = _uiState.value.copy(
            chatMessages = _uiState.value.chatMessages + userMsg,
            isBotTyping = true
        )
    }

    private fun handleBotResponse(query: String) {
        viewModelScope.launch {
            delay(900)
            val lower = query.lowercase()

            val digits = query.filter { it.isDigit() }
            val (botReply, action) = when {
                digits.length >= 10 -> {
                    val phone = digits.takeLast(10)
                    val analysis = AiLeadAssistant.analyzeLead(
                        requirement = "Chatbot direct inquiry: $query",
                        budget = "₹9,999 (Business)",
                        business = "Chatbot Prospect",
                        service = "WhatsApp Automation",
                        name = "Chat Visitor"
                    )
                    val chatLead = LeadEntity(
                        id = UUID.randomUUID().toString(),
                        name = "Chat Visitor (${phone.takeLast(4)})",
                        mobile = phone,
                        businessType = "Chatbot Enquiry",
                        serviceRequired = "WhatsApp Chatbot",
                        budget = "₹9,999 (Business)",
                        message = query,
                        leadTemperature = analysis.temperature.label,
                        status = LeadStatus.NEW.label,
                        aiReason = "Lead captured directly from website chatbot conversation.",
                        nextAction = analysis.nextAction.label,
                        createdAt = System.currentTimeMillis()
                    )
                    repository.insertLead(chatLead)
                    Pair(
                        "धन्यवाद! आपका मोबाइल नंबर (+91 $phone) और रिक्वायरमेंट Deoria AI Hub CRM में सुरक्षित सेव हो गई है।\n\nहमारी टीम जल्द ही आपसे संपर्क करेगी या आप तुरंत नीचे दिए गए WhatsApp बटन से लाइव चैट शुरू कर सकते हैं।",
                        "lead_captured"
                    )
                }
                query.contains("Website बनवानी है") || lower.contains("website") || lower.contains("वेबसाइट") -> {
                    Pair(
                        "शानदार! Deoria AI Hub पर हम हाई-स्पीड, मोबाइल-फ्रेंडली और SEO-रेडी बिजनेस वेबसाइट बनाते हैं।\n\n• स्टार्टर प्लान: ₹4,999 (1 पेज वेबसाइट + WhatsApp बटन)\n• बिजनेस प्लान: ₹9,999 (मल्टी-पेज + चैटबॉट फ्लो)\n\nक्या आप अपनी वेबसाइट के लिए बात करना चाहते हैं?",
                        "website"
                    )
                }
                query.contains("WhatsApp Chatbot चाहिए") || lower.contains("chatbot") || lower.contains("चैटबॉट") -> {
                    Pair(
                        "WhatsApp Chatbot आपके बिज़नेस के लिए 24/7 काम करता है!\n\n✓ ग्राहक के मैसेज का तुरंत 2 सेकंड में जवाब\n✓ प्रॉडक्ट/कैटलॉग और सर्विस मेनू\n✓ ग्राहक का नाम व नंबर ऑटोमेटिक नोट करना\n✓ सीधे आपके फोन पर लीड फॉरवर्ड करना\n\nशुरुआती सेटअप सिर्फ ₹9,999 से उपलब्ध है।",
                        "chatbot"
                    )
                }
                query.contains("AI Automation चाहिए") || lower.contains("automation") || lower.contains("ऑटोमेशन") -> {
                    Pair(
                        "AI Automation से आपका रूटीन काम 10 गुना आसान हो जाता है!\n\n• लीड ऑटोमेशन और गूगल शीट्स सिंक\n• अपॉइंटमेंट व मीटिंग बुकिंग\n• ऑटोमेटिक फॉलो-अप मैसेजेस\n\nफुल AI ऑटोमेशन पैकेज ₹19,999 से शुरू होता है।",
                        "automation"
                    )
                }
                query.contains("Price जानना है") || lower.contains("price") || lower.contains("रेट") || lower.contains("कीमत") || lower.contains("खर्च") -> {
                    Pair(
                        "हमारे सभी प्लान पारदर्शी और बिना किसी छिपे शुल्क के हैं:\n\n1. Starter: ₹4,999 (वेबसाइट + WhatsApp लीड)\n2. Business: ₹9,999 (वेबसाइट + चैटबॉट फ्लो)\n3. AI Automation: ₹19,999 (फुल ऑटोमेशन सुइट)\n\nआप सीधे अपनी जरूरत बताकर कस्टमाइज्ड कोट भी ले सकते हैं।",
                        "price"
                    )
                }
                query.contains("Free Demo चाहिए") || lower.contains("demo") || lower.contains("डेमो") -> {
                    Pair(
                        "निश्चित रूप से! आप अपने बिज़नेस के लिए लाइव व्हाट्सएप बॉट और वेबसाइट का फ्री डेमो देख सकते हैं।\n\nनीचे दिए गए बटन से तुरंत हमारे एक्सपर्ट से +91 9919900020 पर संपर्क करें या फॉर्म भरें।",
                        "demo"
                    )
                }
                else -> {
                    Pair(
                        "धन्यवाद! आपके सवाल के लिए Deoria AI Hub टीम पूरी सहायता करेगी।\n\nआप सीधे हमारे WhatsApp नंबर +91 9919900020 पर मैसेज भेज सकते हैं या नीचे दिए गए enquiry फॉर्म में अपना नंबर छोड़ सकते हैं।",
                        "general"
                    )
                }
            }

            val botMsg = ChatMessage(
                id = UUID.randomUUID().toString(),
                sender = MessageSender.BOT,
                text = botReply,
                timestamp = getCurrentTime(),
                actionPayload = action
            )
            _uiState.value = _uiState.value.copy(
                chatMessages = _uiState.value.chatMessages + botMsg,
                isBotTyping = false
            )
        }
    }

    fun clearChat() {
        initChatMessages()
    }
}
