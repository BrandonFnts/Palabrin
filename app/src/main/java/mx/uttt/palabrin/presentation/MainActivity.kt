package mx.uttt.palabrin.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
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
        installSplashScreen().setKeepOnScreenCondition {
            viewModel.isLoggedIn.value == null
        }
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()
            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val currentRoute = navBackStackEntry?.destination?.route
            val isLoggedIn by viewModel.isLoggedIn.collectAsState()

            PalabrinTheme {
                Scaffold(
                    bottomBar = {
                        BottomNavBar(navController, visible = viewModel.verifyRouteBottom(currentRoute))
                    }
                ) { paddingValues ->
                    if (isLoggedIn != null) {
                        NavHost(
                            navController = navController,
                            startDestination = Routes.HOME.name
                        ) {
                            userRoutes(navController, isLoggedIn == true)
                        }
                    } else {
                        Box(
                            modifier = Modifier
                                .fillMaxSize()
                                .padding(paddingValues),
                            contentAlignment = Alignment.Center
                        ) {
                            CircularProgressIndicator()
                        }
                    }
                }
            }
        }
    }
}