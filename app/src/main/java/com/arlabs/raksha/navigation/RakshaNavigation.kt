package com.arlabs.raksha.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.arlabs.raksha.features.main.MainScreen
import com.arlabs.raksha.features.auth.AuthenticationScreen
import com.arlabs.raksha.features.onboarding.OnBoardingScreen

@Composable
fun RakshaNavigation(startDestination: String) {
    val navController = rememberNavController()


    NavHost(navController = navController, startDestination = startDestination) {

        composable(Routes.OnBoardingScreen) {
            OnBoardingScreen(
                onGetStartedClicked = {
                    navController.navigate(Routes.AuthenticationScreen) {
                        popUpTo(Routes.OnBoardingScreen) {
                            inclusive = true
                        }
                    }
                }
            )
        }

        composable(Routes.AuthenticationScreen) {
            AuthenticationScreen(
                navHostController = navController
            )

        }

        composable(Routes.MainScreen) {
            MainScreen(navController = navController)
        }

        composable(Routes.PhoneNumberScreen) {
             val parentEntry = remember(it) {
                 navController.getBackStackEntry(Routes.AuthenticationScreen)
             }
             val viewModel = androidx.hilt.navigation.compose.hiltViewModel<com.arlabs.raksha.features.auth.AuthViewModel>(parentEntry)
             com.arlabs.raksha.features.auth.PhoneNumberScreen(navController, viewModel)
        }

        composable(Routes.OtpScreen) {
             val parentEntry = remember(it) {
                 navController.getBackStackEntry(Routes.AuthenticationScreen)
             }
             val viewModel = androidx.hilt.navigation.compose.hiltViewModel<com.arlabs.raksha.features.auth.AuthViewModel>(parentEntry)
             com.arlabs.raksha.features.auth.OtpScreen(navController, viewModel)
        }

        composable(Routes.ProfileScreen) {
            com.arlabs.raksha.features.profile.ProfileScreen(navController = navController)
        }

        composable(Routes.VerifyAccountScreen) {
            com.arlabs.raksha.features.profile.VerifyAccountScreen(navController = navController)
        }
    }
}