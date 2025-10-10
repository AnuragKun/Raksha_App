package com.arlabs.raksha.data.repositoryImpl

import android.util.Log
import com.arlabs.raksha.domain.repository.AuthRepository
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.coroutines.tasks.await
import com.arlabs.raksha.domain.util.Result

class AuthRepositoryImpl(
    private val firebaseAuth: FirebaseAuth
) : AuthRepository {


    override suspend fun signInWithGoogle(account: GoogleSignInAccount): Result<String> {
        return try {
            Log.d("AuthRepositoryImpl", "Starting Google sign-in with account: ${account.email}")
            Log.d("AuthRepositoryImpl", "ID Token available: ${account.idToken != null}")

            val credential = GoogleAuthProvider.getCredential(account.idToken, null)
            Log.d("AuthRepositoryImpl", "Created credential, signing in with Firebase")

            val authResult = firebaseAuth.signInWithCredential(credential).await()
            Log.d("AuthRepositoryImpl", "Firebase sign-in successful. User: ${authResult.user?.email}")

            Result.Success("Google sign-in successful")
        } catch (e: Exception) {
            Log.e("AuthRepositoryImpl", "Google sign-in failed: ${e.message}", e)
            Result.Failure(e.localizedMessage ?: "Unknown error during Google sign-in")
        }
    }
}
