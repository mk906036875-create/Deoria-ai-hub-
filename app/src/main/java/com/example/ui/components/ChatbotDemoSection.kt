package com.example.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Chat
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material.icons.filled.Send
import androidx.compose.material.icons.filled.SmartToy
import androidx.compose.material.icons.filled.Verified
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import com.example.model.ChatMessage
import com.example.model.MessageSender
import com.example.ui.theme.TechCyan
import com.example.ui.theme.TechDarkBorder
import com.example.ui.theme.TechDarkSurface
import com.example.ui.theme.TechDarkSurfaceVariant
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextWhite
import com.example.ui.theme.WhatsAppGreen
import com.example.ui.theme.WhatsAppGreenDark

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun ChatbotDemoSection(
    modifier: Modifier = Modifier,
    messages: List<ChatMessage>,
    isBotTyping: Boolean,
    quickChips: List<String>,
    onChipClick: (String) -> Unit,
    onSendMessage: (String) -> Unit,
    onResetChat: () -> Unit,
    onOpenWhatsApp: (String) -> Unit
) {
    var customInputText by remember { mutableStateOf("") }
    val listState = rememberLazyListState()

    // Auto-scroll when messages update
    LaunchedEffect(messages.size, isBotTyping) {
        if (messages.isNotEmpty()) {
            listState.animateScrollToItem(messages.size - 1)
        }
    }

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
                        .height(22.dp)
                        .clip(RoundedCornerShape(2.dp))
                        .background(WhatsAppGreen)
                )
                Spacer(modifier = Modifier.width(10.dp))
                Column {
                    Text(
                        text = "Interactive AI Chatbot Demo",
                        style = MaterialTheme.typography.titleLarge.copy(
                            fontWeight = FontWeight.Bold,
                            color = TextWhite,
                            fontSize = 19.sp
                        )
                    )
                    Text(
                        text = "देखें WhatsApp Bot कैसे काम करता है",
                        style = MaterialTheme.typography.bodySmall.copy(
                            color = WhatsAppGreen,
                            fontSize = 12.sp
                        )
                    )
                }
            }

            IconButton(
                onClick = onResetChat,
                modifier = Modifier.testTag("reset_chat_button")
            ) {
                Icon(
                    imageVector = Icons.Default.Refresh,
                    contentDescription = "रीसेट चैट",
                    tint = TextMuted
                )
            }
        }

        Spacer(modifier = Modifier.height(14.dp))

        // Live Chat Container
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .testTag("chatbot_container"),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = TechDarkSurface),
            border = BorderStroke(1.5.dp, WhatsAppGreen.copy(alpha = 0.5f))
        ) {
            Column(modifier = Modifier.fillMaxWidth()) {
                // Chat Header (WhatsApp style)
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(WhatsAppGreenDark)
                        .padding(horizontal = 14.dp, vertical = 10.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Box(
                        modifier = Modifier
                            .size(36.dp)
                            .clip(CircleShape)
                            .background(Color.White.copy(alpha = 0.2f)),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.SmartToy,
                            contentDescription = null,
                            tint = Color.White,
                            modifier = Modifier.size(20.dp)
                        )
                    }

                    Spacer(modifier = Modifier.width(10.dp))

                    Column(modifier = Modifier.weight(1f)) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Text(
                                text = "Deoria AI Assistant",
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.White
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Icon(
                                imageVector = Icons.Default.Verified,
                                contentDescription = "Verified Bot",
                                tint = TechCyan,
                                modifier = Modifier.size(14.dp)
                            )
                        }
                        Text(
                            text = if (isBotTyping) "typing..." else "Online • 2-Sec Instant Reply",
                            fontSize = 11.sp,
                            color = Color.White.copy(alpha = 0.85f)
                        )
                    }

                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(6.dp))
                            .background(Color.Black.copy(alpha = 0.25f))
                            .padding(horizontal = 6.dp, vertical = 2.dp)
                    ) {
                        Text(
                            text = "DEMO BOT",
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                    }
                }

                // Chat Messages List
                LazyColumn(
                    state = listState,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(300.dp)
                        .background(Color(0xFF090E17))
                        .padding(horizontal = 12.dp, vertical = 12.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    items(messages, key = { it.id }) { msg ->
                        ChatBubble(
                            message = msg,
                            onActionClick = { onOpenWhatsApp(BusinessConstants.buildQuickTopicMessage(msg.text.take(30))) }
                        )
                    }

                    if (isBotTyping) {
                        item {
                            TypingIndicator()
                        }
                    }
                }

                // Quick Prompt Selection Chips (as specified in prompt)
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(TechDarkSurfaceVariant)
                        .padding(horizontal = 10.dp, vertical = 10.dp)
                ) {
                    Text(
                        text = "त्वरित विकल्प चुनें (Quick Options):",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = TechCyan,
                        modifier = Modifier.padding(bottom = 6.dp)
                    )

                    FlowRow(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(6.dp),
                        verticalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        quickChips.forEach { chipText ->
                            FilterChip(
                                selected = false,
                                onClick = { onChipClick(chipText) },
                                label = {
                                    Text(
                                        text = chipText,
                                        fontSize = 12.sp,
                                        fontWeight = FontWeight.Medium
                                    )
                                },
                                shape = RoundedCornerShape(20.dp),
                                colors = FilterChipDefaults.filterChipColors(
                                    containerColor = TechDarkSurface,
                                    labelColor = TextWhite
                                ),
                                border = BorderStroke(1.dp, TechDarkBorder),
                                modifier = Modifier.testTag("chip_${chipText.take(6)}")
                            )
                        }
                    }
                }

                // Custom Query Input Field
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(TechDarkSurface)
                        .padding(horizontal = 10.dp, vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    OutlinedTextField(
                        value = customInputText,
                        onValueChange = { customInputText = it },
                        placeholder = {
                            Text(
                                text = "अपना सवाल यहाँ टाइप करें...",
                                fontSize = 13.sp,
                                color = TextMuted
                            )
                        },
                        modifier = Modifier
                            .weight(1f)
                            .testTag("chat_input_field"),
                        shape = RoundedCornerShape(24.dp),
                        singleLine = true,
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedTextColor = TextWhite,
                            unfocusedTextColor = TextWhite,
                            focusedBorderColor = WhatsAppGreen,
                            unfocusedBorderColor = TechDarkBorder,
                            focusedContainerColor = TechDarkSurfaceVariant,
                            unfocusedContainerColor = TechDarkSurfaceVariant
                        )
                    )

                    Spacer(modifier = Modifier.width(8.dp))

                    IconButton(
                        onClick = {
                            if (customInputText.isNotBlank()) {
                                onSendMessage(customInputText)
                                customInputText = ""
                            }
                        },
                        modifier = Modifier
                            .size(46.dp)
                            .clip(CircleShape)
                            .background(WhatsAppGreen)
                            .testTag("chat_send_button")
                    ) {
                        Icon(
                            imageVector = Icons.Default.Send,
                            contentDescription = "भेजें",
                            tint = Color.Black,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        // Compliance notice
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.Info,
                contentDescription = null,
                tint = TextMuted,
                modifier = Modifier.size(14.dp)
            )
            Spacer(modifier = Modifier.width(6.dp))
            Text(
                text = "यह एक इंटरैक्टिव डेमो है। वास्तविक ऑटोमेशन आपके बिज़नेस नंबर से कनेक्ट होने पर सक्रिय होता है।",
                fontSize = 11.sp,
                color = TextMuted,
                lineHeight = 15.sp
            )
        }
    }
}

