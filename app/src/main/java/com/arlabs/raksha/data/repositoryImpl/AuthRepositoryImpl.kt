package com.arlabs.raksha.data.repositoryImpl

import android.app.Activity
import android.util.Log
import com.arlabs.raksha.domain.repository.AuthRepository
import com.arlabs.raksha.domain.util.Result
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import com.google.firebase.auth.PhoneAuthCredential
import com.google.firebase.auth.PhoneAuthOptions
import com.google.firebase.auth.PhoneAuthProvider
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import java.util.concurrent.TimeUnit

class AuthRepositoryImpl(
    private val firebaseAuth: FirebaseAuth,
    private val firestore: FirebaseFirestore
) : AuthRepository {

    override suspend fun signInWithGoogle(account: GoogleSignInAccount): Result<String> {
        return try {
            val credential = GoogleAuthProvider.getCredential(account.idToken, null)
            val authResult = firebaseAuth.signInWithCredential(credential).await()
            Log.d("AuthRepositoryImpl", "Firebase sign-in successful. User: ${authResult.user?.email}")
            Result.Success("Google sign-in successful")
        } catch (e: Exception) {
            Log.e("AuthRepositoryImpl", "Google sign-in failed: ${e.message}", e)
            Result.Failure(e.localizedMessage ?: "Unknown error during Google sign-in")
        }
    }

    override suspend fun checkUserPhone(uid: String): Result<Boolean> {
        return try {
            val document = firestore.collection("users").document(uid).get().await()
            if (document.exists() && document.contains("phoneNumber") && document.getString("phoneNumber")?.isNotEmpty() == true) {
                Result.Success(true)
            } else {
                Result.Success(false)
            }
        } catch (e: Exception) {
            Log.e("AuthRepositoryImpl", "Check user phone failed", e)
            Result.Failure(e.message ?: "Failed to check user profile")
        }
    }

    override fun sendOtp(phoneNumber: String, activity: Activity, callbacks: PhoneAuthProvider.OnVerificationStateChangedCallbacks) {
        try {
            val options = PhoneAuthOptions.newBuilder(firebaseAuth)
                .setPhoneNumber(phoneNumber)
                .setTimeout(60L, TimeUnit.SECONDS)
                .setActivity(activity)
                .setCallbacks(callbacks)
                .build()
            PhoneAuthProvider.verifyPhoneNumber(options)
        } catch (e: Exception) {
            Log.e("AuthRepositoryImpl", "Send OTP failed", e)
        }
    }

    override suspend fun verifyOtp(verificationId: String, code: String): Result<String> {
        return try {
            val credential = PhoneAuthProvider.getCredential(verificationId, code)
            val user = firebaseAuth.currentUser
            if (user != null) {
                user.linkWithCredential(credential).await()
                
                // Update Firestore
                val userMap = hashMapOf(
                    "phoneNumber" to (user.phoneNumber ?: ""),
                    "isPhoneVerified" to true,
                    "email" to (user.email ?: ""),
                    "displayName" to (user.displayName ?: "")
                )
                firestore.collection("users").document(user.uid).set(userMap, com.google.firebase.firestore.SetOptions.merge()).await()
                
                Result.Success("Phone verified successfully")
            } else {
                Result.Failure("User not signed in")
            }
        } catch (e: Exception) {
            Log.e("AuthRepositoryImpl", "Verify OTP failed", e)
            Result.Failure(e.message ?: "Verification failed")
        }
    }
}
