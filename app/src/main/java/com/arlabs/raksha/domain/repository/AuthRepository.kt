package com.arlabs.raksha.domain.repository

import android.app.Activity
import com.arlabs.raksha.domain.util.Result
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.firebase.auth.PhoneAuthProvider

interface AuthRepository {

    suspend fun signInWithGoogle(account: GoogleSignInAccount) : Result<String>

    suspend fun checkUserPhone(uid: String): Result<Boolean>

    fun sendOtp(phoneNumber: String, activity: Activity, callbacks: PhoneAuthProvider.OnVerificationStateChangedCallbacks)

    suspend fun verifyOtp(verificationId: String, code: String): Result<String>
}