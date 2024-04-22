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

package dev.sergiobelda.navigation.compose.extended.wear

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.wear.compose.navigation.SwipeDismissableNavHost
import androidx.wear.compose.navigation.SwipeDismissableNavHostState
import androidx.wear.compose.navigation.rememberSwipeDismissableNavHostState
import dev.sergiobelda.navigation.compose.extended.NavDestination

/**
 * Provides a place in the Compose hierarchy for self-contained navigation to occur,
 * with backwards navigation provided by a swipe gesture.
 *
 * Once this is called, any Composable within the given [NavGraphBuilder] can be navigated to from
 * the provided [navController].
 *
 * The builder passed into this method is [remember]ed. This means that for this NavHost, the
 * contents of the builder cannot be changed.
 *
 * This function wraps [androidx.wear.compose.navigation.SwipeDismissableNavHost].
 *
 * @param navController The navController for this host
 * @param startNavDestination The start [NavDestination]
 * @param modifier The modifier to be applied to the layout
 * @param userSwipeEnabled [Boolean] Whether swipe-to-dismiss gesture is enabled.
 * @param state State containing information about ongoing swipe and animation.
 * @param route The route for the graph
 * @param builder The builder used to construct the graph
 */
@Composable
fun SwipeDismissableNavHost(
    navController: NavHostController,
    startNavDestination: NavDestination<*>,
    modifier: Modifier = Modifier,
    userSwipeEnabled: Boolean = true,
    state: SwipeDismissableNavHostState = rememberSwipeDismissableNavHostState(),
    route: String? = null,
    builder: NavGraphBuilder.() -> Unit,
) {
    SwipeDismissableNavHost(
        navController = navController,
        startDestination = startNavDestination.route,
        modifier = modifier,
        userSwipeEnabled = userSwipeEnabled,
        state = state,
        route = route,
        builder = builder,
    )
}
