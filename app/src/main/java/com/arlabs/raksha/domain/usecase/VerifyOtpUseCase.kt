package com.arlabs.raksha.domain.usecase

import com.arlabs.raksha.domain.repository.AuthRepository
import com.arlabs.raksha.domain.util.Result

class VerifyOtpUseCase(
    private val repository: AuthRepository
) {
    suspend operator fun invoke(verificationId: String, code: String): Result<String> {
        return repository.verifyOtp(verificationId, code)
    }
}
