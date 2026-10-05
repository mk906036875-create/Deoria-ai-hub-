package com.example.whatsapp

import com.example.model.LeadTemperature

data class WebhookVerificationRequest(
    val mode: String?,
    val token: String?,
    val challenge: String?
)

data class WhatsAppWebhookMessage(
    val from: String,
    val id: String,
    val timestamp: String,
    val text: String,
    val senderName: String = ""
)

data class WhatsAppConversationEntry(
    val id: String,
    val senderNumber: String,
    val senderName: String,
    val messageText: String,
    val isIncoming: Boolean,
    val timestamp: Long = System.currentTimeMillis(),
    val aiClassification: LeadTemperature? = null,
    val isHumanHandoff: Boolean = false
)

enum class WhatsAppIntegrationStatus {
    CONFIGURATION_REQUIRED,
    CONNECTED,
    WEBHOOK_VERIFIED,
    ERROR
}

data class WhatsAppPlatformConfig(
    val status: WhatsAppIntegrationStatus = WhatsAppIntegrationStatus.CONFIGURATION_REQUIRED,
    val isAiAutoReplyEnabled: Boolean = true,
    val isHumanHandoffActive: Boolean = false,
    val businessPhoneNumber: String = "+91 9919900020",
    val requiredEnvVars: List<String> = listOf(
        "WHATSAPP_PHONE_NUMBER_ID",
        "WHATSAPP_BUSINESS_ACCOUNT_ID",
        "WHATSAPP_ACCESS_TOKEN",
        "WHATSAPP_WEBHOOK_VERIFY_TOKEN"
    ),
    val webhookPath: String = "/api/v1/whatsapp/webhook"
)
