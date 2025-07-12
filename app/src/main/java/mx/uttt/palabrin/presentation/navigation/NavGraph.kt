package mx.uttt.palabrin.presentation.navigation

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import mx.uttt.palabrin.presentation.enums.Routes
import mx.uttt.palabrin.presentation.screens.auth.login.LoginScreen
import mx.uttt.palabrin.presentation.screens.auth.signUp.SignUpScreen
import mx.uttt.palabrin.presentation.screens.home.HomeScreen
import mx.uttt.palabrin.presentation.screens.profile.ProfileScreen
import mx.uttt.palabrin.presentation.screens.userActivities.alphabet.AlphabetScreen
import mx.uttt.palabrin.presentation.screens.userActivities.alphabet.game.AlphabetGameScreen
import mx.uttt.palabrin.presentation.screens.userActivities.alphabet.game.content.AlphabetViewModel
import mx.uttt.palabrin.presentation.screens.userActivities.read.ReadScreen
import mx.uttt.palabrin.presentation.screens.userActivities.write.WriteScreen

fun NavGraphBuilder.userRoutes(navController: NavController, isSignedIn: Boolean) {
    composable(Routes.HOME.name) { HomeScreen(navController) }
    composable(Routes.LOGIN.name) { LoginScreen(navController) }
    composable(Routes.SIGNUP.name) { SignUpScreen(navController) }
    composable(Routes.PROFILE.name) { ProfileScreen(navController, isSignedIn) }
    composable(Routes.READ.name) { ReadScreen(navController) }
    composable(Routes.WRITE.name) { WriteScreen(navController) }
    composable(Routes.ALPHABET.name) { AlphabetScreen(navController) }
    composable(
        route = "LETTERGAME/{letter}",
        arguments = listOf(navArgument("letter") { type = NavType.StringType })
    ) {
        val viewModel: AlphabetViewModel = hiltViewModel()
        AlphabetGameScreen(
            navController = navController,
            viewModel = viewModel
        )
    }
}