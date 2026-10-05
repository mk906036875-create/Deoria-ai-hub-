package com.example.data

import com.example.model.PackageItem
import com.example.model.ServiceItem
import com.example.model.StepItem

object AppDataSource {

    val services = listOf(
        ServiceItem(
            id = "ai_automation",
            titleEn = "AI Automation",
            titleHi = "एआई ऑटोमेशन",
            descriptionEn = "Automate repetitive daily tasks, customer follow-ups, and lead data pipelines with custom AI agents.",
            descriptionHi = "अपने बिज़नेस के रोज़ाना के रूटीन काम, डेटा एंट्री और फॉलो-अप को स्मार्ट AI से ऑटोमेट करें।",
            iconName = "SmartToy",
            features = listOf(
                "24/7 Automated business operations",
                "CRM & Google Sheets instant sync",
                "Zero manual data re-entry errors"
            )
        ),
        ServiceItem(
            id = "whatsapp_chatbot",
            titleEn = "WhatsApp Chatbot",
            titleHi = "व्हाट्सएप चैटबॉट",
            descriptionEn = "Instant AI-powered replies, product catalog sharing, and interactive enquiry handling on WhatsApp.",
            descriptionHi = "ग्राहक के मैसेज भेजते ही तुरंत 2 सेकंड में स्मार्ट रिप्लाई, कैटलॉग और इन्क्वायरी हैंडलिंग।",
            iconName = "Chat",
            features = listOf(
                "Instant auto-reply to new messages",
                "Interactive button & menu flow",
                "Direct lead qualification on WhatsApp"
            )
        ),
        ServiceItem(
            id = "business_website",
            titleEn = "Business Website",
            titleHi = "बिज़नेस वेबसाइट",
            descriptionEn = "Lightning-fast, mobile-first websites engineered specifically to convert visitors into phone calls and WhatsApp leads.",
            descriptionHi = "सुपर-फास्ट, मोबाइल-फ्रेंडली वेबसाइट जो विज़िटर्स को असली कॉल्स और WhatsApp लीड्स में बदलती है।",
            iconName = "Language",
            features = listOf(
                "100% Mobile & tablet optimized",
                "Direct WhatsApp & Click-to-Call CTAs",
                "SEO & Google Maps readiness"
            )
        ),
        ServiceItem(
            id = "lead_generation",
            titleEn = "Lead Generation",
            titleHi = "लीड जेनरेशन फनल",
            descriptionEn = "Smart lead capture funnels that qualify prospect budget, requirements, and contact info before sending to you.",
            descriptionHi = "स्मार्ट लीड फनल जो ग्राहक का बजट, नाम और जरूरत पहले ही जांचकर आपको क्वालिटी लीड देता है।",
            iconName = "TrendingUp",
            features = listOf(
                "High-conversion lead capture forms",
                "Automated instant SMS/WhatsApp alerts",
                "Categorized lead filtering"
            )
        ),
        ServiceItem(
            id = "appointment_automation",
            titleEn = "Appointment Automation",
            titleHi = "अपॉइंटमेंट ऑटोमेशन",
            descriptionEn = "Automate consultation bookings, clinic visits, meetings, and send automated reminder notifications.",
            descriptionHi = "क्लीनिक, कोचिंग या कंसल्टेशन के लिए ऑटोमेटिक बुकिंग और समय पर रिमाइंडर मैसेज भेजना।",
            iconName = "EventAvailable",
            features = listOf(
                "Self-service time slot selection",
                "Automated reminder messages",
                "Reduced no-shows & missed meetings"
            )
        ),
        ServiceItem(
            id = "support_automation",
            titleEn = "Customer Support Automation",
            titleHi = "कस्टमर सपोर्ट ऑटोमेशन",
            descriptionEn = "Answer common FAQs, pricing questions, timing queries, and store locations 24/7 without hiring extra staff.",
            descriptionHi = "दुकान/ऑफिस टाइमिंग, लोकेशन और सामान्य सवालों के जवाब 24 घंटे बिना किसी स्टाफ के तुरंत दें।",
            iconName = "HeadsetMic",
            features = listOf(
                "24/7 FAQ instant answers",
                "Human escalation when needed",
                "Multi-language support in Hindi & English"
            )
        )
    )

