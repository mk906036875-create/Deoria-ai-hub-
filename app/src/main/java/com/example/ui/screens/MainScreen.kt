package com.example.ui.screens

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Chat
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.BusinessConstants
import com.example.ui.components.AppTopBar
import com.example.ui.components.ChatbotDemoSection
import com.example.ui.components.ContactSection
import com.example.ui.components.FaqSection
import com.example.ui.components.HeroSection
import com.example.ui.components.HowItWorksSection
import com.example.ui.components.LeadDashboardSection
import com.example.ui.components.LeadFormSection
import com.example.ui.components.PackagesSection
import com.example.ui.components.ServicesSection
import com.example.ui.components.WhatsAppBottomBar
import com.example.ui.components.WhatsAppIntegrationSection
import com.example.ui.theme.TechDarkBackground
import com.example.ui.theme.TechDarkBorder
import com.example.ui.theme.TechDarkSurface
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextWhite
import com.example.ui.theme.WhatsAppGreen
import com.example.util.WhatsAppHelper
import com.example.viewmodel.AppSection
import com.example.viewmodel.MainViewModel
import com.example.viewmodel.UiState
import kotlinx.coroutines.launch

@Composable
fun MainScreen(
    viewModel: MainViewModel,
    uiState: UiState,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val scrollState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()

    // Handle back button: if not on HOME, return to HOME
    BackHandler(enabled = uiState.currentSection != AppSection.HOME) {
        viewModel.setSection(AppSection.HOME)
    }

    val triggerWhatsApp = { text: String ->
        WhatsAppHelper.openWhatsApp(
            context = context,
            message = text,
            phoneNumber = BusinessConstants.WHATSAPP_NUMBER_CLEAN
        )
    }

    Scaffold(
        modifier = modifier.fillMaxSize(),
        containerColor = TechDarkBackground,
        topBar = {
            AppTopBar(
                leadCount = uiState.leads.size,
                onLeadsClick = {
                    viewModel.setSection(AppSection.LEAD_DASHBOARD)
                },
                onWhatsAppClick = {
                    val defaultMsg = "नमस्ते Deoria AI Hub, मेरा नाम [Name] है। मुझे [Service] की जरूरत है। मेरा Business [Business Type] है। कृपया मुझे जानकारी दें।"
                    triggerWhatsApp(defaultMsg)
                }
            )
        },
        bottomBar = {
            WhatsAppBottomBar(
                currentSection = uiState.currentSection,
                onSectionSelected = { section ->
                    viewModel.setSection(section)
                    coroutineScope.launch {
                        scrollState.scrollToItem(0)
                    }
                },
                onQuickWhatsAppClick = {
                    val defaultMsg = "नमस्ते Deoria AI Hub, मेरा नाम [Name] है। मुझे [Service] की जरूरत है। मेरा Business [Business Type] है। कृपया मुझे जानकारी दें।"
                    triggerWhatsApp(defaultMsg)
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    val defaultMsg = "नमस्ते Deoria AI Hub, मेरा नाम [Name] है। मुझे [Service] की जरूरत है। मेरा Business [Business Type] है। कृपया मुझे जानकारी दें।"
                    triggerWhatsApp(defaultMsg)
                },
                containerColor = WhatsAppGreen,
                contentColor = Color.Black,
                elevation = FloatingActionButtonDefaults.elevation(8.dp),
                modifier = Modifier
                    .padding(bottom = 70.dp)
                    .testTag("floating_whatsapp_button")
            ) {
                Icon(
                    imageVector = Icons.Default.Chat,
                    contentDescription = "WhatsApp चैट",
                    modifier = Modifier.size(26.dp)
                )
            }
        }
    ) { innerPadding ->
        LazyColumn(
            state = scrollState,
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(TechDarkBackground)
        ) {
            when (uiState.currentSection) {
                AppSection.HOME -> {
                    // Full Mobile-First Business Presentation
                    item {
                        HeroSection(
                            onWhatsAppClick = {
                                val defaultMsg = "नमस्ते Deoria AI Hub, मेरा नाम [Name] है। मुझे [Service] की जरूरत है। मेरा Business [Business Type] है। कृपया मुझे जानकारी दें।"
                                triggerWhatsApp(defaultMsg)
                            },
                            onDemoClick = {
                                viewModel.setSection(AppSection.CHATBOT_DEMO)
                            }
                        )
                    }

                    item {
                        ServicesSection(
                            onSelectServiceForEnquiry = { service ->
                                viewModel.selectServiceForEnquiry(service)
                            }
                        )
                    }

                    item {
                        HowItWorksSection()
                    }

                    item {
                        PackagesSection(
                            onSelectPackage = { pkgId ->
                                viewModel.selectPackageForEnquiry(pkgId)
                            }
                        )
                    }

                    item {
                        ChatbotDemoSection(
                            messages = uiState.chatMessages,
                            isBotTyping = uiState.isBotTyping,
                            quickChips = uiState.quickChips,
                            onChipClick = { chip -> viewModel.onQuickChipSelected(chip) },
                            onSendMessage = { text -> viewModel.sendUserCustomChat(text) },
                            onResetChat = { viewModel.clearChat() },
                            onOpenWhatsApp = { text -> triggerWhatsApp(text) }
                        )
                    }

                    item {
                        LeadFormSection(
                            leadForm = uiState.leadForm,
                            isSubmitted = uiState.isFormSubmitted,
                            errorMessage = uiState.formErrorMessage,
                            submittedMessageText = uiState.submittedMessageText,
                            onNameChange = { viewModel.updateName(it) },
                            onMobileChange = { viewModel.updateMobile(it) },
                            onBusinessTypeChange = { viewModel.updateBusinessType(it) },
                            onServiceChange = { viewModel.updateRequestedService(it) },
                            onBudgetChange = { viewModel.updateBudget(it) },
                            onRequirementChange = { viewModel.updateRequirement(it) },
                            onSubmit = { viewModel.submitLeadForm() },
                            onReset = { viewModel.resetLeadForm() },
                            onViewInDashboard = { viewModel.setSection(AppSection.LEAD_DASHBOARD) }
                        )
                    }

                    item {
                        FaqSection()
                    }

                    item {
                        ContactSection(
                            onWhatsAppClick = {
                                val defaultMsg = "नमस्ते Deoria AI Hub, मेरा नाम [Name] है। मुझे [Service] की जरूरत है। मेरा Business [Business Type] है। कृपया मुझे जानकारी दें।"
                                triggerWhatsApp(defaultMsg)
                            }
                        )
                    }

                    // Lead Management Dashboard Preview on Home
                    item {
                        LeadDashboardSection(
                            leads = uiState.filteredLeads,
                            stats = uiState.stats,
                            dbInfo = uiState.dbInfo,
                            selectedStatus = uiState.selectedStatusFilter,
                            selectedTemp = uiState.selectedTempFilter,
                            searchQuery = uiState.leadSearchQuery,
                            onStatusFilterChange = { viewModel.setLeadFilterStatus(it) },
                            onTempFilterChange = { viewModel.setLeadFilterTemperature(it) },
                            onSearchQueryChange = { viewModel.setLeadSearchQuery(it) },
                            onUpdateStatus = { id, st -> viewModel.updateLeadStatus(id, st) },
                            onDeleteLead = { id -> viewModel.deleteLead(id) },
                            onNavigateToEnquiryForm = { viewModel.setSection(AppSection.LEAD_FORM) }
                        )
                    }

                    // WhatsApp Business Platform Integration Architecture Preview
                    item {
                        WhatsAppIntegrationSection()
                    }

                    item {
                        FooterSection(
                            onWhatsAppClick = {
                                val defaultMsg = "नमस्ते Deoria AI Hub, मेरा नाम [Name] है। मुझे [Service] की जरूरत है। मेरा Business [Business Type] है। कृपया मुझे जानकारी दें।"
                                triggerWhatsApp(defaultMsg)
                            }
                        )
                    }
                }

                AppSection.SERVICES -> {
                    item {
                        ServicesSection(
                            onSelectServiceForEnquiry = { service ->
                                viewModel.selectServiceForEnquiry(service)
                            }
                        )
                    }
                }

                AppSection.HOW_IT_WORKS -> {
                    item {
                        HowItWorksSection()
                    }
                }

                AppSection.PACKAGES -> {
                    item {
                        PackagesSection(
                            onSelectPackage = { pkgId ->
                                viewModel.selectPackageForEnquiry(pkgId)
                            }
                        )
                    }
                }

                AppSection.CHATBOT_DEMO -> {
                    item {
                        ChatbotDemoSection(
                            messages = uiState.chatMessages,
                            isBotTyping = uiState.isBotTyping,
                            quickChips = uiState.quickChips,
                            onChipClick = { chip -> viewModel.onQuickChipSelected(chip) },
                            onSendMessage = { text -> viewModel.sendUserCustomChat(text) },
                            onResetChat = { viewModel.clearChat() },
                            onOpenWhatsApp = { text -> triggerWhatsApp(text) }
                        )
                    }
                }

                AppSection.LEAD_FORM -> {
                    item {
                        LeadFormSection(
                            leadForm = uiState.leadForm,
                            isSubmitted = uiState.isFormSubmitted,
                            errorMessage = uiState.formErrorMessage,
                            submittedMessageText = uiState.submittedMessageText,
                            onNameChange = { viewModel.updateName(it) },
                            onMobileChange = { viewModel.updateMobile(it) },
                            onBusinessTypeChange = { viewModel.updateBusinessType(it) },
                            onServiceChange = { viewModel.updateRequestedService(it) },
                            onBudgetChange = { viewModel.updateBudget(it) },
                            onRequirementChange = { viewModel.updateRequirement(it) },
                            onSubmit = { viewModel.submitLeadForm() },
                            onReset = { viewModel.resetLeadForm() },
                            onViewInDashboard = { viewModel.setSection(AppSection.LEAD_DASHBOARD) }
                        )
                    }
                }

                AppSection.LEAD_DASHBOARD -> {
                    item {
                        LeadDashboardSection(
                            leads = uiState.filteredLeads,
                            stats = uiState.stats,
                            dbInfo = uiState.dbInfo,
                            selectedStatus = uiState.selectedStatusFilter,
                            selectedTemp = uiState.selectedTempFilter,
                            searchQuery = uiState.leadSearchQuery,
                            onStatusFilterChange = { viewModel.setLeadFilterStatus(it) },
                            onTempFilterChange = { viewModel.setLeadFilterTemperature(it) },
                            onSearchQueryChange = { viewModel.setLeadSearchQuery(it) },
                            onUpdateStatus = { id, st -> viewModel.updateLeadStatus(id, st) },
                            onDeleteLead = { id -> viewModel.deleteLead(id) },
                            onNavigateToEnquiryForm = { viewModel.setSection(AppSection.LEAD_FORM) }
                        )
                    }
                }

                AppSection.WHATSAPP_INTEGRATION -> {
                    item {
                        WhatsAppIntegrationSection()
                    }
                }
            }
        }
    }
}

@Composable
private fun FooterSection(
    onWhatsAppClick: () -> Unit
) {
    Surface(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        shape = RoundedCornerShape(16.dp),
        color = TechDarkSurface,
        border = androidx.compose.foundation.BorderStroke(1.dp, TechDarkBorder)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Deoria AI Hub",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = TextWhite
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = "AI Automation • WhatsApp Chatbot • Business Websites",
                fontSize = 12.sp,
                color = WhatsAppGreen,
                fontWeight = FontWeight.Medium
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = "देवरिया एवं पूर्वांचल के व्यवसायों के लिए आधुनिक लीड जेनरेशन व ऑटोमेशन प्लेटफॉर्म।",
                fontSize = 12.sp,
                color = TextMuted,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(14.dp))

            Text(
                text = "WhatsApp / Phone: ${BusinessConstants.WHATSAPP_DISPLAY}",
                fontSize = 13.sp,
                fontWeight = FontWeight.SemiBold,
                color = TextWhite
            )

            Spacer(modifier = Modifier.height(14.dp))

            Text(
                text = "© 2026 Deoria AI Hub. All rights reserved.",
                fontSize = 11.sp,
                color = TextMuted
            )
        }
    }
}
