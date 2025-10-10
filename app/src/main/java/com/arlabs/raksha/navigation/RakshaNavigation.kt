package com.arlabs.raksha.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.arlabs.raksha.Presentation.MainScreens.MainScreen
import com.arlabs.raksha.Presentation.auth.AuthenticationScreen
import com.arlabs.raksha.Presentation.onBoarding.OnBoardingScreen

@Composable
fun RakshaNavigation() {
    val navController = rememberNavController()


    NavHost(navController = navController, startDestination = Routes.OnBoardingScreen) {

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
            MainScreen()
        }
    }
}