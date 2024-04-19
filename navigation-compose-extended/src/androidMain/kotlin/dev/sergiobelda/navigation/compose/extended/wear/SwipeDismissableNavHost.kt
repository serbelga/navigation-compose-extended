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
import androidx.compose.ui.Modifier
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.wear.compose.navigation.SwipeDismissableNavHost
import androidx.wear.compose.navigation.SwipeDismissableNavHostState
import androidx.wear.compose.navigation.rememberSwipeDismissableNavHostState
import dev.sergiobelda.navigation.compose.extended.NavDestination

/**
 * TODO
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
