package com.example.suratbebaspinjam

import com.example.suratbebaspinjam.auth.AuthActivity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity


class MainActivity : AppCompatActivity() {

    private lateinit var webView: WebView
    private lateinit var btnLogout: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Cek apakah user sudah login
        val sharedPreferences = getSharedPreferences("user_session", Context.MODE_PRIVATE)
        val isLoggedIn = sharedPreferences.getBoolean("isLoggedIn", false)

        if (!isLoggedIn) {
            // Jika belum login, arahkan ke SignInActivity
            startActivity(Intent(this, AuthActivity::class.java))
            finish() // Tutup MainActivity agar tidak bisa di-back
            return
        }

        setContentView(R.layout.activity_main)

        // Inisialisasi WebView
        webView = findViewById(R.id.webView)
        setupWebView()

        // Load URL
        webView.loadUrl("https://forms.gle/m1TXrXu1rkAmcaxf6")

        // Inisialisasi dan setup tombol logout
        btnLogout = findViewById(R.id.signOutIcon)
        btnLogout.setOnClickListener { logoutUser() }
    }

    private fun setupWebView() {
        val webSettings: WebSettings = webView.settings
        webSettings.javaScriptEnabled = true
        webView.webViewClient = WebViewClient() // Agar tetap di dalam WebView
    }

    private fun logoutUser() {
        // Hapus status login dari SharedPreferences
        val sharedPreferences = getSharedPreferences("user_session", Context.MODE_PRIVATE)
        sharedPreferences.edit().remove("isLoggedIn").apply()

        // Arahkan kembali ke halaman login
        val intent = Intent(this, AuthActivity::class.java)
        startActivity(intent)
        finish() // Tutup MainActivity agar tidak bisa kembali
    }
}
