/*
 * Copyright 2024 Sergio Belda
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

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
    navController: NavHostController,
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
        navigatorExtras: Navigator.Extras? = null,
    ) {
        if (navRoute.navDestination is TopLevelNavDestination) {
            navController.navigate(
                route = navRoute.route,
                navOptions = navOptions {
                    navController.graph.findStartDestination().route?.let {
                        // Pop up to the start destination of the graph to
                        // avoid building up a large stack of destinations
                        // on the back stack as users select items
                        popUpTo(it) {
                            saveState = true
                        }

                        // Avoid multiple copies of the same destination when
                        // reselecting the same item
                        launchSingleTop = true
                        // Restore state when reselecting a previously selected item
                        restoreState = true
                    }
                },
                navigatorExtras = navigatorExtras,
            )
        } else {
            navController.navigate(navRoute.route, navOptions, navigatorExtras)
        }
    }

    /**
     * Attempts to pop the controller's back stack.
     *
     * @return true if the stack was popped at least once and the user has been navigated to
     * another destination, false otherwise
     */
    fun popBackStack(): Boolean = navController.popBackStack()

    /**
     * Pops the back stack to a specific [navRoute].
     *
     * @param navRoute route to the destination.
     * @param inclusive whether the given destination should also be popped.
     * @param saveState true to save the state of the popped destination.
     *
     * @return true if the stack was popped at least once and the user has been navigated to
     * another destination, false otherwise
     */
    fun <K : NavArgumentKey> popBackStack(
        navRoute: NavRoute<K>,
        inclusive: Boolean,
        saveState: Boolean = false,
    ): Boolean =
        navController.popBackStack(
            route = navRoute.route,
            inclusive = inclusive,
            saveState = saveState,
        )

    /**
     * Attempts to navigate up in the navigation hierarchy.
     *
     * @return true if navigation was successful, false otherwise
     */
    fun navigateUp() = navController.navigateUp()
}
