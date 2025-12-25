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

import androidx.navigation.NamedNavArgument
import androidx.navigation.NavArgumentBuilder
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavDeepLink
import androidx.navigation.navArgument

/**
 * Represents some Destination in the Navigation graph. It's defined by a
 * [destinationId] and a list of [arguments].
 */
abstract class NavDestination<K> where K : NavArgumentKey {
    /**
     * Identifier of Destination without arguments definition.
     */
    abstract val destinationId: String

    /**
     * Define the navigation arguments to this destination.
     */
    protected open val argumentsMap: Map<K, NavArgumentBuilder.() -> Unit> = emptyMap()

    /**
     * Define the deep link URIs to this destination.
     */
    protected open val deepLinkUris: List<String> = emptyList()

    /**
     * List of [NamedNavArgument] associated with destination.
     */
    val arguments: List<NamedNavArgument>
        get() =
            argumentsMap.map {
                navArgument(it.key.argumentKey, it.value)
            }

    /**
     * List of [NavDeepLink] associated with destination.
     */
    val deepLinks: List<NavDeepLink>
        get() =
            deepLinkUris.map {
                //  navDeepLink {
                //      uriPattern = it + argumentsRoute
                //  }
                NavDeepLink.Builder().setUriPattern(it + argumentsRoute).build()
            }

    /**
     * Route to this destination. It consists of [destinationId] and [arguments].
     */
    val route get() = destinationId + argumentsRoute

    /**
     * Get the arguments route template for this destination.
     */
    private val argumentsRoute: String
        get() {
            val parameters =
                arguments.filter { !it.argument.isDefaultValuePresent && !it.argument.isNullable }
            val optionalParameters =
                arguments.filter { it.argument.isDefaultValuePresent || it.argument.isNullable }

            return parameters.joinToString(
                prefix = PARAM_SEPARATOR,
                separator = PARAM_SEPARATOR,
            ) {
                "{${it.name}}"
            } +
                optionalParameters
                    .takeIf { it.isNotEmpty() }
                    ?.joinToString(
                        prefix = QUERY_PARAM_PREFIX,
                        separator = QUERY_PARAM_SEPARATOR,
                    ) { "${it.name}={${it.name}}" }
                    .orEmpty()
        }

    /**
     * Returns the [NavRoute] associated with this destination given some [arguments].
     */
    fun navRoute(vararg arguments: Pair<K, Any?>): NavRoute<K> = NavRoute(this, arguments.toMap())

    /**
     * Returns a [NavArgs] instance for this destination.
     */
    fun navArgs(navBackStackEntry: NavBackStackEntry): NavArgs<K> = NavArgs(this, navBackStackEntry)

    override fun toString(): String = destinationId
}

/**
 * Represents a TopLevel [NavDestination].
 */
abstract class TopLevelNavDestination<K> : NavDestination<K>() where K : NavArgumentKey