    val steps = listOf(
        StepItem(
            stepNumber = 1,
            titleEn = "Customer sends enquiry",
            titleHi = "ग्राहक संपर्क करता है",
            descriptionEn = "A prospective buyer clicks your WhatsApp button or website enquiry form.",
            descriptionHi = "नया ग्राहक आपकी वेबसाइट या WhatsApp बटन पर क्लिक करके मैसेज भेजता है।",
            iconName = "Send"
        ),
        StepItem(
            stepNumber = 2,
            titleEn = "AI chatbot replies instantly",
            titleHi = "AI चैटबॉट तुरंत उत्तर देता है",
            descriptionEn = "Within seconds, the intelligent bot greets them warmly and provides options.",
            descriptionHi = "बिना किसी इंतज़ार के 2 सेकंड में बॉट ग्राहक का स्वागत करता है और विकल्प देता है।",
            iconName = "FlashOn"
        ),
        StepItem(
            stepNumber = 3,
            titleEn = "Customer details are collected",
            titleHi = "ग्राहक की जानकारी कलेक्ट होती है",
            descriptionEn = "Name, phone number, specific service needed, and budget are smoothly gathered.",
            descriptionHi = "ग्राहक का नाम, मोबाइल नंबर, जरूरत और बजट बातचीत में ही सुरक्षित कलेक्ट हो जाता है।",
            iconName = "FactCheck"
        ),
        StepItem(
            stepNumber = 4,
            titleEn = "Lead is sent to business owner",
            titleHi = "लीड सीधे आपके पास पहुंचती है",
            descriptionEn = "A neatly formatted instant alert lands directly on your WhatsApp or notification panel.",
            descriptionHi = "पूरी लीड विवरण के साथ तुरंत आपके निजी WhatsApp और फोन पर आ जाती है।",
            iconName = "NotificationsActive"
        ),
        StepItem(
            stepNumber = 5,
            titleEn = "Follow-up automation",
            titleHi = "ऑटोमेटिक फॉलो-अप सिस्टम",
            descriptionEn = "Automated courteous reminders ensure warm leads do not go cold before closing.",
            descriptionHi = "ऑटोमेटिक फॉलो-अप से ग्राहक का भरोसा बढ़ता है और डील फाइनल होने की संभावना बढ़ जाती है।",
            iconName = "Autorenew"
        )
    )

    val packages = listOf(
        PackageItem(
            id = "starter",
            name = "Starter",
            price = "₹4,999",
            tagline = "Best for local shops & solo businesses starting online",
            isPopular = false,
            features = listOf(
                "Responsive 1-Page Business Website",
                "Basic WhatsApp Click-to-Chat Flow",
                "Direct Lead Capture to WhatsApp",
                "Google Maps & Contact integration",
                "Fast mobile loading speed",
                "1 Year Hosting Guidance"
            ),
            bestFor = "दुकानदार, रीटेल स्टोर, फ्रीलांसर"
        ),
        PackageItem(
            id = "business",
            name = "Business",
            price = "₹9,999",
            tagline = "Most popular for growing brands & local services",
            isPopular = true,
            features = listOf(
                "Multi-Section Premium Business Website",
                "Interactive WhatsApp Chatbot Flow",
                "Automated FAQ & Menu Responses",
                "Direct Lead Routing to Owner Phone",
                "SEO Meta Setup & Google Business sync",
                "Priority Setup & Technical Support"
            ),
            bestFor = "कोचिंग, क्लीनिक, रेस्तरां, कंसल्टेंट्स"
        ),
        PackageItem(
            id = "ai_automation",
            name = "AI Automation",
            price = "₹19,999",
            tagline = "Complete automation suite for scaling enterprises",
            isPopular = false,
            features = listOf(
                "Full Custom High-Performance Website",
                "Advanced AI Conversational WhatsApp Bot",
                "Appointment & Booking Automation",
                "Customer Support Auto-Ticketing",
                "Google Sheets / CRM Lead Pipeline",
                "Dedicated Onboarding & 30-Day Support"
            ),
            bestFor = "रियल एस्टेट, हॉस्पिटल, बड़े ब्रांड्स व एजेंसी"
        )
    )

    val businessTypes = listOf(
        "Retail / Shop / शोरूम",
        "Doctor / Clinic / अस्पताल",
        "Coaching / School / इंस्टिट्यूट",
        "Real Estate / प्रॉपर्टी डीलर",
        "Restaurant / Hotel / कैफे",
        "Service / Agency / कंसल्टेंट",
        "Manufacturing / होलसेलर",
        "Other Business / अन्य"
    )

    val serviceOptions = listOf(
        "WhatsApp Chatbot",
        "Business Website",
        "AI Automation",
        "Lead Generation Funnel",
        "Appointment Automation",
        "Full Business Suite"
    )

    val budgetOptions = listOf(
        "₹4,999 (Starter)",
        "₹9,999 (Business)",
        "₹19,999 (AI Automation)",
        "₹25,000+ (Custom Enterprise)"
    )

