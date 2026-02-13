package com.arlabs.raksha.domain.usecase

import android.app.Activity
import com.arlabs.raksha.domain.repository.AuthRepository
import com.google.firebase.auth.PhoneAuthProvider

class SendOtpUseCase(
    private val repository: AuthRepository
) {
    operator fun invoke(phoneNumber: String, activity: Activity, callbacks: PhoneAuthProvider.OnVerificationStateChangedCallbacks) {
        repository.sendOtp(phoneNumber, activity, callbacks)
    }
}
