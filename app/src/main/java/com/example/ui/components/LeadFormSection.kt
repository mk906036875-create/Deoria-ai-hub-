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
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Business
import androidx.compose.material.icons.filled.Chat
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.filled.Dashboard
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Phone
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MenuAnchorType
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
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
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.AppDataSource
import com.example.model.BusinessConstants
import com.example.model.LeadFormData
import com.example.ui.theme.TechCyan
import com.example.ui.theme.TechDarkBorder
import com.example.ui.theme.TechDarkSurface
import com.example.ui.theme.TechDarkSurfaceVariant
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextWhite
import com.example.ui.theme.WhatsAppGreen
import com.example.util.WhatsAppHelper

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun LeadFormSection(
    modifier: Modifier = Modifier,
    leadForm: LeadFormData,
    isSubmitted: Boolean,
    errorMessage: String?,
    submittedMessageText: String,
    onNameChange: (String) -> Unit,
    onMobileChange: (String) -> Unit,
    onBusinessTypeChange: (String) -> Unit,
    onServiceChange: (String) -> Unit,
    onBudgetChange: (String) -> Unit,
    onRequirementChange: (String) -> Unit,
    onSubmit: () -> Boolean,
    onReset: () -> Unit,
    onViewInDashboard: (() -> Unit)? = null
) {
    val context = LocalContext.current
    var businessTypeExpanded by remember { mutableStateOf(false) }

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
                    text = "Get Started / Enquiry Form",
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontWeight = FontWeight.Bold,
                        color = TextWhite,
                        fontSize = 20.sp
                    )
                )
                Text(
                    text = "अपने बिज़नेस के लिए तुरंत कोटेशन व कंसल्टेशन पाएं",
                    style = MaterialTheme.typography.bodySmall.copy(
                        color = WhatsAppGreen,
                        fontSize = 12.sp
                    )
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        if (isSubmitted) {
            // SUCCESS & FUNCTIONAL WHATSAPP LEAD DISPATCH CARD
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("lead_submitted_card"),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = TechDarkSurface),
                border = BorderStroke(2.dp, WhatsAppGreen)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(20.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Box(
                        modifier = Modifier
                            .size(54.dp)
                            .clip(CircleShape)
                            .background(WhatsAppGreen),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            imageVector = Icons.Default.CheckCircle,
                            contentDescription = null,
                            tint = Color.Black,
                            modifier = Modifier.size(32.dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    Text(
                        text = "Enquiry Form सबमिट हो गया!",
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = TextWhite
                    )

                    Spacer(modifier = Modifier.height(6.dp))

                    Text(
                        text = "नीचे दिए गए बटन पर क्लिक करके सीधे Deoria AI Hub टीम को WhatsApp पर मैसेज भेजें:",
                        fontSize = 13.sp,
                        color = TextMuted,
                        textAlign = androidx.compose.ui.text.style.TextAlign.Center
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // MANDATORY CTA: "WhatsApp पर Enquiry भेजें"
                    Button(
                        onClick = {
                            WhatsAppHelper.openWhatsApp(
                                context = context,
                                message = submittedMessageText,
                                phoneNumber = BusinessConstants.WHATSAPP_NUMBER_CLEAN
                            )
                        },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(54.dp)
                            .testTag("send_whatsapp_enquiry_button"),
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = WhatsAppGreen,
                            contentColor = Color.Black
                        ),
                        elevation = ButtonDefaults.buttonElevation(defaultElevation = 6.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Chat,
                            contentDescription = null,
                            modifier = Modifier.size(24.dp)
                        )
                        Spacer(modifier = Modifier.width(10.dp))
                        Text(
                            text = "WhatsApp पर Enquiry भेजें",
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    // Target WhatsApp Number
                    Text(
                        text = "To: ${BusinessConstants.WHATSAPP_DISPLAY}",
                        fontSize = 12.sp,
                        color = WhatsAppGreen,
                        fontWeight = FontWeight.SemiBold
                    )

                    Spacer(modifier = Modifier.height(18.dp))

                    // Message Preview Box
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(12.dp))
                            .background(TechDarkSurfaceVariant)
                            .border(1.dp, TechDarkBorder, RoundedCornerShape(12.dp))
                            .padding(14.dp)
                    ) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "तैयार WhatsApp संदेश (Pre-filled):",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                color = TechCyan
                            )

                            IconButton(
                                onClick = {
                                    WhatsAppHelper.copyToClipboard(
                                        context = context,
                                        text = submittedMessageText,
                                        toastMessage = "WhatsApp संदेश कॉपी हो गया"
                                    )
                                },
                                modifier = Modifier.size(28.dp)
                            ) {
                                Icon(
                                    imageVector = Icons.Default.ContentCopy,
                                    contentDescription = "कॉपी करें",
                                    tint = TechCyan,
                                    modifier = Modifier.size(16.dp)
                                )
                            }
                        }

                        Spacer(modifier = Modifier.height(8.dp))

                        Text(
                            text = submittedMessageText,
                            fontSize = 12.sp,
                            lineHeight = 18.sp,
                            color = TextWhite
                        )
                    }

                    if (onViewInDashboard != null) {
                        Spacer(modifier = Modifier.height(10.dp))
                        Button(
                            onClick = onViewInDashboard,
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(44.dp)
                                .testTag("view_in_dashboard_btn"),
                            shape = RoundedCornerShape(10.dp),
                            colors = ButtonDefaults.buttonColors(
                                containerColor = TechDarkSurfaceVariant,
                                contentColor = TechCyan
                            ),
                            border = BorderStroke(1.dp, TechCyan.copy(alpha = 0.5f))
                        ) {
                            Icon(Icons.Default.Dashboard, contentDescription = null, modifier = Modifier.size(16.dp))
                            Spacer(modifier = Modifier.width(6.dp))
                            Text("AI Leads Dashboard में देखें (CRM View)", fontSize = 12.sp, fontWeight = FontWeight.Bold)
                        }
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    // Modify / Fill another form button
                    OutlinedButton(
                        onClick = onReset,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(44.dp)
                            .testTag("edit_enquiry_button"),
                        shape = RoundedCornerShape(10.dp),
                        border = BorderStroke(1.dp, TechDarkBorder),
                        colors = ButtonDefaults.outlinedButtonColors(contentColor = TextMuted)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Edit,
                            contentDescription = null,
                            modifier = Modifier.size(16.dp)
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(
                            text = "फॉर्म एडिट करें / नया फॉर्म भरें",
                            fontSize = 12.sp
                        )
                    }

                    Spacer(modifier = Modifier.height(12.dp))

                    // System setup disclosure
                    Row(
                        modifier = Modifier.fillMaxWidth(),
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
                            text = "WhatsApp API ऑटोमेशन बिज़नेस नंबर से कनेक्ट होने पर ही सक्रिय होता है। हमारी टीम इसे आपके लिए सेटअप करेगी।",
                            fontSize = 11.sp,
                            color = TextMuted,
                            lineHeight = 15.sp
                        )
                    }
                }
            }
        } else {
            // LEAD FORM INPUT CONTAINER
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .testTag("lead_form_card"),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = TechDarkSurface),
                border = BorderStroke(1.dp, TechDarkBorder)
            ) {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(18.dp)
                ) {
                    // Error Banner if present
                    if (errorMessage != null) {
                        Row(
                            verticalAlignment = Alignment.CenterVertically,
                            modifier = Modifier
                                .fillMaxWidth()
                                .clip(RoundedCornerShape(8.dp))
                                .background(Color(0x33EF4444))
                                .border(1.dp, Color(0xFFEF4444), RoundedCornerShape(8.dp))
                                .padding(10.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Warning,
                                contentDescription = null,
                                tint = Color(0xFFEF4444),
                                modifier = Modifier.size(16.dp)
                            )
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = errorMessage,
                                fontSize = 12.sp,
                                color = Color(0xFFFCA5A5),
                                fontWeight = FontWeight.Medium
                            )
                        }
                        Spacer(modifier = Modifier.height(14.dp))
                    }

                    // 1. Name Input
                    Text(
                        text = "आपका नाम (Name) *",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = TextWhite
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    OutlinedTextField(
                        value = leadForm.name,
                        onValueChange = onNameChange,
                        placeholder = { Text("उदा. अमित शर्मा", color = TextMuted) },
                        leadingIcon = {
                            Icon(Icons.Default.Person, contentDescription = null, tint = TechCyan)
                        },
                        singleLine = true,
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("input_lead_name"),
                        shape = RoundedCornerShape(10.dp),
                        colors = outlinedFieldColors()
                    )

                    Spacer(modifier = Modifier.height(14.dp))

                    // 2. Mobile Number Input
                    Text(
                        text = "मोबाइल नंबर (Mobile Number) *",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = TextWhite
                    )
                    Spacer(modifier = Modifier.height(6.dp))
                    OutlinedTextField(
                        value = leadForm.mobileNumber,
                        onValueChange = onMobileChange,
                        placeholder = { Text("10 अंकों का मोबाइल नंबर", color = TextMuted) },
                        leadingIcon = {
                            Icon(Icons.Default.Phone, contentDescription = null, tint = WhatsAppGreen)
                        },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Phone),
                        singleLine = true,
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("input_lead_mobile"),
                        shape = RoundedCornerShape(10.dp),
                        colors = outlinedFieldColors()
                    )

                    Spacer(modifier = Modifier.height(14.dp))

                    // 3. Business Type Selector
                    Text(
                        text = "बिज़नेस का प्रकार (Business Type)",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = TextWhite
                    )
                    Spacer(modifier = Modifier.height(6.dp))

                    ExposedDropdownMenuBox(
                        expanded = businessTypeExpanded,
                        onExpandedChange = { businessTypeExpanded = !businessTypeExpanded }
                    ) {
                        OutlinedTextField(
                            value = leadForm.businessType,
                            onValueChange = {},
                            readOnly = true,
                            leadingIcon = {
                                Icon(Icons.Default.Business, contentDescription = null, tint = TechCyan)
                            },
                            trailingIcon = {
                                ExposedDropdownMenuDefaults.TrailingIcon(expanded = businessTypeExpanded)
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .menuAnchor(MenuAnchorType.PrimaryNotEditable)
                                .testTag("input_lead_business_type"),
                            shape = RoundedCornerShape(10.dp),
                            colors = outlinedFieldColors()
                        )

                        ExposedDropdownMenu(
                            expanded = businessTypeExpanded,
                            onDismissRequest = { businessTypeExpanded = false },
                            modifier = Modifier.background(TechDarkSurfaceVariant)
                        ) {
                            AppDataSource.businessTypes.forEach { type ->
                                DropdownMenuItem(
                                    text = { Text(type, color = TextWhite) },
                                    onClick = {
                                        onBusinessTypeChange(type)
                                        businessTypeExpanded = false
                                    }
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    // 4. Requested Service
                    Text(
                        text = "आवश्यक सर्विस (Requested Service)",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = TextWhite
                    )
                    Spacer(modifier = Modifier.height(6.dp))

                    FlowRow(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(6.dp),
                        verticalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        AppDataSource.serviceOptions.forEach { srv ->
                            val isSelected = leadForm.requestedService == srv
                            FilterChip(
                                selected = isSelected,
                                onClick = { onServiceChange(srv) },
                                label = { Text(srv, fontSize = 11.sp) },
                                shape = RoundedCornerShape(16.dp),
                                colors = FilterChipDefaults.filterChipColors(
                                    selectedContainerColor = WhatsAppGreen,
                                    selectedLabelColor = Color.Black,
                                    containerColor = TechDarkSurfaceVariant,
                                    labelColor = TextWhite
                                )
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    // 5. Approximate Budget
                    Text(
                        text = "अनुमानित बजट (Approximate Budget)",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = TextWhite
                    )
                    Spacer(modifier = Modifier.height(6.dp))

                    FlowRow(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(6.dp),
                        verticalArrangement = Arrangement.spacedBy(6.dp)
                    ) {
                        AppDataSource.budgetOptions.forEach { bdg ->
                            val isSelected = leadForm.budget == bdg
                            FilterChip(
                                selected = isSelected,
                                onClick = { onBudgetChange(bdg) },
                                label = { Text(bdg, fontSize = 11.sp) },
                                shape = RoundedCornerShape(16.dp),
                                colors = FilterChipDefaults.filterChipColors(
                                    selectedContainerColor = TechCyan,
                                    selectedLabelColor = Color.Black,
                                    containerColor = TechDarkSurfaceVariant,
                                    labelColor = TextWhite
                                )
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(14.dp))

                    // 6. Requirement
                    Text(
                        text = "प्रोजेक्ट की आवश्यकता (Requirement / Details)",
                        fontSize = 12.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = TextWhite
                    )
                    Spacer(modifier = Modifier.height(6.dp))

                    OutlinedTextField(
                        value = leadForm.requirement,
                        onValueChange = onRequirementChange,
                        placeholder = {
                            Text(
                                "उदा. हमें अपनी दुकान के लिए WhatsApp ऑटो-रिप्लाई और 1 पेज वेबसाइट चाहिए...",
                                color = TextMuted,
                                fontSize = 12.sp
                            )
                        },
                        minLines = 3,
                        modifier = Modifier
                            .fillMaxWidth()
                            .testTag("input_lead_requirement"),
                        shape = RoundedCornerShape(10.dp),
                        colors = outlinedFieldColors()
                    )

                    Spacer(modifier = Modifier.height(20.dp))

                    // Submit Form Button
                    Button(
                        onClick = { onSubmit() },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(50.dp)
                            .testTag("submit_lead_form_button"),
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = WhatsAppGreen,
                            contentColor = Color.Black
                        )
                    ) {
                        Text(
                            text = "सबमिट करें और WhatsApp पर भेजें",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    Text(
                        text = "सीधे संपर्क हेतु हेल्पलाइन: ${BusinessConstants.WHATSAPP_DISPLAY}",
                        fontSize = 11.sp,
                        color = TextMuted,
                        textAlign = androidx.compose.ui.text.style.TextAlign.Center,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }
        }
    }
}

@Composable
private fun outlinedFieldColors() = OutlinedTextFieldDefaults.colors(
    focusedTextColor = TextWhite,
    unfocusedTextColor = TextWhite,
    focusedBorderColor = WhatsAppGreen,
    unfocusedBorderColor = TechDarkBorder,
    focusedContainerColor = TechDarkSurfaceVariant,
    unfocusedContainerColor = TechDarkSurfaceVariant,
    cursorColor = WhatsAppGreen
)
