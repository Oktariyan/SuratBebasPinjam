package com.example.suratbebaspinjam

import com.example.suratbebaspinjam.auth.AuthActivity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import dagger.hilt.android.AndroidEntryPoint


class SplashScreenActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val prefs = getSharedPreferences("AppPrefs", MODE_PRIVATE)
        val isLoggedIn = prefs.getBoolean("isLoggedIn", false)

        if (isLoggedIn) {
            startActivity(Intent(this, MainActivity::class.java))
        } else {
            startActivity(
                Intent(
                    this,
                    AuthActivity::class.java
                )
            ) // Activity untuk SignInFragment & SignUpFragment
        }

        finish()
    }
}
