package com.arlabs.raksha.domain.repository

import com.arlabs.raksha.domain.util.Result
import com.google.android.gms.auth.api.signin.GoogleSignInAccount

interface AuthRepository {

    suspend fun signInWithGoogle(account: GoogleSignInAccount) : Result<String>
}