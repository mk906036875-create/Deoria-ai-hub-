package com.example.util

import com.example.model.AiLeadAnalysis
import com.example.model.LeadTemperature
import com.example.model.NextAction

object AiLeadAssistant {

    fun analyzeLead(
        requirement: String,
        budget: String,
        business: String,
        service: String,
        name: String = ""
    ): AiLeadAnalysis {
        val cleanReq = requirement.trim().lowercase()
        val cleanBudget = budget.lowercase()
        val customerName = if (name.isNotBlank()) "$name जी" else "जी"

        // 1 & 2. Classification
        // HOT: customer is ready to buy or asks for immediate service/price
        val isHot = cleanReq.contains("price") || cleanReq.contains("कीमत") || cleanReq.contains("रेट") ||
                cleanReq.contains("rate") || cleanReq.contains("cost") || cleanReq.contains("urgent") ||
                cleanReq.contains("तुरंत") || cleanReq.contains("chahiye") || cleanReq.contains("चाहिए") ||
                cleanReq.contains("ready") || cleanReq.contains("book") || cleanReq.contains("start") ||
                cleanBudget.contains("19,999") || cleanBudget.contains("25,000") || cleanBudget.contains("enterprise")

        // COLD: customer is only asking general questions
        val isCold = !isHot && (
                cleanReq.contains("kya hota hai") || cleanReq.contains("क्या होता है") ||
                cleanReq.contains("just checking") || cleanReq.contains("just asking") ||
                cleanReq.contains("general") || cleanReq.contains("सिर्फ पूछना था") ||
                cleanReq.contains("मालूम करना था") ||
                (cleanReq.length in 1..12 && cleanBudget.contains("4,999"))
        )

        // 3. Short reason & 5. Next action
        val (temperature, reason, nextAction) = when {
            isHot -> {
                val reasonText = when {
                    cleanReq.contains("price") || cleanReq.contains("कीमत") || cleanReq.contains("रेट") || cleanReq.contains("cost") ->
                        "Customer has a specific service requirement and is asking for price."
                    cleanReq.contains("urgent") || cleanReq.contains("तुरंत") || cleanReq.contains("ready") ->
                        "Customer expresses high urgency and is ready to start immediate implementation."
                    else ->
                        "Customer has a well-defined business requirement with clear purchasing intent."
                }
                val action = if (cleanReq.contains("price") || cleanReq.contains("कीमत") || cleanReq.contains("rate") || cleanReq.contains("cost")) {
                    NextAction.SEND_QUOTATION
                } else {
                    NextAction.CALL_NOW
                }
                Triple(LeadTemperature.HOT, reasonText, action)
            }
            isCold -> {
                val reasonText = "Customer is only asking general exploratory questions without immediate buying intent."
                Triple(LeadTemperature.COLD, reasonText, NextAction.FOLLOW_UP_LATER)
            }
            else -> {
                // WARM: customer is interested but needs more information
                val reasonText = "Customer is interested in $service and needs detailed information or a demo before deciding."
                Triple(LeadTemperature.WARM, reasonText, NextAction.SEND_DEMO)
            }
        }

        // 4. Generate professional Hindi WhatsApp reply
        val suggestedReply = when (temperature) {
            LeadTemperature.HOT -> {
                if (nextAction == NextAction.SEND_QUOTATION) {
                    "नमस्ते $customerName! आपके $business के लिए $service बनाया जा सकता है। मैं आपको Demo और Package की पूरी जानकारी भेज सकता हूँ। क्या आप 10 मिनट के कॉल पर कोटेशन समझना चाहेंगे?"
                } else {
                    "नमस्ते $customerName! 🙏 Deoria AI Hub से सादर प्रणाम। आपके $business के लिए $service तुरंत शुरू किया जा सकता है। क्या मैं अभी कॉल करके आपको प्रक्रिया समझा दूं?"
                }
            }
            LeadTemperature.WARM -> {
                "नमस्ते $customerName! Deoria AI Hub से संपर्क करने के लिए धन्यवाद। आपके $business के लिए $service का लाइव डेमो और फीचर्स रेडी हैं। क्या मैं आपको डेमो वीडियो और ब्रोशर WhatsApp पर शेयर करूं?"
            }
            LeadTemperature.COLD -> {
                "नमस्ते $customerName! Deoria AI Hub में आपका स्वागत है। आपके $business के लिए हमारे स्टार्टर और ऑटोमेशन पैकेजेस की जानकारी तैयार है। आप अपनी सुविधा अनुसार डिटेल्स देख सकते हैं।"
            }
        }

        return AiLeadAnalysis(
            temperature = temperature,
            reason = reason,
            suggestedReply = suggestedReply,
            nextAction = nextAction
        )
    }

    fun classifyTemperature(budget: String, business: String, requirement: String, service: String): LeadTemperature {
        return analyzeLead(requirement, budget, business, service).temperature
    }

    fun generateSuggestedReply(name: String, business: String, service: String, budget: String, temperature: LeadTemperature): String {
        return analyzeLead("", budget, business, service, name).suggestedReply
    }
}
