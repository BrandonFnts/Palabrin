package mx.uttt.palabrin.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import mx.uttt.palabrin.presentation.enums.Routes
import mx.uttt.palabrin.presentation.navigation.userRoutes
import mx.uttt.palabrin.presentation.components.BottomNavBar
import mx.uttt.palabrin.presentation.components.TopAppBar
import mx.uttt.palabrin.presentation.ui.theme.PalabrinTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val viewModel by viewModels<MainActivityViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()
            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val currentRoute = navBackStackEntry?.destination?.route

            PalabrinTheme {
                Scaffold(
                    topBar = {
                        TopAppBar(visible = viewModel.verifyRouteTop(currentRoute))
                    },
                    bottomBar = {
                        BottomNavBar(navController, visible = viewModel.verifyRouteBottom(currentRoute))
                    }
                ) { paddingValues ->
                    NavHost(
                        modifier = Modifier.padding(paddingValues),
                        navController = navController,
                        startDestination = Routes.HOME.name
                    ) {
                        userRoutes(navController)
                    }
                }
            }
        }
    }
}