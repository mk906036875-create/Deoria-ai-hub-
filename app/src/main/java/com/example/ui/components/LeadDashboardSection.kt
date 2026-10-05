package com.example.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Chat
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Send
import androidx.compose.material.icons.filled.Storage
import androidx.compose.material.icons.filled.Work
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.LeadStatus
import com.example.model.LeadTemperature
import com.example.model.ManagedLead
import com.example.model.NextAction
import com.example.ui.theme.TechCyan
import com.example.ui.theme.TechDarkBorder
import com.example.ui.theme.TechDarkSurface
import com.example.ui.theme.TechDarkSurfaceVariant
import com.example.ui.theme.TechGold
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextWhite
import com.example.ui.theme.WhatsAppGreen
import com.example.util.WhatsAppHelper
import com.example.viewmodel.DashboardStats
import com.example.viewmodel.DatabaseInfo

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun LeadDashboardSection(
    modifier: Modifier = Modifier,
    leads: List<ManagedLead>,
    stats: DashboardStats,
    dbInfo: DatabaseInfo,
    selectedStatus: LeadStatus?,
    selectedTemp: LeadTemperature?,
    searchQuery: String,
    onStatusFilterChange: (LeadStatus?) -> Unit,
    onTempFilterChange: (LeadTemperature?) -> Unit,
    onSearchQueryChange: (String) -> Unit,
    onUpdateStatus: (String, LeadStatus) -> Unit,
    onDeleteLead: (String) -> Unit,
    onNavigateToEnquiryForm: () -> Unit
) {
    val context = LocalContext.current
    var activeStatusChangeLead by remember { mutableStateOf<ManagedLead?>(null) }
    var activeDeleteLead by remember { mutableStateOf<ManagedLead?>(null) }
    var showDbInfoDialog by remember { mutableStateOf(false) }

    // User confirmation state before sending WhatsApp message
    var leadToConfirmWhatsApp by remember { mutableStateOf<ManagedLead?>(null) }
    var editableWhatsAppText by remember { mutableStateOf("") }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 20.dp)
    ) {
        // Section Header
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Box(
                    modifier = Modifier
                        .width(4.dp)
                        .height(24.dp)
                        .clip(RoundedCornerShape(2.dp))
                        .background(TechCyan)
                )
                Spacer(modifier = Modifier.width(10.dp))
                Column {
                    Text(
                        text = "AI Lead Assistant & CRM",
                        style = MaterialTheme.typography.titleLarge.copy(
                            fontWeight = FontWeight.Bold,
                            color = TextWhite,
                            fontSize = 19.sp
                        )
                    )
                    Text(
                        text = "Intelligent Classification & Automated Next Actions",
                        style = MaterialTheme.typography.bodySmall.copy(
                            color = TechCyan,
                            fontSize = 12.sp
                        )
                    )
                }
            }

            // Database Info Modal Trigger
            IconButton(
                onClick = { showDbInfoDialog = true },
                modifier = Modifier.testTag("db_info_button")
            ) {
                Icon(
                    imageVector = Icons.Default.Storage,
                    contentDescription = "Database Status",
                    tint = WhatsAppGreen
                )
            }
        }

        Spacer(modifier = Modifier.height(14.dp))

        // Real Database Status Card
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .clickable { showDbInfoDialog = true },
            shape = RoundedCornerShape(12.dp),
            colors = CardDefaults.cardColors(containerColor = TechDarkSurfaceVariant),
            border = BorderStroke(1.dp, TechDarkBorder)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(10.dp)
                        .clip(CircleShape)
                        .background(WhatsAppGreen)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Column(modifier = Modifier.weight(1f)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = "Database: ${dbInfo.databaseName}",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            color = TextWhite
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "• Room SQLite",
                            fontSize = 11.sp,
                            color = WhatsAppGreen
                        )
                    }
                    Text(
                        text = "Persistent on device • AI analysis auto-computed • Zero demo leads",
                        fontSize = 11.sp,
                        color = TextMuted
                    )
                }

                Icon(
                    imageVector = Icons.Default.Info,
                    contentDescription = "Details",
                    tint = TechCyan,
                    modifier = Modifier.size(18.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Dashboard Stats (Total, New, Hot, Warm, Cold, Converted)
        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                StatCard(
                    title = "Total Leads",
                    count = stats.totalLeads.toString(),
                    color = TechCyan,
                    modifier = Modifier.weight(1f)
                )
                StatCard(
                    title = "New Leads",
                    count = stats.newLeads.toString(),
                    color = Color(0xFF38BDF8),
                    modifier = Modifier.weight(1f)
                )
                StatCard(
                    title = "Hot Leads",
                    count = stats.hotLeads.toString(),
                    color = Color(0xFFEF4444),
                    modifier = Modifier.weight(1f)
                )
            }

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                StatCard(
                    title = "Warm Leads",
                    count = stats.warmLeads.toString(),
                    color = Color(0xFFF59E0B),
                    modifier = Modifier.weight(1f)
                )
                StatCard(
                    title = "Cold Leads",
                    count = stats.coldLeads.toString(),
                    color = Color(0xFF06B6D4),
                    modifier = Modifier.weight(1f)
                )
                StatCard(
                    title = "Converted",
                    count = stats.convertedLeads.toString(),
                    color = WhatsAppGreen,
                    modifier = Modifier.weight(1f)
                )
            }
        }

        Spacer(modifier = Modifier.height(18.dp))

        // Search Bar (Requirement 6)
        OutlinedTextField(
            value = searchQuery,
            onValueChange = onSearchQueryChange,
            placeholder = { Text("Search by name or mobile number...", fontSize = 12.sp, color = TextMuted) },
            leadingIcon = {
                Icon(Icons.Default.Search, contentDescription = null, tint = TextMuted)
            },
            singleLine = true,
            modifier = Modifier
                .fillMaxWidth()
                .testTag("leads_search_input"),
            shape = RoundedCornerShape(12.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedTextColor = TextWhite,
                unfocusedTextColor = TextWhite,
                focusedBorderColor = TechCyan,
                unfocusedBorderColor = TechDarkBorder,
                focusedContainerColor = TechDarkSurface,
                unfocusedContainerColor = TechDarkSurface
            )
        )

        Spacer(modifier = Modifier.height(14.dp))

        // Filter Chips (All, Hot, Statuses)
        Column(modifier = Modifier.fillMaxWidth()) {
            Text(
                text = "Filter Leads by Status & AI Heat:",
                fontSize = 11.sp,
                fontWeight = FontWeight.SemiBold,
                color = TextMuted,
                modifier = Modifier.padding(bottom = 6.dp)
            )

            FlowRow(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(6.dp),
                verticalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                // All Filter
                FilterChip(
                    selected = selectedStatus == null && selectedTemp == null,
                    onClick = {
                        onStatusFilterChange(null)
                        onTempFilterChange(null)
                    },
                    label = { Text("All (${leads.size})", fontSize = 11.sp) },
                    shape = RoundedCornerShape(16.dp),
                    colors = chipColors(selectedStatus == null && selectedTemp == null)
                )

                // Hot Filter
                FilterChip(
                    selected = selectedTemp == LeadTemperature.HOT,
                    onClick = {
                        onTempFilterChange(if (selectedTemp == LeadTemperature.HOT) null else LeadTemperature.HOT)
                        onStatusFilterChange(null)
                    },
                    label = { Text("🔥 Hot Leads", fontSize = 11.sp) },
                    shape = RoundedCornerShape(16.dp),
                    colors = chipColors(selectedTemp == LeadTemperature.HOT, Color(0xFFEF4444))
                )

                // Status filters
                LeadStatus.values().forEach { status ->
                    val isSelected = selectedStatus == status
                    FilterChip(
                        selected = isSelected,
                        onClick = {
                            onStatusFilterChange(if (isSelected) null else status)
                            onTempFilterChange(null)
                        },
                        label = { Text(status.label, fontSize = 11.sp) },
                        shape = RoundedCornerShape(16.dp),
                        colors = chipColors(isSelected, Color(status.colorHex))
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Leads Count Header
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Saved Leads (${leads.size})",
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold,
                color = TextWhite
            )

            TextButton(onClick = onNavigateToEnquiryForm) {
                Text("+ New Lead Form", color = WhatsAppGreen, fontSize = 12.sp, fontWeight = FontWeight.Bold)
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Leads List
        if (leads.isEmpty()) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp),
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(containerColor = TechDarkSurface),
                border = BorderStroke(1.dp, TechDarkBorder)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Box(
                        modifier = Modifier
                            .size(50.dp)
                            .clip(CircleShape)
                            .background(TechDarkSurfaceVariant),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.AutoAwesome,
                            contentDescription = null,
                            tint = TechCyan,
                            modifier = Modifier.size(26.dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    Text(
                        text = "डेटाबेस में अभी कोई Lead सुरक्षित नहीं है",
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold,
                        color = TextWhite
                    )

                    Spacer(modifier = Modifier.height(6.dp))

                    Text(
                        text = "रियल डेटाबेस एक्टिव है। जब कोई विज़िटर वेबसाइट फॉर्म भरेगा, AI Assistant तुरंत उसका एनालिसिस, टेम्परेचर और सुझावित WhatsApp रिप्लाई तैयार करेगा।",
                        fontSize = 12.sp,
                        color = TextMuted,
                        textAlign = androidx.compose.ui.text.style.TextAlign.Center,
                        lineHeight = 18.sp
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    Button(
                        onClick = onNavigateToEnquiryForm,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = WhatsAppGreen,
                            contentColor = Color.Black
                        ),
                        shape = RoundedCornerShape(10.dp)
                    ) {
                        Text("पहली Lead दर्ज करें (Go to Form)", fontWeight = FontWeight.Bold, fontSize = 13.sp)
                    }
                }
            }
        } else {
            Column(verticalArrangement = Arrangement.spacedBy(14.dp)) {
                leads.forEach { lead ->
                    AiLeadAssistantCard(
                        lead = lead,
                        onInitiateWhatsApp = {
                            leadToConfirmWhatsApp = lead
                            editableWhatsAppText = lead.suggestedReply
                        },
                        onCallClick = {
                            WhatsAppHelper.dialPhoneNumber(context, lead.mobile)
                        },
                        onCopyReplyClick = {
                            WhatsAppHelper.copyToClipboard(
                                context = context,
                                text = lead.suggestedReply,
                                toastMessage = "AI WhatsApp Reply copied to clipboard!"
                            )
                        },
                        onChangeStatusClick = {
                            activeStatusChangeLead = lead
                        },
                        onDeleteClick = {
                            activeDeleteLead = lead
                        }
                    )
                }
            }
        }
    }

    // MANDATORY REQUIREMENT: Confirmation Dialog before sending WhatsApp message!
    // "Do not automatically send messages without user confirmation. Clearly label AI-generated replies."
    leadToConfirmWhatsApp?.let { targetLead ->
        AlertDialog(
            onDismissRequest = { leadToConfirmWhatsApp = null },
            title = {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.Chat, contentDescription = null, tint = WhatsAppGreen)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Confirm WhatsApp Message",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        color = TextWhite
                    )
                }
            },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    // Transparent AI label
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(8.dp))
                            .background(Color(0xFF16252B))
                            .border(BorderStroke(1.dp, TechCyan.copy(alpha = 0.5f)), RoundedCornerShape(8.dp))
                            .padding(10.dp)
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.AutoAwesome, contentDescription = null, tint = TechCyan, modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "🤖 AI-GENERATED SUGGESTED REPLY (Review & Confirm)",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                color = TechCyan
                            )
                        }
                    }

                    Text(
                        text = "Recipient: ${targetLead.name} (+91 ${targetLead.mobile})\nBusiness: ${targetLead.business}",
                        fontSize = 12.sp,
                        color = TextWhite,
                        fontWeight = FontWeight.Medium
                    )

                    Text(
                        text = "You can edit this message before launching WhatsApp:",
                        fontSize = 11.sp,
                        color = TextMuted
                    )

                    OutlinedTextField(
                        value = editableWhatsAppText,
                        onValueChange = { editableWhatsAppText = it },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(130.dp)
                            .testTag("editable_whatsapp_text"),
                        shape = RoundedCornerShape(10.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedTextColor = TextWhite,
                            unfocusedTextColor = TextWhite,
                            focusedBorderColor = WhatsAppGreen,
                            unfocusedBorderColor = TechDarkBorder,
                            focusedContainerColor = TechDarkSurfaceVariant,
                            unfocusedContainerColor = TechDarkSurfaceVariant
                        )
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        val cleanNumber = "91" + targetLead.mobile.filter { it.isDigit() }.takeLast(10)
                        WhatsAppHelper.openWhatsApp(
                            context = context,
                            message = editableWhatsAppText,
                            phoneNumber = cleanNumber
                        )
                        leadToConfirmWhatsApp = null
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = WhatsAppGreen,
                        contentColor = Color.Black
                    ),
                    shape = RoundedCornerShape(8.dp)
                ) {
                    Icon(Icons.Default.Send, contentDescription = null, modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(6.dp))
                    Text("Confirm & Send WhatsApp", fontWeight = FontWeight.Bold, fontSize = 12.sp)
                }
            },
            dismissButton = {
                TextButton(onClick = { leadToConfirmWhatsApp = null }) {
                    Text("Cancel", color = TextMuted)
                }
            },
            containerColor = TechDarkSurface,
            shape = RoundedCornerShape(16.dp)
        )
    }

    // Status Change Dialog
    activeStatusChangeLead?.let { currentLead ->
        AlertDialog(
            onDismissRequest = { activeStatusChangeLead = null },
            title = {
                Text(
                    text = "Update Lead Status",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextWhite
                )
            },
            text = {
                Column {
                    Text(
                        text = "${currentLead.name} • ${currentLead.business}",
                        fontSize = 13.sp,
                        color = TextMuted
                    )
                    Spacer(modifier = Modifier.height(14.dp))
                    LeadStatus.values().forEach { st ->
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(8.dp))
                                .clickable {
                                    onUpdateStatus(currentLead.id, st)
                                    activeStatusChangeLead = null
                                }
                                .padding(vertical = 10.dp, horizontal = 8.dp)
                        ) {
                            Box(
                                modifier = Modifier
                                    .size(10.dp)
                                    .clip(CircleShape)
                                    .background(Color(st.colorHex))
                            )
                            Spacer(modifier = Modifier.width(10.dp))
                            Text(
                                text = st.label,
                                fontSize = 14.sp,
                                color = if (currentLead.status == st) TextWhite else TextMuted,
                                fontWeight = if (currentLead.status == st) FontWeight.Bold else FontWeight.Normal,
                                modifier = Modifier.weight(1f)
                            )
                            if (currentLead.status == st) {
                                Icon(
                                    imageVector = Icons.Default.Check,
                                    contentDescription = null,
                                    tint = WhatsAppGreen,
                                    modifier = Modifier.size(18.dp)
                                )
                            }
                        }
                    }
                }
            },
            confirmButton = {
                TextButton(onClick = { activeStatusChangeLead = null }) {
                    Text("Cancel", color = TextMuted)
                }
            },
            containerColor = TechDarkSurface,
            shape = RoundedCornerShape(16.dp)
        )
    }

    // Delete Confirmation Dialog
    activeDeleteLead?.let { leadToDelete ->
        AlertDialog(
            onDismissRequest = { activeDeleteLead = null },
            title = {
                Text(
                    text = "Delete Lead?",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFFEF4444)
                )
            },
            text = {
                Text(
                    text = "Are you sure you want to permanently delete '${leadToDelete.name}' from the database? This action cannot be undone.",
                    fontSize = 13.sp,
                    color = TextWhite,
                    lineHeight = 18.sp
                )
            },
            confirmButton = {
                Button(
                    onClick = {
                        onDeleteLead(leadToDelete.id)
                        activeDeleteLead = null
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color(0xFFEF4444),
                        contentColor = Color.White
                    )
                ) {
                    Text("Delete Permanently")
                }
            },
            dismissButton = {
                TextButton(onClick = { activeDeleteLead = null }) {
                    Text("Cancel", color = TextMuted)
                }
            },
            containerColor = TechDarkSurface,
            shape = RoundedCornerShape(16.dp)
        )
    }

    // Database Architecture & Status Info Dialog
    if (showDbInfoDialog) {
        AlertDialog(
            onDismissRequest = { showDbInfoDialog = false },
            title = {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.Storage, contentDescription = null, tint = WhatsAppGreen)
                    Spacer(modifier = Modifier.width(8.dp))
                    Text("Database Architecture & Status", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = TextWhite)
                }
            },
            text = {
                Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
                    Text(
                        text = "1. Active Local Database Engine",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold,
                        color = TechCyan
                    )
                    Text(
                        text = "• Engine: Android Room (Persistent SQLite)\n• Database: ${dbInfo.databaseName}\n• Table: ${dbInfo.tableName}\n• Status: 🟢 Fully Connected & Active\n• Durability: Persists indefinitely across app restarts, reloads, and offline mode.",
                        fontSize = 12.sp,
                        color = TextWhite,
                        lineHeight = 17.sp
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    Text(
                        text = "2. Remote Cloud Sync Architecture",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold,
                        color = TechGold
                    )
                    Text(
                        text = "• Cloud Sync: ${dbInfo.cloudSyncStatus}\n• Required Configuration: If connecting external cloud databases (e.g. Supabase, PostgreSQL, or Firebase), supply credentials through the AI Studio Secrets panel.\n• Security Mandate: Database master passwords and API keys are never exposed in frontend application source code.",
                        fontSize = 12.sp,
                        color = TextMuted,
                        lineHeight = 17.sp
                    )
                }
            },
            confirmButton = {
                TextButton(onClick = { showDbInfoDialog = false }) {
                    Text("Close", color = TechCyan)
                }
            },
            containerColor = TechDarkSurface,
            shape = RoundedCornerShape(16.dp)
        )
    }
}

