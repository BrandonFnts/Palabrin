package mx.uttt.palabrin.presentation.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import mx.uttt.palabrin.presentation.enums.Routes
import mx.uttt.palabrin.presentation.screens.auth.login.LoginScreen
import mx.uttt.palabrin.presentation.screens.auth.signUp.SignUpScreen
import mx.uttt.palabrin.presentation.screens.home.HomeScreen
import mx.uttt.palabrin.presentation.screens.profile.ProfileScreen
import mx.uttt.palabrin.presentation.screens.userActivities.read.ReadScreen
import mx.uttt.palabrin.presentation.screens.userActivities.write.WriteScreen

fun NavGraphBuilder.userRoutes(navController: NavController) {
    composable(Routes.HOME.name) { HomeScreen(navController) }
    composable(Routes.LOGIN.name) { LoginScreen(navController) }
    composable(Routes.SIGNUP.name) { SignUpScreen(navController) }
    composable(Routes.PROFILE.name) { ProfileScreen(navController) }
    composable(Routes.READ.name) { ReadScreen(navController) }
    composable(Routes.WRITE.name) { WriteScreen(navController) }
}