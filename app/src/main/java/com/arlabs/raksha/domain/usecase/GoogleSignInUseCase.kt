package com.arlabs.raksha.domain.usecase

import com.arlabs.raksha.domain.repository.AuthRepository
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.arlabs.raksha.domain.util.Result

class GoogleSignInUseCase(
    private val repository: AuthRepository) {

    suspend operator fun invoke(account: GoogleSignInAccount): Result<String>{
        return repository.signInWithGoogle(account)
    }

}