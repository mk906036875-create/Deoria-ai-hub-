package com.example.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.material.icons.filled.HelpOutline
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.TechCyan
import com.example.ui.theme.TechDarkBorder
import com.example.ui.theme.TechDarkSurface
import com.example.ui.theme.TechDarkSurfaceVariant
import com.example.ui.theme.TextMuted
import com.example.ui.theme.TextWhite

data class FaqItem(
    val question: String,
    val answer: String
)

@Composable
fun FaqSection(
    modifier: Modifier = Modifier
) {
    val faqList = remember {
        listOf(
            FaqItem(
                question = "Deoria AI Hub क्या है और यह मेरे बिजनेस की कैसे मदद करता है?",
                answer = "Deoria AI Hub देवरिया व पूर्वांचल के स्थानीय व्यापारियों (दुकानदार, कोचिंग, क्लीनिक, रेस्तरां, एजेंसी) के लिए प्रोफेशनल वेबसाइट्स, WhatsApp चैटबॉट और AI लीड ऑटोमेशन तैयार करता है जिससे ग्राहक की कोई भी पूछताछ छूटे नहीं।"
            ),
            FaqItem(
                question = "WhatsApp Chatbot कैसे काम करता है?",
                answer = "जब कोई ग्राहक आपके बिजनेस नंबर पर मैसेज भेजता है, तो चैटबॉट तुरंत अभिवादन करता है, सर्विसेज व प्राइस मेनू प्रस्तुत करता है, ग्राहक का नाम व नंबर नोट करता है और तुरंत आपके डैशबोर्ड पर लीड दर्ज कर देता है।"
            ),
            FaqItem(
                question = "वेबसाइट और चैटबॉट सेटअप में कितना समय लगता है?",
                answer = "स्टार्टर पैकेज मात्र 24-48 घंटों में रेडी हो जाता है। बिज़नेस और फुल AI ऑटोमेशन पैकेजेस 3 से 5 दिनों में पूर्ण टेस्टिंग के साथ डिलीवर किए जाते हैं।"
            ),
            FaqItem(
                question = "क्या मुझे कोडिंग या टेक्निकल ज्ञान की आवश्यकता है?",
                answer = "बिल्कुल नहीं! डिज़ाइन, होस्टिंग, डोमेन और ऑटोमेशन का पूरा सेटअप हमारी टीम संभालती है। आप सीधे अपने मोबाइल से लीड्स और ग्राहकों के संपर्क देख सकते हैं।"
            ),
            FaqItem(
                question = "पैकेज और प्राइसिंग क्या है?",
                answer = "पारदर्शी और एकमुश्त शुल्क:\n• Starter: ₹4,999 (1-पेज बिज़नेस वेबसाइट + WhatsApp लीड फ्लो)\n• Business: ₹9,999 (मल्टी-सेक्शन वेबसाइट + इंटरैक्टिव चैटबॉट)\n• AI Automation: ₹19,999 (फुल ऑटोमेशन सुइट + अपॉइंटमेंट व लीड CRM)"
            ),
            FaqItem(
                question = "क्या यह असली WhatsApp Business API से कनेक्ट हो सकता है?",
                answer = "हाँ, ऐप में Meta WhatsApp Business Platform का प्रोडक्शन आर्किटेक्चर शामिल है। जब आप अपनी आधिकारिक Meta API क्रेडेंशियल्स जोड़ते हैं, तो ऑटो-रिप्लाई और वेबहुक पूरी तरह एक्टिवेट हो जाते हैं।"
            )
        )
    }

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
                    .height(24.dp)
                    .clip(RoundedCornerShape(2.dp))
                    .background(TechCyan)
            )
            Spacer(modifier = Modifier.width(10.dp))
            Column {
                Text(
                    text = "अक्सर पूछे जाने वाले सवाल (FAQ)",
                    style = MaterialTheme.typography.titleLarge.copy(
                        fontWeight = FontWeight.Bold,
                        color = TextWhite,
                        fontSize = 20.sp
                    )
                )
                Text(
                    text = "सामान्य व्यावसायिक प्रश्न और उनके समाधान",
                    style = MaterialTheme.typography.bodySmall.copy(
                        color = TechCyan,
                        fontSize = 12.sp
                    )
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
            faqList.forEachIndexed { index, item ->
                FaqAccordionItem(item = item, index = index)
            }
        }
    }
}

@Composable
private fun FaqAccordionItem(
    item: FaqItem,
    index: Int
) {
    var expanded by remember { mutableStateOf(index == 0) }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .clickable { expanded = !expanded }
            .testTag("faq_item_$index"),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = TechDarkSurface),
        border = BorderStroke(1.dp, if (expanded) TechCyan.copy(alpha = 0.5f) else TechDarkBorder)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(14.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    modifier = Modifier.weight(1f),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.HelpOutline,
                        contentDescription = null,
                        tint = TechCyan,
                        modifier = Modifier.size(18.dp)
                    )
                    Spacer(modifier = Modifier.width(10.dp))
                    Text(
                        text = item.question,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.SemiBold,
                        color = TextWhite
                    )
                }

                Icon(
                    imageVector = if (expanded) Icons.Default.KeyboardArrowUp else Icons.Default.KeyboardArrowDown,
                    contentDescription = null,
                    tint = TextMuted,
                    modifier = Modifier.size(20.dp)
                )
            }

            AnimatedVisibility(visible = expanded) {
                Column(modifier = Modifier.padding(top = 10.dp, start = 28.dp)) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(1.dp)
                            .background(TechDarkBorder)
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    Text(
                        text = item.answer,
                        fontSize = 13.sp,
                        lineHeight = 19.sp,
                        color = TextMuted
                    )
                }
            }
        }
    }
}
