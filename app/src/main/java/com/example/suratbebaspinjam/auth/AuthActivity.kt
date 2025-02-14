package com.example.suratbebaspinjam.auth

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.commit
import com.example.suratbebaspinjam.R
import com.example.suratbebaspinjam.signin.SignInFragment
import com.example.suratbebaspinjam.signup.SignUpFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AuthActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_auth)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragment_container, SignInFragment())
                .commit()
        }
    }

    // Pindah ke SignUpFragment
    fun switchToSignUp() {
        supportFragmentManager.commit {
            replace(R.id.fragment_container, SignUpFragment())
            addToBackStack(null) // Bisa dihapus jika tidak ingin kembali ke SignInFragment
        }
    }

    // Pindah ke SignInFragment
    fun switchToSignIn() {
        supportFragmentManager.commit {
            replace(R.id.fragment_container, SignInFragment())
            addToBackStack(null)
        }
    }
}
