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
import com.google.firebase.firestore.FirebaseFirestore
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
    fun provideFirebaseFirestore(): com.google.firebase.firestore.FirebaseFirestore = com.google.firebase.firestore.FirebaseFirestore.getInstance()

    @Provides
    @Singleton
    fun provideAuthRepository(
        firebaseAuth: FirebaseAuth,
        firestore: com.google.firebase.firestore.FirebaseFirestore
    ): AuthRepository {
        return AuthRepositoryImpl(firebaseAuth, firestore)
    }

    @Provides
    @Singleton
    fun provideGoogleSignInUseCase(repository: AuthRepository): GoogleSignInUseCase {
        return GoogleSignInUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideCheckUserPhoneUseCase(repository: AuthRepository): com.arlabs.raksha.domain.usecase.CheckUserPhoneUseCase {
        return com.arlabs.raksha.domain.usecase.CheckUserPhoneUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideSendOtpUseCase(repository: AuthRepository): com.arlabs.raksha.domain.usecase.SendOtpUseCase {
        return com.arlabs.raksha.domain.usecase.SendOtpUseCase(repository)
    }

    @Provides
    @Singleton
    fun provideVerifyOtpUseCase(repository: AuthRepository): com.arlabs.raksha.domain.usecase.VerifyOtpUseCase {
        return com.arlabs.raksha.domain.usecase.VerifyOtpUseCase(repository)
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
    @Provides
    @Singleton
    fun provideReportRepository(firestore: FirebaseFirestore): com.arlabs.raksha.domain.repository.ReportRepository {
        return com.arlabs.raksha.data.repositoryImpl.ReportRepositoryImpl(firestore)
    }

    @Provides
    @Singleton
    fun provideFusedLocationProviderClient(@ApplicationContext context: android.content.Context): com.google.android.gms.location.FusedLocationProviderClient {
        return com.google.android.gms.location.LocationServices.getFusedLocationProviderClient(context)
    }
}
