package com.arlabs.raksha.di

import android.content.Context
import com.arlabs.raksha.data.local.UserPreferencesDataStore
import com.arlabs.raksha.data.repositoryImpl.AuthRepositoryImpl
import com.arlabs.raksha.data.repositoryImpl.UserPreferencesRepositoryImpl
import com.arlabs.raksha.domain.repository.AuthRepository
import com.arlabs.raksha.domain.repository.UserPreferencesRepository
import com.arlabs.raksha.domain.usecase.GetUserPreferencesUseCase
import com.arlabs.raksha.domain.usecase.GoogleSignInUseCase
import com.arlabs.raksha.domain.usecase.SetUserPreferencesUseCase
import com.google.firebase.auth.FirebaseAuth
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideFirebaseAuth(): FirebaseAuth = FirebaseAuth.getInstance()

    @Provides
    @Singleton
    fun provideAuthRepository(firebaseAuth: FirebaseAuth): AuthRepository {
        return AuthRepositoryImpl(firebaseAuth)
    }

    @Provides
    @Singleton
    fun provideGoogleSignInUseCase(repository: AuthRepository): GoogleSignInUseCase {
        return GoogleSignInUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideUserPreferencesDataStore(@ApplicationContext context: Context): UserPreferencesDataStore {
        return UserPreferencesDataStore(context)
    }

    @Provides
    @Singleton
    fun provideUserPreferencesRepository(dataStore: UserPreferencesDataStore): UserPreferencesRepository {
        return UserPreferencesRepositoryImpl(dataStore)
    }

    @Provides
    @Singleton
    fun provideGetUserPreferencesUseCase(repository: UserPreferencesRepository): GetUserPreferencesUseCase {
        return GetUserPreferencesUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideSetUserPreferencesUseCase(repository: UserPreferencesRepository): SetUserPreferencesUseCase {
        return SetUserPreferencesUseCase(repository)
    }
}
