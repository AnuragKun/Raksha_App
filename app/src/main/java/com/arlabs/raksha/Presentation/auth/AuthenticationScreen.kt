package com.arlabs.raksha.Presentation.auth

import android.app.Activity
import android.util.Log.e
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.arlabs.raksha.Common.GradientBox
import com.arlabs.raksha.Common.TopCurveShape
import com.arlabs.raksha.R
import com.arlabs.raksha.navigation.Routes
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.common.api.ApiException
import com.arlabs.raksha.domain.util.Result
import com.arlabs.raksha.util.GoogleSignInHelper

@Composable
fun AuthenticationScreen(
    navHostController: NavHostController,
    authViewModel: AuthViewModel = hiltViewModel()
) {

    var showError by remember { mutableStateOf(false) }
    var errorMessage by remember { mutableStateOf("") }

    val context = LocalContext.current
    val authState by authViewModel.authState.collectAsState()

    val googleSignInLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        when (result.resultCode){
            Activity.RESULT_OK -> {
                val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
                try {
                    val account = task.getResult(ApiException::class.java)
                    if(account!=null) {
                        authViewModel.signInWithGoogle(account)
                    } else {
                        showError = true
                        errorMessage = "Google sign in failed: Account is null"
                    }
                } catch (e: ApiException) {
                    showError = true
                    errorMessage = "Google sign in failed: ${e.statusCode} - ${e.message}"
                }
            }
            Activity.RESULT_CANCELED -> {
                showError = true
                errorMessage = "Google sign in was canceled"
            }
            else -> {
                showError = true
                errorMessage = "Google sign in failed with result code: ${result.resultCode}"
            }
        }
    }

    LaunchedEffect(authState) {
        when (val currentState = authState) {
            is Result.Success -> {
                navHostController.navigate(Routes.MainScreen){
                    popUpTo(Routes.AuthenticationScreen) {inclusive = true}
                }
            }
            is Result.Failure -> {
                showError = true
                errorMessage = currentState.message
            }
            Result.Idle, Result.Loading -> {

            }
        }
    }

    Scaffold { innerPadding ->

        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.5f),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Image(
                    painter = painterResource(id = R.drawable.ic_raksha_logo),
                    contentDescription = "",
                    modifier = Modifier.size(250.dp)
                )
            }

            GradientBox(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.6f)
                    .align(Alignment.BottomCenter)
                    .clip(TopCurveShape())
            ) {
                Column(
                    modifier = Modifier.padding(innerPadding),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Spacer(modifier = Modifier.height(90.dp))

                    Text(
                        "RAKSHA",
                        style = TextStyle(
                            fontSize = 30.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.White
                        )
                    )

                    Spacer(modifier = Modifier.height(24.dp))

                    Text(
                        "Sign in to continue protecting yourself!",
                        fontSize = 20.sp,
                        color = Color.White,
                        fontWeight = FontWeight.Light
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    Button(
                        onClick = {val googleSignInClient = GoogleSignInHelper.getGoogleSignInClient(context)
                            val signInIntent = googleSignInClient.signInIntent
                            googleSignInLauncher.launch(signInIntent) },
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 24.dp),
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.White.copy(alpha = 0.3f),
                            contentColor = Color.White
                        )
                    ) {
                        Image(
                            painter = painterResource(id = R.drawable.ic_google),
                            contentDescription = null,
                            modifier = Modifier.size(24.dp)
                        )
                        Spacer(modifier = Modifier.width(12.dp))
                        Text("Continue With Google")
                    }

                    Spacer(modifier = Modifier.height(60.dp))

                    Text("Your Safety is our Priority",
                        fontSize = 20.sp,
                        color = Color.White,
                        fontWeight = FontWeight.SemiBold
                    )
                }
            }
        }
    }
}

//@Composable
//@Preview(showSystemUi = true)
//fun AuthScreenPreview(){
//    AuthenticationScreen()
//}