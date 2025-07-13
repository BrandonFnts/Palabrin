package mx.uttt.palabrin.presentation.screens.userActivities.draw

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarData
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import mx.uttt.palabrin.presentation.screens.userActivities.draw.content.DrawContent
import mx.uttt.palabrin.presentation.screens.userActivities.draw.content.DrawView
import mx.uttt.palabrin.presentation.screens.userActivities.draw.content.DrawViewModel

@Composable
fun DrawScreen(
    navController: NavController,
    viewModel: DrawViewModel = hiltViewModel()
) {
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(Unit) {
        viewModel.snackbarMessage.collect { message ->
            snackbarHostState.showSnackbar(message)
        }
    }

    Scaffold(
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState) { data: SnackbarData ->
                Snackbar(
                    containerColor = MaterialTheme.colorScheme.secondary,
                    snackbarData = data,
                    )
            }
        },
        content = { innerPadding ->
            DrawContent(
                paddingValues = innerPadding,
                navController = navController
            )
        }
    )
    DrawView()
}