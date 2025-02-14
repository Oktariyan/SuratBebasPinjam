package com.example.suratbebaspinjam.shared

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.messaging.FirebaseMessaging
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val auth: FirebaseAuth,
    private val messaging: FirebaseMessaging
) : UserRepository {

    override fun getFirebaseUser(): AuthorizationResponse {
        return auth.currentUser?.let { user ->
            AuthorizationResponse.Success(user)
        } ?: AuthorizationResponse.Error("User not found")
    }

    override fun firebaseSignOut() {
        auth.signOut()
    }

    override fun getFirebaseToken(user: FirebaseUser): Flow<TokenResponse> = flow {
        try {
            val token = user.getIdToken(true).await().token
            if (token != null) {
                emit(TokenResponse.Success(token))
            } else {
                emit(TokenResponse.Failed)
            }
        } catch (e: Exception) {
            emit(TokenResponse.Failed)
        }
    }

    override fun getPushToken(): Flow<TokenResponse> = flow {
        try {
            val token = messaging.token.await()
            if (token != null) {
                emit(TokenResponse.Success(token))
            } else {
                emit(TokenResponse.Failed)
            }
        } catch (e: Exception) {
            emit(TokenResponse.Failed)
        }
    }
}
