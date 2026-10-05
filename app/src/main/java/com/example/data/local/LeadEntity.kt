package com.example.data.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "customer_leads")
data class LeadEntity(
    @PrimaryKey
    val id: String,
    val name: String,
    val mobile: String,
    @ColumnInfo(name = "business_type")
    val businessType: String,
    @ColumnInfo(name = "service_required")
    val serviceRequired: String,
    val budget: String,
    val message: String,
    @ColumnInfo(name = "lead_temperature")
    val leadTemperature: String,
    val status: String,
    @ColumnInfo(name = "ai_reason")
    val aiReason: String = "",
    @ColumnInfo(name = "next_action")
    val nextAction: String = "Call now",
    @ColumnInfo(name = "created_at")
    val createdAt: Long = System.currentTimeMillis()
)
