package com.example.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Key
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Send
import androidx.compose.material.icons.filled.SupportAgent
import androidx.compose.material.icons.filled.Sync
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.BusinessConstants
import com.example.ui.theme.TechCyan
import com.example.ui.theme.TechDarkBorder
import com.example.ui.theme.TechDarkSurface
import com.example.ui.theme.TechDarkSurfaceVariant
import com.example.ui.theme.TechGold
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextWhite
import com.example.ui.theme.WhatsAppGreen
import com.example.whatsapp.WhatsAppIntegrationService
import com.example.whatsapp.WhatsAppWebhookMessage
import java.util.UUID

@Composable
fun WhatsAppIntegrationSection(
    modifier: Modifier = Modifier
) {
    val config by WhatsAppIntegrationService.configState.collectAsState()
    val history by WhatsAppIntegrationService.conversationHistory.collectAsState()
    var simulatorInput by remember { mutableStateOf("Mujhe apne business ke liye WhatsApp chatbot chahiye. Price kya hai?") }
    var simulatorSender by remember { mutableStateOf("Rahul Verma") }

    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 20.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // Section Header
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = Modifier
                    .width(4.dp)
                    .height(24.dp)
                    .clip(RoundedCornerShape(2.dp))
                    .background(WhatsAppGreen)
            )
            Spacer(modifier = Modifier.width(10.dp))
            Column {
                Text(
                    text = "WhatsApp Business Platform Architecture",
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontWeight = FontWeight.Bold,
                        color = TextWhite,
                        fontSize = 18.sp
                    )
                )
                Text(
                    text = "Cloud API Webhook, Auto-Reply & Human Handoff",
                    style = MaterialTheme.typography.bodySmall.copy(
                        color = TechCyan,
                        fontSize = 12.sp
                    )
                )
            }
        }

        // Configuration Required Status Notice (Honest declaration: never pretend active without credentials)
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(12.dp))
                .background(Color(0xFF231B0F))
                .border(BorderStroke(1.dp, TechGold.copy(alpha = 0.5f)), RoundedCornerShape(12.dp))
                .padding(14.dp)
        ) {
            Row(verticalAlignment = Alignment.Top) {
                Icon(
                    imageVector = Icons.Default.Info,
                    contentDescription = null,
                    tint = TechGold,
                    modifier = Modifier
                        .size(20.dp)
                        .padding(top = 2.dp)
                )
                Spacer(modifier = Modifier.width(10.dp))
                Column {
                    Text(
                        text = "Status: Configuration Required",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold,
                        color = TechGold
                    )
                    Text(
                        text = "WhatsApp Business Cloud API आर्किटेक्चर पूर्णतया तैयार है। लाइव ऑटोमेशन शुरू करने के लिए Meta Developers कंसोल से क्रेडेंशियल्स AI Studio Secrets पैनल में जोड़ें। जब तक क्रेडेंशियल्स कॉन्फ़िगर नहीं होते, तब तक सुरक्षित सिमुलेशन मोड एक्टिव रहेगा।",
                        fontSize = 11.sp,
                        lineHeight = 16.sp,
                        color = TextWhite.copy(alpha = 0.9f)
                    )
                }
            }
        }

        // Toggles Card: AI Auto Reply & Human Handoff
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(14.dp),
            colors = CardDefaults.cardColors(containerColor = TechDarkSurface),
            border = BorderStroke(1.dp, TechDarkBorder)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(14.dp)
            ) {
                Text(
                    text = "Automation Controls",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextWhite
                )

                // 1. AI Auto Reply Switch
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "AI Auto-Reply Engine",
                            fontSize = 13.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = TextWhite
                        )
                        Text(
                            text = if (config.isAiAutoReplyEnabled) "ON: AI स्वतः उत्तर जनरेट करेगा" else "OFF: ऑटो-रिप्लाई पॉज़ है",
                            fontSize = 11.sp,
                            color = if (config.isAiAutoReplyEnabled) WhatsAppGreen else TextMuted
                        )
                    }

                    Switch(
                        checked = config.isAiAutoReplyEnabled,
                        onCheckedChange = { WhatsAppIntegrationService.setAiAutoReply(it) },
                        colors = SwitchDefaults.colors(
                            checkedThumbColor = WhatsAppGreen,
                            checkedTrackColor = Color(0xFF1E3A2B)
                        ),
                        modifier = Modifier.testTag("ai_auto_reply_switch")
                    )
                }

                Box(modifier = Modifier.fillMaxWidth().height(1.dp).background(TechDarkBorder))

                // 2. Human Handoff Switch
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(modifier = Modifier.weight(1f)) {
                        Text(
                            text = "Human Handoff (लाइव एजेंट)",
                            fontSize = 13.sp,
                            fontWeight = FontWeight.SemiBold,
                            color = TextWhite
                        )
                        Text(
                            text = if (config.isHumanHandoffActive) "ACTIVE: संदेश सीधे मानवीय प्रतिनिधि को भेजे जा रहे हैं" else "INACTIVE: बॉट ऑटोमेटेड संचालन में है",
                            fontSize = 11.sp,
                            color = if (config.isHumanHandoffActive) TechGold else TextMuted
                        )
                    }

                    Switch(
                        checked = config.isHumanHandoffActive,
                        onCheckedChange = { WhatsAppIntegrationService.setHumanHandoff(it) },
                        colors = SwitchDefaults.colors(
                            checkedThumbColor = TechGold,
                            checkedTrackColor = Color(0xFF382B14)
                        ),
                        modifier = Modifier.testTag("human_handoff_switch")
                    )
                }
            }
        }

        // Complete 7-Step Architecture Flow Diagram
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(14.dp),
            colors = CardDefaults.cardColors(containerColor = TechDarkSurface),
            border = BorderStroke(1.dp, TechDarkBorder)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text(
                    text = "End-to-End WhatsApp Automation Flow",
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold,
                    color = TechCyan
                )
                Spacer(modifier = Modifier.height(10.dp))

                val steps = listOf(
                    "1. Customer sends message on WhatsApp (+91 9919900020)",
                    "2. Meta WhatsApp Webhook receives payload",
                    "3. Incoming message stored in conversation repository",
                    "4. AI Lead Assistant classifies intent & urgency",
                    "5. Tailored Hindi WhatsApp reply generated",
                    "6. Dispatched via WhatsApp Cloud API",
                    "7. Customer receives instant response & CRM logs thread"
                )

                steps.forEach { step ->
                    Row(
                        modifier = Modifier.padding(vertical = 4.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(
                            modifier = Modifier
                                .size(6.dp)
                                .clip(CircleShape)
                                .background(TechCyan)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = step,
                            fontSize = 11.sp,
                            color = TextWhite.copy(alpha = 0.85f),
                            lineHeight = 16.sp
                        )
                    }
                }
            }
        }

        // Live Webhook Pipeline Simulator
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(14.dp),
            colors = CardDefaults.cardColors(containerColor = TechDarkSurface),
            border = BorderStroke(1.dp, TechDarkBorder)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.PlayArrow, contentDescription = null, tint = WhatsAppGreen, modifier = Modifier.size(18.dp))
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "Webhook Simulator (Test Architecture)",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold,
                        color = TextWhite
                    )
                }

                Text(
                    text = "Simulate an incoming WhatsApp webhook message to test the AI reply & storage pipeline:",
                    fontSize = 11.sp,
                    color = TextMuted
                )

                OutlinedTextField(
                    value = simulatorSender,
                    onValueChange = { simulatorSender = it },
                    label = { Text("Customer Name", fontSize = 11.sp) },
                    singleLine = true,
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(8.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedTextColor = TextWhite,
                        unfocusedTextColor = TextWhite,
                        focusedBorderColor = TechCyan,
                        unfocusedBorderColor = TechDarkBorder
                    )
                )

                OutlinedTextField(
                    value = simulatorInput,
                    onValueChange = { simulatorInput = it },
                    label = { Text("Incoming WhatsApp Message", fontSize = 11.sp) },
                    modifier = Modifier.fillMaxWidth(),
                    shape = RoundedCornerShape(8.dp),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedTextColor = TextWhite,
                        unfocusedTextColor = TextWhite,
                        focusedBorderColor = TechCyan,
                        unfocusedBorderColor = TechDarkBorder
                    )
                )

                Button(
                    onClick = {
                        val testMsg = WhatsAppWebhookMessage(
                            from = "919919900020",
                            id = "sim_" + UUID.randomUUID().toString().take(6),
                            timestamp = System.currentTimeMillis().toString(),
                            text = simulatorInput,
                            senderName = simulatorSender
                        )
                        WhatsAppIntegrationService.processIncomingWebhookMessage(testMsg)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(44.dp)
                        .testTag("simulate_webhook_btn"),
                    shape = RoundedCornerShape(10.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = TechDarkSurfaceVariant,
                        contentColor = TechCyan
                    ),
                    border = BorderStroke(1.dp, TechCyan.copy(alpha = 0.5f))
                ) {
                    Icon(Icons.Default.Sync, contentDescription = null, modifier = Modifier.size(16.dp))
                    Spacer(modifier = Modifier.width(6.dp))
                    Text("Trigger Webhook Pipeline Test", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                }
            }
        }

        // Required Credentials Checklist
        Card(
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(14.dp),
            colors = CardDefaults.cardColors(containerColor = TechDarkSurface),
            border = BorderStroke(1.dp, TechDarkBorder)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(Icons.Default.Key, contentDescription = null, tint = TechGold, modifier = Modifier.size(18.dp))
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Production Credentials Required",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold,
                        color = TextWhite
                    )
                }

                Text(
                    text = "Meta WhatsApp Cloud API के लिए आवश्यक सर्वर पर्यावरण चर (Environment Variables):",
                    fontSize = 11.sp,
                    color = TextMuted
                )

                config.requiredEnvVars.forEach { key ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(6.dp))
                            .background(TechDarkSurfaceVariant)
                            .padding(horizontal = 10.dp, vertical = 6.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(key, fontSize = 11.sp, color = TechCyan, fontWeight = FontWeight.Bold)
                        Text("Config Required", fontSize = 10.sp, color = TechGold)
                    }
                }

                Spacer(modifier = Modifier.height(2.dp))

                Text(
                    text = "⚠️ सुरक्षा निर्देश: किसी भी टोकन या पासवर्ड को फ्रंटएंड कोड में कभी न रखें। इन्हें केवल AI Studio Secrets या सर्वर-साइड पर्यावरण में ही सुरक्षित रखें।",
                    fontSize = 11.sp,
                    color = TextMuted,
                    lineHeight = 16.sp
                )
            }
        }

        // Pipeline Conversation History Log
        if (history.isNotEmpty()) {
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(14.dp),
                colors = CardDefaults.cardColors(containerColor = TechDarkSurface),
                border = BorderStroke(1.dp, TechDarkBorder)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Webhook Message Log (${history.size})",
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold,
                            color = TextWhite
                        )
                        TextButton(onClick = { WhatsAppIntegrationService.clearConversationHistory() }) {
                            Text("Clear", color = TextMuted, fontSize = 11.sp)
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                        history.takeLast(6).reversed().forEach { entry ->
                            Box(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .clip(RoundedCornerShape(8.dp))
                                    .background(if (entry.isIncoming) Color(0xFF16252F) else Color(0xFF132A32))
                                    .border(
                                        BorderStroke(
                                            1.dp,
                                            if (entry.isIncoming) TechCyan.copy(alpha = 0.3f) else WhatsAppGreen.copy(alpha = 0.3f)
                                        ),
                                        RoundedCornerShape(8.dp)
                                    )
                                    .padding(10.dp)
                            ) {
                                Column {
                                    Row(
                                        modifier = Modifier.fillMaxWidth(),
                                        horizontalArrangement = Arrangement.SpaceBetween
                                    ) {
                                        Text(
                                            text = if (entry.isIncoming) "📥 ${entry.senderName}" else "🤖 ${entry.senderName}",
                                            fontSize = 11.sp,
                                            fontWeight = FontWeight.Bold,
                                            color = if (entry.isIncoming) TechCyan else WhatsAppGreen
                                        )
                                        if (entry.aiClassification != null) {
                                            Text(
                                                text = "${entry.aiClassification.emoji} ${entry.aiClassification.label}",
                                                fontSize = 10.sp,
                                                color = Color(entry.aiClassification.colorHex)
                                            )
                                        }
                                    }
                                    Spacer(modifier = Modifier.height(4.dp))
                                    Text(
                                        text = entry.messageText,
                                        fontSize = 12.sp,
                                        color = TextWhite,
                                        lineHeight = 16.sp
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
