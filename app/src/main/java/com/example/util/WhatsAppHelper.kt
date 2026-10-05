package com.example.util

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.widget.Toast
import com.example.model.BusinessConstants
import java.net.URLEncoder

object WhatsAppHelper {

    fun openWhatsApp(context: Context, message: String, phoneNumber: String = BusinessConstants.WHATSAPP_NUMBER_CLEAN) {
        try {
            val encodedMessage = URLEncoder.encode(message, "UTF-8")
            // Try standard whatsapp direct URL which Android routes to WhatsApp or Browser
            val url = "https://api.whatsapp.com/send?phone=$phoneNumber&text=$encodedMessage"
            val intent = Intent(Intent.ACTION_VIEW).apply {
                data = Uri.parse(url)
                flags = Intent.FLAG_ACTIVITY_NEW_TASK
            }
            context.startActivity(intent)
        } catch (e: Exception) {
            // Fallback: Copy to clipboard and notify
            copyToClipboard(context, message, "संदेश कॉपी हो गया है")
            Toast.makeText(
                context,
                "WhatsApp ओपन करने में असमर्थ। संदेश क्लिपबोर्ड में कॉपी कर दिया गया है।",
                Toast.LENGTH_LONG
            ).show()
        }
    }

    fun copyToClipboard(context: Context, text: String, toastMessage: String = "कॉपी किया गया") {
        try {
            val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
            val clip = ClipData.newPlainText("Deoria AI Hub Enquiry", text)
            clipboard.setPrimaryClip(clip)
            Toast.makeText(context, toastMessage, Toast.LENGTH_SHORT).show()
        } catch (e: Exception) {
            // ignore
        }
    }

    fun dialPhoneNumber(context: Context, phoneNumber: String = BusinessConstants.WHATSAPP_DISPLAY) {
        try {
            val cleanPhone = phoneNumber.replace(" ", "")
            val intent = Intent(Intent.ACTION_DIAL).apply {
                data = Uri.parse("tel:$cleanPhone")
                flags = Intent.FLAG_ACTIVITY_NEW_TASK
            }
            context.startActivity(intent)
        } catch (e: Exception) {
            Toast.makeText(context, "कॉल डायल नहीं हो सका: $phoneNumber", Toast.LENGTH_SHORT).show()
        }
    }
}
