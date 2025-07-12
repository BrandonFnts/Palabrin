package mx.uttt.palabrin.presentation.screens.userActivities.alphabet.game

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import mx.uttt.palabrin.R
import mx.uttt.palabrin.presentation.screens.userActivities.alphabet.game.content.AlphabetGameContent
import mx.uttt.palabrin.presentation.screens.userActivities.alphabet.game.content.AlphabetViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AlphabetGameScreen(
    navController: NavController, viewModel: AlphabetViewModel
){
    Scaffold(
        content = { innerPadding ->
            AlphabetGameContent(padding = innerPadding, viewModel = viewModel)
        }
    )
}