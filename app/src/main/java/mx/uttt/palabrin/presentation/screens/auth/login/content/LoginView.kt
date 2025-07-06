package mx.uttt.palabrin.presentation.screens.auth.login.content

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import mx.uttt.palabrin.R
import mx.uttt.palabrin.domain.models.Response
import mx.uttt.palabrin.presentation.enums.Routes

@Composable
fun LoginView(
    modifier: Modifier,
    navController: NavController,
    viewModel: LoginViewModel = hiltViewModel()
) {
    val login by viewModel.isLoading.collectAsState()
    val context = LocalContext.current

    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        login?.let { islogin ->
            when (islogin) {
                is Response.Loading -> {

                    CircularProgressIndicator()

                }

                is Response.Error -> {

                    viewModel.resetInitState()

                }

                is Response.Success -> {
                    Toast.makeText(
                        context,
                        stringResource(id = R.string.auth_success_login),
                        Toast.LENGTH_LONG
                    ).show()
                    navController.navigate(Routes.HOME.name) {
                        popUpTo(navController.graph.startDestinationId) {
                            inclusive = false
                        }
                    }
                    viewModel.resetInitState()
                }

                else -> {}
            }
        }
    }
}