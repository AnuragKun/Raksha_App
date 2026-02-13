package com.arlabs.raksha

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.activity.viewModels
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.arlabs.raksha.navigation.RakshaNavigation
import com.arlabs.raksha.ui.theme.RakshaTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    
    private val mainViewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen().setKeepOnScreenCondition {
            mainViewModel.startDestination.value == null
        }
        enableEdgeToEdge()
        setContent {
            RakshaTheme {
                val startDestination by mainViewModel.startDestination.collectAsState()
                
                if(startDestination != null) {
                    RakshaNavigation(startDestination = startDestination!!)
                }
            }
        }
    }
}


