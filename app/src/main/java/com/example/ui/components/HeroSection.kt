package com.example.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Chat
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Speed
import androidx.compose.material.icons.filled.SupportAgent
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.R
import com.example.model.BusinessConstants
import com.example.ui.theme.TechCyan
import com.example.ui.theme.TechCyanBadgeBg
import com.example.ui.theme.TechDarkBorder
import com.example.ui.theme.TechDarkSurface
import com.example.ui.theme.TechDarkSurfaceVariant
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextWhite
import com.example.ui.theme.WhatsAppGreen
import com.example.ui.theme.WhatsAppGreenLight

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun HeroSection(
    modifier: Modifier = Modifier,
    onWhatsAppClick: () -> Unit,
    onDemoClick: () -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // AI Badge
        Box(
            modifier = Modifier
                .clip(RoundedCornerShape(30.dp))
                .background(TechCyanBadgeBg)
                .borderBadge(TechCyan.copy(alpha = 0.6f))
                .padding(horizontal = 14.dp, vertical = 6.dp)
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
                    text = "DEORIA AI HUB • SMART AUTOMATION",
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    color = TechCyan,
                    letterSpacing = 0.8.sp
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Main Heading
        Text(
            text = "AI Automation & WhatsApp Chatbot for Your Business",
            fontSize = 25.sp,
            lineHeight = 33.sp,
            fontWeight = FontWeight.ExtraBold,
            color = TextWhite,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(horizontal = 8.dp)
        )

        Spacer(modifier = Modifier.height(12.dp))

        // Hindi Subheading (as specified in user prompt)
        Text(
            text = "अपने Business की Enquiry, Lead और Customer Support को AI से Automate करें।",
            fontSize = 15.sp,
            lineHeight = 22.sp,
            fontWeight = FontWeight.Medium,
            color = TextMuted,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(horizontal = 12.dp)
        )

        Spacer(modifier = Modifier.height(20.dp))

        // Primary Buttons (as requested)
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            // "WhatsApp पर बात करें"
            Button(
                onClick = onWhatsAppClick,
                modifier = Modifier
                    .weight(1f)
                    .height(52.dp)
                    .testTag("hero_whatsapp_button"),
                shape = RoundedCornerShape(12.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = WhatsAppGreen,
                    contentColor = Color.Black
                ),
                elevation = ButtonDefaults.buttonElevation(defaultElevation = 4.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Chat,
                    contentDescription = null,
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "WhatsApp पर बात करें",
                    fontWeight = FontWeight.Bold,
                    fontSize = 13.sp
                )
            }

            // "Free Demo"
            OutlinedButton(
                onClick = onDemoClick,
                modifier = Modifier
                    .weight(1f)
                    .height(52.dp)
                    .testTag("hero_free_demo_button"),
                shape = RoundedCornerShape(12.dp),
                border = BorderStroke(1.5.dp, TechCyan),
                colors = ButtonDefaults.outlinedButtonColors(
                    contentColor = TechCyan
                )
            ) {
                Icon(
                    imageVector = Icons.Default.PlayArrow,
                    contentDescription = null,
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = "Free Demo",
                    fontWeight = FontWeight.Bold,
                    fontSize = 13.sp
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        // WhatsApp Direct Helpline indicator
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(horizontal = 8.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(6.dp)
                    .clip(CircleShape)
                    .background(WhatsAppGreenLight)
            )
            Spacer(modifier = Modifier.width(6.dp))
            Text(
                text = "आधिकारिक WhatsApp हेल्पलाइन: ${BusinessConstants.WHATSAPP_DISPLAY}",
                fontSize = 12.sp,
                color = WhatsAppGreenLight,
                fontWeight = FontWeight.SemiBold
            )
        }

        Spacer(modifier = Modifier.height(24.dp))

        // Hero Graphic Banner
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(16.dp)),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(containerColor = TechDarkSurface),
            border = BorderStroke(1.dp, TechDarkBorder)
        ) {
            Box(modifier = Modifier.fillMaxWidth()) {
                Image(
                    painter = painterResource(id = R.drawable.img_hero_ai_hub),
                    contentDescription = "AI Automation Hub Hero Interface",
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(16f / 9f),
                    contentScale = ContentScale.Crop
                )

                // Sleek Gradient Overlay at bottom
                Box(
                    modifier = Modifier
                        .matchParentSize()
                        .background(
                            Brush.verticalGradient(
                                colors = listOf(Color.Transparent, TechDarkSurface.copy(alpha = 0.85f)),
                                startY = 180f
                            )
                        )
                )

                // Floating feature badge on top of image
                Row(
                    modifier = Modifier
                        .align(Alignment.BottomStart)
                        .padding(12.dp)
                        .clip(RoundedCornerShape(8.dp))
                        .background(TechDarkSurfaceVariant.copy(alpha = 0.92f))
                        .borderBadge(TechDarkBorder)
                        .padding(horizontal = 10.dp, vertical = 6.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.CheckCircle,
                        contentDescription = null,
                        tint = WhatsAppGreen,
                        modifier = Modifier.size(16.dp)
                    )
                    Spacer(modifier = Modifier.width(6.dp))
                    Text(
                        text = "WhatsApp Lead Automation",
                        color = TextWhite,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(18.dp))

        // Quick Value Pillars
        FlowRow(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            PillarChip(icon = Icons.Default.Speed, label = "24/7 Instant Auto Reply")
            PillarChip(icon = Icons.Default.SupportAgent, label = "Zero Lead Drop")
            PillarChip(icon = Icons.Default.AutoAwesome, label = "Deoria & UP Local Support")
        }
    }
}

@Composable
private fun PillarChip(
    icon: androidx.compose.ui.graphics.vector.ImageVector,
    label: String
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .clip(RoundedCornerShape(8.dp))
            .background(TechDarkSurfaceVariant)
            .borderBadge(TechDarkBorder)
            .padding(horizontal = 10.dp, vertical = 6.dp)
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = TechCyan,
            modifier = Modifier.size(14.dp)
        )
        Spacer(modifier = Modifier.width(6.dp))
        Text(
            text = label,
            fontSize = 11.sp,
            color = TextMuted,
            fontWeight = FontWeight.Medium
        )
    }
}

private fun Modifier.borderBadge(color: Color): Modifier = this.border(
    width = 1.dp,
    color = color,
    shape = RoundedCornerShape(30.dp)
)