@Composable
private fun ChatBubble(
    message: ChatMessage,
    onActionClick: () -> Unit
) {
    val isUser = message.sender == MessageSender.USER

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = if (isUser) Alignment.End else Alignment.Start
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth(0.88f)
                .clip(
                    RoundedCornerShape(
                        topStart = 14.dp,
                        topEnd = 14.dp,
                        bottomStart = if (isUser) 14.dp else 2.dp,
                        bottomEnd = if (isUser) 2.dp else 14.dp
                    )
                )
                .background(if (isUser) Color(0xFF005C4B) else Color(0xFF1F2C34))
                .padding(12.dp)
        ) {
            Column {
                if (!isUser) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .padding(bottom = 6.dp)
                            .clip(RoundedCornerShape(4.dp))
                            .background(Color(0xFF0F2027))
                            .padding(horizontal = 6.dp, vertical = 2.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.AutoAwesome,
                            contentDescription = null,
                            tint = TechCyan,
                            modifier = Modifier.size(11.dp)
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Text(
                            text = "AI Automated Response",
                            fontSize = 9.sp,
                            fontWeight = FontWeight.Bold,
                            color = TechCyan
                        )
                    }
                }

                Text(
                    text = message.text,
                    fontSize = 13.sp,
                    lineHeight = 19.sp,
                    color = TextWhite
                )

                // If it's a bot reply offering action
                if (!isUser && message.actionPayload != null) {
                    Spacer(modifier = Modifier.height(10.dp))
                    Button(
                        onClick = onActionClick,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(38.dp),
                        shape = RoundedCornerShape(8.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = WhatsAppGreen,
                            contentColor = Color.Black
                        )
                    ) {
                        Icon(
                            imageVector = Icons.Default.Chat,
                            contentDescription = null,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "WhatsApp पर बात आगे बढ़ाएं",
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = message.timestamp,
                    fontSize = 10.sp,
                    color = Color.White.copy(alpha = 0.6f),
                    modifier = Modifier.align(Alignment.End)
                )
            }
        }
    }
}

@Composable
private fun TypingIndicator() {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .clip(RoundedCornerShape(12.dp))
            .background(Color(0xFF1F2C34))
            .padding(horizontal = 12.dp, vertical = 8.dp)
    ) {
        CircularProgressIndicator(
            modifier = Modifier.size(14.dp),
            color = WhatsAppGreen,
            strokeWidth = 2.dp
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = "Deoria AI Assistant टाइप कर रहा है...",
            fontSize = 11.sp,
            color = TextMuted
        )
    }
}
