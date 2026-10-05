package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material.icons.filled.Chat
import androidx.compose.material.icons.filled.Dashboard
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.model.BusinessConstants
import com.example.ui.theme.TechCyan
import com.example.ui.theme.TechDarkBorder
import com.example.ui.theme.TechDarkSurface
import com.example.ui.theme.TechDarkSurfaceVariant
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextWhite
import com.example.ui.theme.WhatsAppGreen
import com.example.util.WhatsAppHelper

@Composable
fun AppTopBar(
    modifier: Modifier = Modifier,
    leadCount: Int = 0,
    onLeadsClick: () -> Unit,
    onWhatsAppClick: () -> Unit
) {
    val context = LocalContext.current

    Surface(
        modifier = modifier.fillMaxWidth(),
        color = TechDarkSurface,
        tonalElevation = 6.dp,
        border = androidx.compose.foundation.BorderStroke(1.dp, TechDarkBorder)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 14.dp, vertical = 10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // App Logo
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(Color(0xFF0A1128)),
                contentAlignment = Alignment.Center
            ) {
                androidx.compose.foundation.Image(
                    painter = painterResource(id = R.drawable.img_deoria_icon),
                    contentDescription = "Deoria AI Hub Logo",
                    modifier = Modifier.size(32.dp)
                )
            }

            Spacer(modifier = Modifier.width(10.dp))

            Column(modifier = Modifier.weight(1f)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Text(
                        text = "Deoria AI Hub",
                        style = MaterialTheme.typography.titleMedium.copy(
                            fontWeight = FontWeight.Bold,
                            color = TextWhite,
                            fontSize = 16.sp
                        )
                    )
                    Spacer(modifier = Modifier.width(5.dp))
                    // Online Pulse Dot
                    Box(
                        modifier = Modifier
                            .size(7.dp)
                            .clip(CircleShape)
                            .background(WhatsAppGreen)
                    )
                }

                Text(
                    text = "AI Automation • WhatsApp Chatbot",
                    style = MaterialTheme.typography.bodySmall.copy(
                        color = TechCyan,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Medium
                    )
                )
            }

            // Leads CRM Shortcut with count badge
            IconButton(
                onClick = onLeadsClick,
                modifier = Modifier.testTag("top_bar_leads_button")
            ) {
                BadgedBox(
                    badge = {
                        if (leadCount > 0) {
                            Badge(
                                containerColor = Color(0xFFEF4444),
                                contentColor = Color.White
                            ) {
                                Text("$leadCount", fontSize = 9.sp, fontWeight = FontWeight.Bold)
                            }
                        }
                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.Dashboard,
                        contentDescription = "Leads CRM",
                        tint = TechCyan,
                        modifier = Modifier.size(22.dp)
                    )
                }
            }

            // Quick Call CTA
            IconButton(
                onClick = { WhatsAppHelper.dialPhoneNumber(context, BusinessConstants.WHATSAPP_DISPLAY) },
                modifier = Modifier.testTag("top_bar_call_button")
            ) {
                Icon(
                    imageVector = Icons.Default.Call,
                    contentDescription = "कॉल करें",
                    tint = TextMuted,
                    modifier = Modifier.size(20.dp)
                )
            }

            // Quick WhatsApp CTA
            IconButton(
                onClick = onWhatsAppClick,
                modifier = Modifier.testTag("top_bar_whatsapp_button")
            ) {
                Icon(
                    imageVector = Icons.Default.Chat,
                    contentDescription = "WhatsApp चैट करें",
                    tint = WhatsAppGreen,
                    modifier = Modifier.size(22.dp)
                )
            }
        }
    }
}
