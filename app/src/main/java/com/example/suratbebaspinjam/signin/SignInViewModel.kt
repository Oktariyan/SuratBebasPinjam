package com.example.suratbebaspinjam.signin

import androidx.lifecycle.ViewModel
import com.example.suratbebaspinjam.shared.AuthenticationRepository
import com.example.suratbebaspinjam.shared.AuthenticationResponse
import com.example.suratbebaspinjam.shared.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val authenticationRepository: AuthenticationRepository,
    private val userRepository: UserRepository
) : ViewModel() {
    fun signInEmail(email: String, password: String) = authenticationRepository.loginWithEmail(email, password)

    fun signInGoogle(): Flow<AuthenticationResponse> {
        return authenticationRepository.signInWithGoogle()
    }

    private val scope = CoroutineScope(Dispatchers.IO)
    fun createTargetAlerts() { scope.launch {
        userRepository.createTargetAlerts().collect{}
    }}
}
