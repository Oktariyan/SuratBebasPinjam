package com.example.suratbebaspinjam.shared

import android.annotation.SuppressLint
import android.util.Patterns
import android.webkit.MimeTypeMap
import java.net.URLConnection.*
import java.text.SimpleDateFormat
import java.util.Locale

fun String.parseDate(): String? {
    return try {
        val inputFormatter = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
        val date = inputFormatter.parse(this) ?: return null
        val outputFormatter = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
        outputFormatter.format(date)
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}

@SuppressLint("DefaultLocale")
fun Long.toFormattedTime():String{
    val seconds = ((this / 1000) % 60).toInt()
    val minutes = ((this / (1000 * 60)) % 60).toInt()
    val hours = ((this / (1000 * 60 * 60)) % 24).toInt()

    return if (hours >0){
        String.format("%02d:%02d:%02d",hours,minutes,seconds)
    }else{
        String.format("%02d:%02d",minutes,seconds)
    }
}

fun CharSequence.parseEmail(): Boolean {
    return Patterns.EMAIL_ADDRESS.matcher(this).matches()
}

fun String.parsePassword(): Boolean {
    return this.length >= 8 && this.any { it.isDigit() } && this.any { it.isLetter() } && this.any { !it.isLetterOrDigit() } && this.none { it.isWhitespace() } && this.none { !it.isLetterOrDigit() } && this.none { !it.isLetter() } && this.none { !it.isDigit() }
}

fun String.getMimeTypeFromUrl(): String? {
    return try {
        val connection = guessContentTypeFromName(this)
        connection ?: run {
            val extension = MimeTypeMap.getFileExtensionFromUrl(this)
            MimeTypeMap.getSingleton().getMimeTypeFromExtension(extension)
        }
    } catch (e: Exception) {
        null
    }
}