    fun getInitialDemoLeads(): List<com.example.model.ManagedLead> {
        return listOf(
            com.example.model.ManagedLead(
                id = "lead_1",
                name = "डॉ. अभिषेक वर्मा",
                mobile = "9838012345",
                business = "Doctor / Clinic (लाइफकेयर क्लीनिक, देवरिया)",
                serviceRequired = "Appointment Automation",
                budget = "₹19,999 (AI Automation)",
                dateTime = "आज, 10:15 AM",
                requirement = "मरीजों के ऑनलाइन अपॉइंटमेंट व WhatsApp रिमाइंडर का ऑटोमेशन चाहिए।",
                status = com.example.model.LeadStatus.NEW,
                temperature = com.example.model.LeadTemperature.HOT,
                suggestedReply = "नमस्ते डॉ. अभिषेक वर्मा जी! 🙏 Deoria AI Hub की ओर से सादर प्रणाम। हमने आपके लाइफकेयर क्लीनिक हेतु अपॉइंटमेंट ऑटोमेशन की आवश्यकता नोट की है। क्या आज 10 मिनट के कॉल पर हम आपको लाइव डेमो दिखा सकते हैं?",
                isDemo = true
            ),
            com.example.model.ManagedLead(
                id = "lead_2",
                name = "राजेश त्रिपाठी",
                mobile = "9450198765",
                business = "Coaching / Institute (सक्सेस पॉइंट, सिविल लाइंस)",
                serviceRequired = "WhatsApp Chatbot",
                budget = "₹9,999 (Business)",
                dateTime = "आज, 09:30 AM",
                requirement = "छात्रों के एडमिशन इंक्वायरी और फीस स्ट्रक्चर के लिए 24/7 ऑटो-रिप्लाई बॉट।",
                status = com.example.model.LeadStatus.CONTACTED,
                temperature = com.example.model.LeadTemperature.WARM,
                suggestedReply = "नमस्ते राजेश त्रिपाठी जी! Deoria AI Hub से संपर्क करने के लिए धन्यवाद। सक्सेस पॉइंट कोचिंग हेतु WhatsApp Chatbot का डेमो व प्रपोजल तैयार है। क्या आज दोपहर बातचीत संभव है?",
                isDemo = true
            ),
            com.example.model.ManagedLead(
                id = "lead_3",
                name = "विक्रम सिंह",
                mobile = "9792456789",
                business = "Real Estate (पूर्वांचल प्रॉपर्टीज)",
                serviceRequired = "Lead Generation Funnel",
                budget = "₹25,000+ (Custom Enterprise)",
                dateTime = "कल, 04:20 PM",
                requirement = "प्लॉट व मकान खरीदारों की लीड्स सीधे WhatsApp व गूगल शीट्स पर फिल्टर करके चाहिए।",
                status = com.example.model.LeadStatus.NEW,
                temperature = com.example.model.LeadTemperature.HOT,
                suggestedReply = "नमस्ते विक्रम सिंह जी! Deoria AI Hub की ओर से नमस्ते। पूर्वांचल प्रॉपर्टीज के लिए रियल एस्टेट लीड जेनरेशन और WhatsApp ऑटोमेशन का कम्पलीट प्लान तैयार है। क्या हम आज शाम कॉल पर बात कर सकते हैं?",
                isDemo = true
            ),
            com.example.model.ManagedLead(
                id = "lead_4",
                name = "सुमित जायसवाल",
                mobile = "9918234567",
                business = "Retail / Shop (जायसवाल गारमेंट्स, सुभाष चौक)",
                serviceRequired = "Business Website",
                budget = "₹4,999 (Starter)",
                dateTime = "कल, 06:45 PM",
                requirement = "दुकान का 1-पेज ऑनलाइन कैटलॉग व WhatsApp पर ऑर्डर लेने की व्यवस्था।",
                status = com.example.model.LeadStatus.INTERESTED,
                temperature = com.example.model.LeadTemperature.COLD,
                suggestedReply = "नमस्ते सुमित जी! Deoria AI Hub में स्वागत है। जायसवाल गारमेंट्स के लिए हमारा ₹4,999 वाला स्टार्टर वेबसाइट प्लान परफेक्ट रहेगा। इसमें तुरंत WhatsApp ऑर्डर बटन भी मिलता है।",
                isDemo = true
            ),
            com.example.model.ManagedLead(
                id = "lead_5",
                name = "मनोज कुमार",
                mobile = "9889765432",
                business = "Restaurant / Hotel (रॉयल डाइनिंग, स्टेशन रोड)",
                serviceRequired = "Full Business Suite",
                budget = "₹19,999 (AI Automation)",
                dateTime = "3 दिन पहले",
                requirement = "डिजिटल मेनू, टेबल बुकिंग व कस्टमर फीडबैक का WhatsApp ऑटोमेशन।",
                status = com.example.model.LeadStatus.CONVERTED,
                temperature = com.example.model.LeadTemperature.HOT,
                suggestedReply = "नमस्ते मनोज जी! रॉयल डाइनिंग के लिए AI ऑटोमेशन सेटअप सफल रहा। किसी भी सपोर्ट या नए फीचर्स के लिए हम सदैव उपलब्ध हैं।",
                isDemo = true
            )
        )
    }
}
