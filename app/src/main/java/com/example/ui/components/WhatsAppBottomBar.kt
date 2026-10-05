package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Assignment
import androidx.compose.material.icons.filled.Chat
import androidx.compose.material.icons.filled.Dashboard
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.LocalOffer
import androidx.compose.material.icons.filled.MiscellaneousServices
import androidx.compose.material.icons.filled.SmartToy
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.BusinessConstants
import com.example.ui.theme.TechCyan
import com.example.ui.theme.TechDarkBorder
import com.example.ui.theme.TechDarkSurface
import com.example.ui.theme.TextMuted
import com.example.ui.theme.WhatsAppGreen
import com.example.viewmodel.AppSection

@Composable
fun WhatsAppBottomBar(
    currentSection: AppSection,
    onSectionSelected: (AppSection) -> Unit,
    onQuickWhatsAppClick: () -> Unit
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .windowInsetsPadding(WindowInsets.navigationBars),
        color = TechDarkSurface,
        border = androidx.compose.foundation.BorderStroke(1.dp, TechDarkBorder),
        tonalElevation = 8.dp
    ) {
        Column(modifier = Modifier.fillMaxWidth()) {
            // Sticky Quick WhatsApp Action strip
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color(0xFF0F1A24))
                    .padding(horizontal = 12.dp, vertical = 6.dp)
            ) {
                Button(
                    onClick = onQuickWhatsAppClick,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(44.dp)
                        .testTag("bottom_sticky_whatsapp_button"),
                    shape = RoundedCornerShape(10.dp),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = WhatsAppGreen,
                        contentColor = Color.Black
                    )
                ) {
                    Icon(
                        imageVector = Icons.Default.Chat,
                        contentDescription = null,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "WhatsApp पर बात करें (${BusinessConstants.WHATSAPP_DISPLAY})",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold
                    )
                }
            }

            // Navigation bar items
            NavigationBar(
                containerColor = TechDarkSurface,
                modifier = Modifier.height(60.dp)
            ) {
                val items = listOf(
                    Triple(AppSection.HOME, "Home", Icons.Default.Home),
                    Triple(AppSection.SERVICES, "Services", Icons.Default.MiscellaneousServices),
                    Triple(AppSection.PACKAGES, "Plans", Icons.Default.LocalOffer),
                    Triple(AppSection.CHATBOT_DEMO, "Demo", Icons.Default.SmartToy),
                    Triple(AppSection.LEAD_FORM, "Enquiry", Icons.Default.Assignment),
                    Triple(AppSection.LEAD_DASHBOARD, "CRM", Icons.Default.Dashboard)
                )

                items.forEach { (section, label, icon) ->
                    val isSelected = currentSection == section
                    val activeColor = when (section) {
                        AppSection.LEAD_DASHBOARD -> TechCyan
                        AppSection.CHATBOT_DEMO -> TechCyan
                        else -> WhatsAppGreen
                    }

                    NavigationBarItem(
                        selected = isSelected,
                        onClick = { onSectionSelected(section) },
                        icon = {
                            Icon(
                                imageVector = icon,
                                contentDescription = label,
                                modifier = Modifier.size(20.dp)
                            )
                        },
                        label = {
                            Text(
                                text = label,
                                fontSize = 10.sp,
                                fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                            )
                        },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = Color.Black,
                            selectedTextColor = activeColor,
                            indicatorColor = activeColor,
                            unselectedIconColor = TextMuted,
                            unselectedTextColor = TextMuted
                        ),
                        modifier = Modifier.testTag("nav_item_${section.name.lowercase()}")
                    )
                }
            }
        }
    }
}
