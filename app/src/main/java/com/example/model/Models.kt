package com.example.model

data class ServiceItem(
    val id: String,
    val titleEn: String,
    val titleHi: String,
    val descriptionEn: String,
    val descriptionHi: String,
    val iconName: String,
    val features: List<String>
)

data class StepItem(
    val stepNumber: Int,
    val titleEn: String,
    val titleHi: String,
    val descriptionEn: String,
    val descriptionHi: String,
    val iconName: String
)

data class PackageItem(
    val id: String,
    val name: String,
    val price: String,
    val tagline: String,
    val isPopular: Boolean = false,
    val features: List<String>,
    val bestFor: String
)

data class ChatMessage(
    val id: String,
    val sender: MessageSender,
    val text: String,
    val timestamp: String,
    val actionPayload: String? = null
)

enum class MessageSender {
    USER, BOT, SYSTEM
}

data class LeadFormData(
    val name: String = "",
    val mobileNumber: String = "",
    val businessType: String = "Retail / Shop",
    val requestedService: String = "WhatsApp Chatbot",
    val budget: String = "₹9,999 (Business)",
    val requirement: String = ""
)

enum class LeadStatus(val label: String, val colorHex: Long) {
    NEW("New", 0xFF38BDF8),
    CONTACTED("Contacted", 0xFFF59E0B),
    INTERESTED("Interested", 0xFF818CF8),
    CONVERTED("Converted", 0xFF22C55E),
    NOT_INTERESTED("Not Interested", 0xFF94A3B8)
}

enum class LeadTemperature(val label: String, val emoji: String, val colorHex: Long) {
    HOT("Hot Lead", "🔥", 0xFFEF4444),
    WARM("Warm Lead", "⚡", 0xFFF59E0B),
    COLD("Cold Lead", "❄️", 0xFF38BDF8)
}

enum class NextAction(val label: String, val emoji: String, val badgeColorHex: Long) {
    CALL_NOW("Call now", "📞", 0xFFEF4444),
    SEND_QUOTATION("Send quotation", "📄", 0xFFF59E0B),
    SEND_DEMO("Send demo", "▶️", 0xFF38BDF8),
    FOLLOW_UP_LATER("Follow up later", "⏰", 0xFF94A3B8);

    companion object {
        fun fromLabel(label: String): NextAction {
            return values().firstOrNull { it.label.equals(label, ignoreCase = true) } ?: CALL_NOW
        }
    }
}

data class AiLeadAnalysis(
    val temperature: LeadTemperature,
    val reason: String,
    val suggestedReply: String,
    val nextAction: NextAction
)

data class ManagedLead(
    val id: String,
    val name: String,
    val mobile: String,
    val business: String,
    val serviceRequired: String,
    val budget: String,
    val dateTime: String,
    val requirement: String,
    val status: LeadStatus = LeadStatus.NEW,
    val temperature: LeadTemperature = LeadTemperature.WARM,
    val aiReason: String = "",
    val suggestedReply: String = "",
    val nextAction: NextAction = NextAction.CALL_NOW,
    val isDemo: Boolean = false,
    val createdAt: Long = System.currentTimeMillis()
)

object BusinessConstants {
    const val WHATSAPP_DISPLAY = "+91 9919900020"
    const val WHATSAPP_NUMBER_CLEAN = "919919900020"
    const val BUSINESS_NAME = "Deoria AI Hub"
    const val TAGLINE = "AI Automation • WhatsApp Chatbot • Business Websites"

    fun buildHindiWhatsAppMessage(lead: LeadFormData): String {
        val name = lead.name.ifBlank { "[Name]" }
        val service = lead.requestedService.ifBlank { "[Service]" }
        val business = lead.businessType.ifBlank { "[Business Type]" }
        return "नमस्ते Deoria AI Hub, मेरा नाम $name है। मुझे $service की जरूरत है। मेरा Business $business है। कृपया मुझे जानकारी दें।"
    }

    fun buildQuickTopicMessage(topic: String): String {
        return "नमस्ते Deoria AI Hub, मुझे अपने बिज़नेस के लिए $topic के बारे में जानकारी और फ्री डेमो चाहिए।"
    }
}
