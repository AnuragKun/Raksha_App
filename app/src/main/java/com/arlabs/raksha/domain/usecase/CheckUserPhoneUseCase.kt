package com.arlabs.raksha.domain.usecase

import com.arlabs.raksha.domain.repository.AuthRepository
import com.arlabs.raksha.domain.util.Result

class CheckUserPhoneUseCase(
    private val repository: AuthRepository
) {
    suspend operator fun invoke(uid: String): Result<Boolean> {
        return repository.checkUserPhone(uid)
    }
}