@Composable
private fun StatCard(
    title: String,
    count: String,
    color: Color,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = TechDarkSurface),
        border = BorderStroke(1.dp, TechDarkBorder)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = count,
                fontSize = 20.sp,
                fontWeight = FontWeight.ExtraBold,
                color = color
            )
            Spacer(modifier = Modifier.height(2.dp))
            Text(
                text = title,
                fontSize = 10.sp,
                fontWeight = FontWeight.Medium,
                color = TextMuted,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

@Composable
private fun AiLeadAssistantCard(
    lead: ManagedLead,
    onInitiateWhatsApp: () -> Unit,
    onCallClick: () -> Unit,
    onCopyReplyClick: () -> Unit,
    onChangeStatusClick: () -> Unit,
    onDeleteClick: () -> Unit
) {
    var expandedAiDetails by remember { mutableStateOf(true) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .testTag("lead_card_${lead.id}"),
        shape = RoundedCornerShape(14.dp),
        colors = CardDefaults.cardColors(containerColor = TechDarkSurface),
        border = BorderStroke(
            1.2.dp,
            if (lead.temperature == LeadTemperature.HOT) Color(0xFFEF4444).copy(alpha = 0.7f) else TechDarkBorder
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp)
        ) {
            // Header: Name + Temperature + Delete
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.weight(1f)) {
                    Text(
                        text = lead.name,
                        fontSize = 15.sp,
                        fontWeight = FontWeight.Bold,
                        color = TextWhite,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(4.dp))
                            .background(Color(0x3322C55E))
                            .padding(horizontal = 5.dp, vertical = 2.dp)
                    ) {
                        Text("SAVED DB", fontSize = 9.sp, color = WhatsAppGreen, fontWeight = FontWeight.Bold)
                    }
                }

                Row(verticalAlignment = Alignment.CenterVertically) {
                    // Requirement 2: AI Temperature Classification Badge (HOT, WARM, COLD)
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(20.dp))
                            .background(Color(lead.temperature.colorHex).copy(alpha = 0.2f))
                            .border(BorderStroke(1.dp, Color(lead.temperature.colorHex)), RoundedCornerShape(20.dp))
                            .padding(horizontal = 8.dp, vertical = 3.dp)
                    ) {
                        Text(
                            text = "${lead.temperature.emoji} ${lead.temperature.label}",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color(lead.temperature.colorHex)
                        )
                    }

                    Spacer(modifier = Modifier.width(4.dp))

                    IconButton(
                        onClick = onDeleteClick,
                        modifier = Modifier.size(28.dp).testTag("delete_lead_${lead.id}")
                    ) {
                        Icon(
                            imageVector = Icons.Default.Delete,
                            contentDescription = "Delete Lead",
                            tint = Color(0xFFEF4444),
                            modifier = Modifier.size(16.dp)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(4.dp))

            // Time & Status Row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = lead.dateTime,
                    fontSize = 11.sp,
                    color = TextMuted
                )

                // Status chip
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .clip(RoundedCornerShape(12.dp))
                        .background(Color(lead.status.colorHex).copy(alpha = 0.15f))
                        .clickable { onChangeStatusClick() }
                        .padding(horizontal = 8.dp, vertical = 3.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .size(6.dp)
                            .clip(CircleShape)
                            .background(Color(lead.status.colorHex))
                    )
                    Spacer(modifier = Modifier.width(5.dp))
                    Text(
                        text = lead.status.label,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = Color(lead.status.colorHex)
                    )
                    Spacer(modifier = Modifier.width(3.dp))
                    Icon(
                        imageVector = Icons.Default.Edit,
                        contentDescription = "Edit Status",
                        tint = Color(lead.status.colorHex),
                        modifier = Modifier.size(10.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            // Customer Enquiry Details
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(8.dp))
                    .background(TechDarkSurfaceVariant)
                    .padding(10.dp),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.Work, contentDescription = null, tint = TechCyan, modifier = Modifier.size(14.dp))
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(text = "Business Type: ${lead.business}", fontSize = 12.sp, color = TextWhite)
                }
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.Phone, contentDescription = null, tint = WhatsAppGreen, modifier = Modifier.size(14.dp))
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(text = "Mobile: +91 ${lead.mobile}", fontSize = 12.sp, color = WhatsAppGreen, fontWeight = FontWeight.Medium)
                }
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.AutoAwesome, contentDescription = null, tint = TechGold, modifier = Modifier.size(14.dp))
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(text = "Service: ${lead.serviceRequired} • Budget: ${lead.budget}", fontSize = 12.sp, color = TextWhite)
                }
                if (lead.requirement.isNotBlank()) {
                    Text(
                        text = "Customer Requirement: \"${lead.requirement}\"",
                        fontSize = 11.sp,
                        color = TextWhite,
                        fontWeight = FontWeight.Medium,
                        modifier = Modifier.padding(start = 20.dp, top = 2.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(10.dp))

            // AI LEAD ASSISTANT RESULTS CONTAINER (Requirements 2, 3, 4, 5, 6)
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(10.dp))
                    .background(Color(0xFF0F1E24))
                    .border(BorderStroke(1.dp, TechCyan.copy(alpha = 0.5f)), RoundedCornerShape(10.dp))
                    .padding(12.dp)
            ) {
                // Header with expand/collapse
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { expandedAiDetails = !expandedAiDetails },
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.AutoAwesome,
                            contentDescription = null,
                            tint = TechCyan,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "AI LEAD ASSISTANT ANALYSIS",
                            fontSize = 11.sp,
                            fontWeight = FontWeight.Bold,
                            color = TechCyan,
                            letterSpacing = 0.6.sp
                        )
                    }

                    Icon(
                        imageVector = if (expandedAiDetails) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                        contentDescription = null,
                        tint = TechCyan,
                        modifier = Modifier.size(16.dp)
                    )
                }

                AnimatedVisibility(visible = expandedAiDetails) {
                    Column(
                        modifier = Modifier.padding(top = 10.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        // Requirement 3: Short reason for classification
                        Row(verticalAlignment = Alignment.Top) {
                            Text(
                                text = "AI Reason: ",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                color = TechGold
                            )
                            Text(
                                text = lead.aiReason.ifBlank { "Customer inquiry matches automated lead qualification criteria." },
                                fontSize = 11.sp,
                                color = TextWhite,
                                lineHeight = 16.sp
                            )
                        }

                        // Requirement 5: Suggest Next Action (Call now, Send quotation, Send demo, Follow up later)
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .clip(RoundedCornerShape(6.dp))
                                .background(Color(lead.nextAction.badgeColorHex).copy(alpha = 0.2f))
                                .border(BorderStroke(1.dp, Color(lead.nextAction.badgeColorHex)), RoundedCornerShape(6.dp))
                                .padding(horizontal = 8.dp, vertical = 4.dp)
                        ) {
                            Text(
                                text = "Next Suggested Action: ${lead.nextAction.emoji} ${lead.nextAction.label}",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color(lead.nextAction.badgeColorHex)
                            )
                        }

                        Spacer(modifier = Modifier.height(2.dp))

                        // Requirement 4: Professional Hindi WhatsApp reply
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(8.dp))
                                .background(Color(0xFF132A32))
                                .padding(10.dp)
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Box(
                                    modifier = Modifier
                                        .size(6.dp)
                                        .clip(CircleShape)
                                        .background(WhatsAppGreen)
                                )
                                Spacer(modifier = Modifier.width(6.dp))
                                Text(
                                    text = "🤖 AI-GENERATED SUGGESTED REPLY (HINDI):",
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = WhatsAppGreen
                                )
                            }
                            Spacer(modifier = Modifier.height(6.dp))
                            Text(
                                text = lead.suggestedReply,
                                fontSize = 12.sp,
                                lineHeight = 17.sp,
                                color = TextWhite
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Action Buttons (WhatsApp with confirmation, Call, Copy Reply, Status)
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(6.dp)
            ) {
                // 1. WhatsApp Button (Triggers user confirmation dialog first!)
                Button(
                    onClick = onInitiateWhatsApp,
                    modifier = Modifier
                        .weight(1.3f)
                        .height(38.dp)
                        .testTag("lead_whatsapp_${lead.id}"),
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = WhatsAppGreen,
                        contentColor = Color.Black
                    )
                ) {
                    Icon(Icons.Default.Chat, contentDescription = null, modifier = Modifier.size(15.dp))
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("WhatsApp", fontSize = 11.sp, fontWeight = FontWeight.Bold)
                }

                // 2. Call Button
                OutlinedButton(
                    onClick = onCallClick,
                    modifier = Modifier
                        .weight(0.9f)
                        .height(38.dp)
                        .testTag("lead_call_${lead.id}"),
                    shape = RoundedCornerShape(8.dp),
                    border = BorderStroke(1.dp, TechDarkBorder),
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = TextWhite)
                ) {
                    Icon(Icons.Default.Call, contentDescription = null, modifier = Modifier.size(15.dp), tint = TechCyan)
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("Call", fontSize = 11.sp)
                }

                // 3. Copy Reply Button
                OutlinedButton(
                    onClick = onCopyReplyClick,
                    modifier = Modifier
                        .weight(1.2f)
                        .height(38.dp)
                        .testTag("lead_copy_${lead.id}"),
                    shape = RoundedCornerShape(8.dp),
                    border = BorderStroke(1.dp, TechDarkBorder),
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = TextWhite)
                ) {
                    Icon(Icons.Default.ContentCopy, contentDescription = null, modifier = Modifier.size(14.dp), tint = TechGold)
                    Spacer(modifier = Modifier.width(4.dp))
                    Text("Copy Reply", fontSize = 10.sp, fontWeight = FontWeight.SemiBold)
                }

                // 4. Change Status Button
                OutlinedButton(
                    onClick = onChangeStatusClick,
                    modifier = Modifier
                        .weight(1f)
                        .height(38.dp)
                        .testTag("lead_status_${lead.id}"),
                    shape = RoundedCornerShape(8.dp),
                    border = BorderStroke(1.dp, TechDarkBorder),
                    colors = ButtonDefaults.outlinedButtonColors(contentColor = TextWhite)
                ) {
                    Text("Status", fontSize = 11.sp)
                }
            }
        }
    }
}

@Composable
private fun chipColors(isSelected: Boolean, activeColor: Color = WhatsAppGreen) = FilterChipDefaults.filterChipColors(
    selectedContainerColor = activeColor,
    selectedLabelColor = Color.Black,
    containerColor = TechDarkSurface,
    labelColor = TextWhite
)
