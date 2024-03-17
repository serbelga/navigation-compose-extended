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

import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavType.Companion.IntType
import androidx.navigation.NavType.Companion.StringType

/**
 * It resolves the argument values for given a [navBackStackEntry] for the [destination].
 */
class NavSafeArgs<K> internal constructor(
    private val destination: NavDestination<K>,
    private val navBackStackEntry: NavBackStackEntry,
) where K : NavArgumentKey {

    private val argumentValues: Map<String, Any?> = buildArgumentValuesMap()

    private fun buildArgumentValuesMap(): Map<String, Any?> {
        val map = mutableMapOf<String, Any?>()
        navBackStackEntry.arguments?.apply {
            destination.arguments.forEach { namedNavArgument ->
                map[namedNavArgument.name] = when (namedNavArgument.argument.type) {
                    StringType -> getString(namedNavArgument.name)
                    IntType -> getInt(namedNavArgument.name)
                    else -> null
                }
            }
        }
        return map
    }

    /**
     * Get the [String] value of the argument the given its [key].
     */
    fun getString(key: K): String? =
        argumentValues.getValue(key.argumentKey) as? String
}
