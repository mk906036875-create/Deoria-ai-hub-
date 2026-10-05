package com.example.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
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
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Chat
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.EventAvailable
import androidx.compose.material.icons.filled.HeadsetMic
import androidx.compose.material.icons.filled.Language
import androidx.compose.material.icons.filled.SmartToy
import androidx.compose.material.icons.filled.TrendingUp
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.AppDataSource
import com.example.model.ServiceItem
import com.example.ui.theme.TechCyan
import com.example.ui.theme.TechDarkBorder
import com.example.ui.theme.TechDarkSurface
import com.example.ui.theme.TechDarkSurfaceVariant
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextWhite
import com.example.ui.theme.WhatsAppGreen

@Composable
fun ServicesSection(
    modifier: Modifier = Modifier,
    onSelectServiceForEnquiry: (String) -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 20.dp)
    ) {
        // Section Header
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = Modifier
                    .width(4.dp)
                    .height(22.dp)
                    .clip(RoundedCornerShape(2.dp))
                    .background(TechCyan)
            )
            Spacer(modifier = Modifier.width(10.dp))
            Column {
                Text(
                    text = "हमारे प्रमुख सर्विसेज",
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontWeight = FontWeight.Bold,
                        color = TextWhite,
                        fontSize = 20.sp
                    )
                )
                Text(
                    text = "End-to-end AI & Digital Automation Solutions",
                    style = MaterialTheme.typography.bodySmall.copy(
                        color = TechCyan,
                        fontSize = 12.sp
                    )
                )
            }
        }

        Spacer(modifier = Modifier.height(18.dp))

        // Service Cards
        Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
            AppDataSource.services.forEach { service ->
                ServiceCard(
                    service = service,
                    onEnquiryClick = { onSelectServiceForEnquiry(service.titleEn) }
                )
            }
        }
    }
}

@Composable
private fun ServiceCard(
    service: ServiceItem,
    onEnquiryClick: () -> Unit
) {
    val icon = when (service.iconName) {
        "SmartToy" -> Icons.Default.SmartToy
        "Chat" -> Icons.Default.Chat
        "Language" -> Icons.Default.Language
        "TrendingUp" -> Icons.Default.TrendingUp
        "EventAvailable" -> Icons.Default.EventAvailable
        else -> Icons.Default.HeadsetMic
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .testTag("service_card_${service.id}"),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = TechDarkSurface),
        border = BorderStroke(1.dp, TechDarkBorder)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Box(
                    modifier = Modifier
                        .size(46.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(TechDarkSurfaceVariant),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = icon,
                        contentDescription = service.titleEn,
                        tint = if (service.id == "whatsapp_chatbot") WhatsAppGreen else TechCyan,
                        modifier = Modifier.size(26.dp)
                    )
                }

                Spacer(modifier = Modifier.width(12.dp))

                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = service.titleEn,
                        fontSize = 17.sp,
                        fontWeight = FontWeight.Bold,
                        color = TextWhite
                    )
                    Text(
                        text = service.titleHi,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Medium,
                        color = WhatsAppGreen
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = service.descriptionHi,
                fontSize = 13.sp,
                lineHeight = 19.sp,
                color = TextWhite,
                fontWeight = FontWeight.Normal
            )

            Spacer(modifier = Modifier.height(6.dp))

            Text(
                text = service.descriptionEn,
                fontSize = 12.sp,
                lineHeight = 17.sp,
                color = TextMuted
            )

            Spacer(modifier = Modifier.height(12.dp))

            // Bullet features
            Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                service.features.forEach { feat ->
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .size(16.dp)
                                .clip(CircleShape)
                                .background(TechDarkSurfaceVariant),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.Check,
                                contentDescription = null,
                                tint = TechCyan,
                                modifier = Modifier.size(11.dp)
                            )
                        }
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = feat,
                            fontSize = 12.sp,
                            color = TextMuted
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(14.dp))

            // Action Button
            Button(
                onClick = onEnquiryClick,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(44.dp)
                    .testTag("service_enquiry_btn_${service.id}"),
                shape = RoundedCornerShape(10.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = TechDarkSurfaceVariant,
                    contentColor = TextWhite
                ),
                border = BorderStroke(1.dp, TechDarkBorder)
            ) {
                Text(
                    text = "इस सर्विस के लिए Enquiry करें",
                    fontSize = 13.sp,
                    fontWeight = FontWeight.SemiBold
                )
                Spacer(modifier = Modifier.width(6.dp))
                Icon(
                    imageVector = Icons.Default.ArrowForward,
                    contentDescription = null,
                    modifier = Modifier.size(16.dp),
                    tint = TechCyan
                )
            }
        }
    }
}
