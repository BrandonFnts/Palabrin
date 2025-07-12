package mx.uttt.palabrin.presentation.screens.userActivities.alphabet

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import mx.uttt.palabrin.R
import mx.uttt.palabrin.presentation.screens.userActivities.alphabet.content.AlphabetContent

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AlphabetScreen(navController: NavController){
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(stringResource(R.string.letter_game_title)) },
                navigationIcon = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(Icons.Default.ArrowBackIosNew, contentDescription = "Atrás")
                    }
                }
            )
        },
        content = { innerPadding ->
            AlphabetContent(innerPadding, navController)
        }
    )
}