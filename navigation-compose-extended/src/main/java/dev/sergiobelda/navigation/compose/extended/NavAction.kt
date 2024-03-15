package dev.sergiobelda.navigation.compose.extended

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.Navigator
import androidx.navigation.navOptions

/**
 * Creates a [NavAction] that is remembered across compositions.
 *
 * @param navController the [NavHostController] to use for navigation.
 */
@Composable
fun rememberNavAction(
    navController: NavHostController
): NavAction = remember(navController) {
    NavAction(navController)
}

/**
 * Handles the navigation actions.
 */
class NavAction(private val navController: NavHostController) {

    /**
     * Navigates to the given [navRoute] in the current NavGraph. If an invalid route is given, an
     * [IllegalArgumentException] will be thrown.
     *
     * @param navRoute route to the destination.
     * @param navOptions special options for this navigation operation.
     * @param navigatorExtras extras to pass to the [Navigator].
     *
     * @throws IllegalArgumentException if the given route is invalid
     */
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
