package mx.uttt.palabrin.presentation.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import mx.uttt.palabrin.presentation.enums.Routes

@Composable
fun BottomNavBar(navController: NavHostController, visible : Boolean) {
    AnimatedVisibility(
        visible = visible,
        enter = slideInVertically() + expandVertically() + fadeIn(),
        exit = slideOutVertically() + shrinkVertically() + fadeOut()
    ) {
        NavigationBar(
            containerColor = MaterialTheme.colorScheme.surface,
        ) {
            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val currentDestination = navBackStackEntry?.destination

            navigationItems.forEach { navItem ->
                NavigationBarItem(
                    selected = currentDestination?.hierarchy?.any { it.route == navItem.route } == true,
                    onClick = {
                        if (navController.currentDestination?.route != navItem.route) {
                            navController.navigate(navItem.route) {
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    },
                    label = { Text(text = navItem.title) },
                    alwaysShowLabel = true,
                    icon = { BadgedBox(
                        badge = {
                            if (navItem.hasBadge != null){
                                Badge { Text(text = navItem.hasBadge.toString()) }
                            } else if (navItem.hasNews) {
                                Badge()
                            }
                        }
                    ) {
                        Icon(
                            imageVector = if (currentDestination?.hierarchy?.any { it.route == navItem.route } == true) {
                                navItem.selectedIcon
                            } else navItem.unselectedIcon, contentDescription = navItem.title)
                    } },
                )
            }
        }
    }
}

@Composable
fun TopAppBar(visible : Boolean = true) {
    AnimatedVisibility(
        visible = visible,
        enter = slideInVertically() + expandVertically() + fadeIn(),
        exit = slideOutVertically() + shrinkVertically() + fadeOut()
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(120.dp)
                .background(MaterialTheme.colorScheme.background),
            contentAlignment = Alignment.BottomStart
        ) {
            AppLogo()
            HorizontalDivider(
                modifier = Modifier
                    .padding(top = 10.dp)
                    .fillMaxWidth()
                    .height(1.dp),
                color = Color.LightGray
            )

        }
    }
}

data class NavigationItems(
    val title: String,
    val route: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val hasNews: Boolean,
    val hasBadge: Int? = null
)

val navigationItems = listOf(
    NavigationItems(
        title = "Inicio",
        route = Routes.HOME.name,
        selectedIcon = Icons.Filled.Home,
        unselectedIcon = Icons.Outlined.Home,
        hasNews = false
    ),
    NavigationItems(
        title = "Perfil",
        route = Routes.PROFILE.name,
        selectedIcon = Icons.Filled.AccountCircle,
        unselectedIcon = Icons.Outlined.AccountCircle,
        hasNews = false
    )
)