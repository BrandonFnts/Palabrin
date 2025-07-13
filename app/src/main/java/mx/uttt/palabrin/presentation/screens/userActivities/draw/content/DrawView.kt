package mx.uttt.palabrin.presentation.screens.userActivities.draw.content

import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import mx.uttt.palabrin.domain.models.Response
import mx.uttt.palabrin.presentation.components.GenericProgressLinearIndicator

@Composable
fun DrawView (
    modifier: Modifier = Modifier,
    viewModel: DrawViewModel = hiltViewModel()
) {

    val state = viewModel.isLoading.collectAsState()
    val context = LocalContext.current

    Box(
        modifier = modifier.fillMaxSize(),
        contentAlignment = Alignment.TopStart
    ) {

        state.let { state ->

            when(state.value){
                is Response.Loading -> {
                    GenericProgressLinearIndicator()
                }

                is Response.Error -> {
                    LaunchedEffect(Unit){
                        Toast.makeText(
                            context,
                            "Error al descargar el modelo, Favor de reintentar",
                            Toast.LENGTH_SHORT
                        ).show()
                        viewModel.resetState()
                    }

                }

                is Response.Success -> {

                }

                else -> {}
            }

        }
    }
}