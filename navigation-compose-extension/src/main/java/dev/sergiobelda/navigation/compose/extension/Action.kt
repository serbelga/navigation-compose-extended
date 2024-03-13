package dev.sergiobelda.navigation.compose.extension

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.Navigator
import androidx.navigation.navOptions

@Composable
fun rememberAction(
    navController: NavHostController
): Action = remember(navController) {
    Action(navController)
}

class Action(private val navController: NavHostController) {

    fun <K : NavArgumentKey> navigate(
        navRoute: NavRoute<K>,
        navOptions: NavOptions? = null,
        navigatorExtras: Navigator.Extras? = null
    ) {
        if (navRoute.destination is TopLevelNavDestination) {
            navController.navigate(
                route = navRoute.route,
                navOptions = navOptions {
                    // Pop up to the start destination of the graph to
                    // avoid building up a large stack of destinations
                    // on the back stack as users select items
                    popUpTo(navController.graph.findStartDestination().id) {
                        saveState = true
                    }
                    // Avoid multiple copies of the same destination when
                    // reselecting the same item
                    launchSingleTop = true
                    // Restore state when reselecting a previously selected item
                    restoreState = true
                },
                navigatorExtras = navigatorExtras
            )
        } else {
            navController.navigate(navRoute.route, navOptions, navigatorExtras)
        }
    }
}
