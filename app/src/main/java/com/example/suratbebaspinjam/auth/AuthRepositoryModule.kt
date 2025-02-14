package com.example.suratbebaspinjam.auth


import com.example.suratbebaspinjam.shared.AuthenticationRepository
import com.example.suratbebaspinjam.shared.AuthenticationRepositoryImpl
import com.example.suratbebaspinjam.shared.UserRepository
import com.example.suratbebaspinjam.shared.UserRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class AuthRepositoryModule {

    @Binds
    @Singleton
    abstract fun bindAuthenticationRepository(
        authenticationRepositoryImpl: AuthenticationRepositoryImpl
    ) : AuthenticationRepository

    @Binds
    @Singleton
    abstract fun bindUserRepository(
        userRepositoryImpl: UserRepositoryImpl
    ) : UserRepository
}
