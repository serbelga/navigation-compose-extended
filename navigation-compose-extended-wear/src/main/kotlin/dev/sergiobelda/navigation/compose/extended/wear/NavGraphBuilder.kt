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
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavDeepLink
import androidx.navigation.NavGraphBuilder
import androidx.wear.compose.navigation.WearNavigator
import androidx.wear.compose.navigation.composable
import dev.sergiobelda.navigation.compose.extended.NavDestination

/**
 * Adds the content composable to the [NavGraphBuilder] as a [WearNavigator.Destination].
 *
 * @param navDestination navigation destination associated with the composable
 * @param arguments list of arguments to associate with destination
 * @param deepLinks list of deep links to associate with the destinations
 * @param content composable for the destination
 */
fun NavGraphBuilder.composable(
    navDestination: NavDestination<*>,
    arguments: List<NamedNavArgument> = navDestination.arguments,
    deepLinks: List<NavDeepLink> = navDestination.deepLinks,
    content: @Composable (NavBackStackEntry) -> Unit,
) {
    composable(
        route = navDestination.route,
        arguments = arguments,
        deepLinks = deepLinks,
        content = content,
    )
}
