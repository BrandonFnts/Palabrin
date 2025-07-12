package mx.uttt.palabrin.presentation.screens.userActivities.write

import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import mx.uttt.palabrin.presentation.screens.home.content.HomeContent

@Composable
fun WriteScreen(navController: NavController){
    Scaffold(
        content = { innerPadding ->
            HomeContent(innerPadding, navController)
        }
    )
}