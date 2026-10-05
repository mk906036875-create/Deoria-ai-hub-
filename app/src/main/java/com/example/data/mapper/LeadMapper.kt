package com.example.data.mapper

import com.example.data.local.LeadEntity
import com.example.model.LeadStatus
import com.example.model.LeadTemperature
import com.example.model.ManagedLead
import com.example.model.NextAction
import com.example.util.AiLeadAssistant
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object LeadMapper {

    fun toManagedLead(entity: LeadEntity): ManagedLead {
        val tempEnum = when (entity.leadTemperature) {
            "Hot Lead" -> LeadTemperature.HOT
            "Cold Lead" -> LeadTemperature.COLD
            else -> LeadTemperature.WARM
        }
        val statusEnum = when (entity.status) {
            "Contacted" -> LeadStatus.CONTACTED
            "Interested" -> LeadStatus.INTERESTED
            "Converted" -> LeadStatus.CONVERTED
            "Not Interested" -> LeadStatus.NOT_INTERESTED
            else -> LeadStatus.NEW
        }
        val dateStr = SimpleDateFormat("dd MMM, hh:mm a", Locale.getDefault()).format(Date(entity.createdAt))

        // If aiReason was not saved, compute analysis
        val analysis = if (entity.aiReason.isBlank()) {
            AiLeadAssistant.analyzeLead(
                requirement = entity.message,
                budget = entity.budget,
                business = entity.businessType,
                service = entity.serviceRequired,
                name = entity.name
            )
        } else {
            null
        }

        val reason = entity.aiReason.ifBlank { analysis?.reason ?: "" }
        val nextAction = if (entity.nextAction.isNotBlank()) {
            NextAction.fromLabel(entity.nextAction)
        } else {
            analysis?.nextAction ?: NextAction.CALL_NOW
        }
        val reply = analysis?.suggestedReply ?: AiLeadAssistant.analyzeLead(
            requirement = entity.message,
            budget = entity.budget,
            business = entity.businessType,
            service = entity.serviceRequired,
            name = entity.name
        ).suggestedReply

        return ManagedLead(
            id = entity.id,
            name = entity.name,
            mobile = entity.mobile,
            business = entity.businessType,
            serviceRequired = entity.serviceRequired,
            budget = entity.budget,
            dateTime = dateStr,
            requirement = entity.message,
            status = statusEnum,
            temperature = tempEnum,
            aiReason = reason,
            suggestedReply = reply,
            nextAction = nextAction,
            isDemo = false,
            createdAt = entity.createdAt
        )
    }

    fun toEntity(lead: ManagedLead): LeadEntity {
        return LeadEntity(
            id = lead.id,
            name = lead.name,
            mobile = lead.mobile,
            businessType = lead.business,
            serviceRequired = lead.serviceRequired,
            budget = lead.budget,
            message = lead.requirement,
            leadTemperature = lead.temperature.label,
            status = lead.status.label,
            aiReason = lead.aiReason,
            nextAction = lead.nextAction.label,
            createdAt = lead.createdAt
        )
    }
}
