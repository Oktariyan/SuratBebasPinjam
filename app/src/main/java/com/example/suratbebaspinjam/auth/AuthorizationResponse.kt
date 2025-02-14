package com.example.suratbebaspinjam.auth

import com.google.firebase.auth.FirebaseUser

sealed interface AuthorizationResponse {
    data class Success(val user: FirebaseUser) : AuthorizationResponse
    data class Error(val message: String) : AuthorizationResponse
}
