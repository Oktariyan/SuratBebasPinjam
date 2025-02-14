package com.example.suratbebaspinjam.shared

sealed interface AuthenticationResponse {
    data object Loading : AuthenticationResponse
    data object Success : AuthenticationResponse
    data class Error(val message: String) : AuthenticationResponse
}
