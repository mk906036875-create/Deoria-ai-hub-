package com.example.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
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
import androidx.compose.material.icons.filled.Autorenew
import androidx.compose.material.icons.filled.FactCheck
import androidx.compose.material.icons.filled.FlashOn
import androidx.compose.material.icons.filled.NotificationsActive
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.AppDataSource
import com.example.model.StepItem
import com.example.ui.theme.TechCyan
import com.example.ui.theme.TechDarkBorder
import com.example.ui.theme.TechDarkSurface
import com.example.ui.theme.TechDarkSurfaceVariant
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextWhite
import com.example.ui.theme.WhatsAppGreen

@Composable
fun HowItWorksSection(
    modifier: Modifier = Modifier
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
                    .background(WhatsAppGreen)
            )
            Spacer(modifier = Modifier.width(10.dp))
            Column {
                Text(
                    text = "How It Works (सिस्टम कैसे काम करता है)",
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontWeight = FontWeight.Bold,
                        color = TextWhite,
                        fontSize = 19.sp
                    )
                )
                Text(
                    text = "5-Step Automated Lead Conversion Pipeline",
                    style = MaterialTheme.typography.bodySmall.copy(
                        color = WhatsAppGreen,
                        fontSize = 12.sp
                    )
                )
            }
        }

        Spacer(modifier = Modifier.height(18.dp))

        // Steps List with visual connection
        Column(modifier = Modifier.fillMaxWidth()) {
            AppDataSource.steps.forEachIndexed { index, step ->
                StepRowItem(
                    step = step,
                    isLast = index == AppDataSource.steps.size - 1
                )
            }
        }
    }
}

@Composable
private fun StepRowItem(
    step: StepItem,
    isLast: Boolean
) {
    val icon = when (step.iconName) {
        "Send" -> Icons.Default.Send
        "FlashOn" -> Icons.Default.FlashOn
        "FactCheck" -> Icons.Default.FactCheck
        "NotificationsActive" -> Icons.Default.NotificationsActive
        else -> Icons.Default.Autorenew
    }

    Row(modifier = Modifier.fillMaxWidth()) {
        // Step indicator & vertical connector line
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.width(44.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(36.dp)
                    .clip(CircleShape)
                    .background(if (step.stepNumber == 1 || step.stepNumber == 4) WhatsAppGreen else TechCyan),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "${step.stepNumber}",
                    color = androidx.compose.ui.graphics.Color.Black,
                    fontWeight = FontWeight.Bold,
                    fontSize = 14.sp
                )
            }

            if (!isLast) {
                Box(
                    modifier = Modifier
                        .width(2.dp)
                        .height(80.dp)
                        .background(TechDarkBorder)
                )
            }
        }

        Spacer(modifier = Modifier.width(12.dp))

        // Step Card Details
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = if (isLast) 0.dp else 16.dp),
            shape = RoundedCornerShape(14.dp),
            colors = CardDefaults.cardColors(containerColor = TechDarkSurface),
            border = BorderStroke(1.dp, TechDarkBorder)
        ) {
            Column(modifier = Modifier.padding(14.dp)) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(28.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .background(TechDarkSurfaceVariant),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = icon,
                            contentDescription = null,
                            tint = TechCyan,
                            modifier = Modifier.size(16.dp)
                        )
                    }

                    Spacer(modifier = Modifier.width(10.dp))

                    Column {
                        Text(
                            text = "Step ${step.stepNumber}: ${step.titleEn}",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold,
                            color = TextWhite
                        )
                        Text(
                            text = step.titleHi,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Medium,
                            color = WhatsAppGreen
                        )
                    }
                }

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = step.descriptionHi,
                    fontSize = 12.sp,
                    lineHeight = 17.sp,
                    color = TextWhite
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = step.descriptionEn,
                    fontSize = 11.sp,
                    color = TextMuted
                )
            }
        }
    }
}
