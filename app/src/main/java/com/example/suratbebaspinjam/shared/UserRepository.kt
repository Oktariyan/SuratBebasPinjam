package com.example.suratbebaspinjam.shared

import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    fun getFirebaseUser(): AuthorizationResponse
    fun firebaseSignOut()
    fun getFirebaseToken(user: FirebaseUser): Flow<TokenResponse>
    fun getPushToken(): Flow<TokenResponse>
    fun createTargetAlerts(): Flow<ResponseWrapper<TargetAlerts>>
    fun getTargetAlerts(): List<String>
}
