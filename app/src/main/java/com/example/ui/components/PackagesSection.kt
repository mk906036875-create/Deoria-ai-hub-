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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Star
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.AppDataSource
import com.example.model.PackageItem
import com.example.ui.theme.TechCyan
import com.example.ui.theme.TechDarkBorder
import com.example.ui.theme.TechDarkSurface
import com.example.ui.theme.TechDarkSurfaceVariant
import com.example.ui.theme.TechGold
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextWhite
import com.example.ui.theme.WhatsAppGreen

@Composable
fun PackagesSection(
    modifier: Modifier = Modifier,
    onSelectPackage: (String) -> Unit
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
                    .background(TechGold)
            )
            Spacer(modifier = Modifier.width(10.dp))
            Column {
                Text(
                    text = "Packages & Pricing",
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontWeight = FontWeight.Bold,
                        color = TextWhite,
                        fontSize = 20.sp
                    )
                )
                Text(
                    text = "पारदर्शी और किफायती प्लान्स (No Hidden Charges)",
                    style = MaterialTheme.typography.bodySmall.copy(
                        color = TechGold,
                        fontSize = 12.sp
                    )
                )
            }
        }

        Spacer(modifier = Modifier.height(18.dp))

        // Packages List
        Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
            AppDataSource.packages.forEach { pkg ->
                PackageCard(
                    pkg = pkg,
                    onSelect = { onSelectPackage(pkg.id) }
                )
            }
        }
    }
}

@Composable
private fun PackageCard(
    pkg: PackageItem,
    onSelect: () -> Unit
) {
    val borderColor = if (pkg.isPopular) WhatsAppGreen else TechDarkBorder
    val borderWidth = if (pkg.isPopular) 2.dp else 1.dp

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .testTag("package_card_${pkg.id}"),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = TechDarkSurface),
        border = BorderStroke(borderWidth, borderColor)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(18.dp)
        ) {
            // Header with Popular Badge
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = pkg.name,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = TextWhite
                )

                if (pkg.isPopular) {
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(20.dp))
                            .background(WhatsAppGreen)
                            .padding(horizontal = 10.dp, vertical = 4.dp)
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(
                                imageVector = Icons.Default.Star,
                                contentDescription = null,
                                tint = Color.Black,
                                modifier = Modifier.size(12.dp)
                            )
                            Spacer(modifier = Modifier.width(4.dp))
                            Text(
                                text = "Most Popular",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                color = Color.Black
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(6.dp))

            // Price
            Row(verticalAlignment = Alignment.Bottom) {
                Text(
                    text = pkg.price,
                    fontSize = 28.sp,
                    fontWeight = FontWeight.ExtraBold,
                    color = if (pkg.isPopular) WhatsAppGreen else TechCyan
                )
                Spacer(modifier = Modifier.width(6.dp))
                Text(
                    text = "एकमुश्त सेटअप शुल्क",
                    fontSize = 12.sp,
                    color = TextMuted,
                    modifier = Modifier.padding(bottom = 4.dp)
                )
            }

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = pkg.tagline,
                fontSize = 12.sp,
                color = TextMuted
            )

            Spacer(modifier = Modifier.height(6.dp))

            // Best For Tag
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(6.dp))
                    .background(TechDarkSurfaceVariant)
                    .padding(horizontal = 8.dp, vertical = 4.dp)
            ) {
                Text(
                    text = "उपयुक्त: ${pkg.bestFor}",
                    fontSize = 11.sp,
                    color = TechCyan,
                    fontWeight = FontWeight.Medium
                )
            }

            Spacer(modifier = Modifier.height(14.dp))

            // Feature list
            Column(verticalArrangement = Arrangement.spacedBy(8.dp)) {
                pkg.features.forEach { feature ->
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = Icons.Default.CheckCircle,
                            contentDescription = null,
                            tint = if (pkg.isPopular) WhatsAppGreen else TechCyan,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = feature,
                            fontSize = 13.sp,
                            color = TextWhite
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Select Plan CTA
            Button(
                onClick = onSelect,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp)
                    .testTag("package_select_btn_${pkg.id}"),
                shape = RoundedCornerShape(10.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (pkg.isPopular) WhatsAppGreen else TechDarkSurfaceVariant,
                    contentColor = if (pkg.isPopular) Color.Black else TextWhite
                ),
                border = if (!pkg.isPopular) BorderStroke(1.dp, TechDarkBorder) else null
            ) {
                Text(
                    text = "यह प्लान चुनें (Get Started)",
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}
