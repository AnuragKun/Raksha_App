package com.arlabs.raksha.util

import android.content.Context
import android.util.Log
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions

object GoogleSignInHelper {

    private const val WEB_CLIENT_ID = "741199930371-m1v0r1a4bg10m0eaj8mihml1rqbeqe8b.apps.googleusercontent.com"

    fun getGoogleSignInClient(context: Context): GoogleSignInClient {
        Log.d("GoogleSignInHelper", "Creating GoogleSignInClient with web client ID: $WEB_CLIENT_ID")

        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(WEB_CLIENT_ID)
            .requestEmail()
            .build()
        return  GoogleSignIn.getClient(context,gso)
    }
}