package com.example.suratbebaspinjam.shared

sealed interface TokenResponse {
    data class Success(val token: String) : TokenResponse
    data object Failed : TokenResponse
}
