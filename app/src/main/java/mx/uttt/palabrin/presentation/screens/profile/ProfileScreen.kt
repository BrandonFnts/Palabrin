package mx.uttt.palabrin.presentation.screens.profile

import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import mx.uttt.palabrin.R
import mx.uttt.palabrin.presentation.components.DialogWithIcon
import mx.uttt.palabrin.presentation.enums.Routes
import mx.uttt.palabrin.presentation.screens.profile.content.ProfileContent

@Composable
fun ProfileScreen(
    navController: NavController,
    isSignedIn: Boolean
) {
    Scaffold(
        content = { innerPadding ->
            if (isSignedIn) {
                ProfileContent(
                    paddingValues = innerPadding,
                    navController = navController
                )
            } else {
                DialogWithIcon(
                    onDismissRequest = {
                        navController.popBackStack()
                    },
                    onConfirmation = {
                        navController.navigate(Routes.LOGIN.name) {
                            popUpTo(navController.graph.startDestinationId) {
                                inclusive = false
                            }
                        }
                    },
                    dialogTitle = R.string.veruser_title_login,
                    dialogText = R.string.veruser_text_login,
                    icon = R.drawable.ic_alert
                )
            }
        }
    )
}