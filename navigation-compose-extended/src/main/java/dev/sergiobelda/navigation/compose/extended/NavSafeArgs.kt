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
import androidx.navigation.NavType.Companion.BoolType
import androidx.navigation.NavType.Companion.FloatType
import androidx.navigation.NavType.Companion.IntType
import androidx.navigation.NavType.Companion.LongType
import androidx.navigation.NavType.Companion.StringType

/**
 * It resolves the argument values for given a [navBackStackEntry] for the [navDestination].
 */
class NavSafeArgs<K> internal constructor(
    private val navDestination: NavDestination<K>,
    private val navBackStackEntry: NavBackStackEntry,
) where K : NavArgumentKey {

    private val argumentValues: Map<String, Any?> = buildArgumentValuesMap()

    private fun buildArgumentValuesMap(): Map<String, Any?> {
        val map = mutableMapOf<String, Any?>()
        navBackStackEntry.arguments?.apply {
            navDestination.arguments.forEach { namedNavArgument ->
                map[namedNavArgument.name] = when (namedNavArgument.argument.type) {
                    BoolType -> getBoolean(namedNavArgument.name)
                    FloatType -> getFloat(namedNavArgument.name)
                    IntType -> getInt(namedNavArgument.name)
                    LongType -> getLong(namedNavArgument.name)
                    StringType -> getString(namedNavArgument.name)
                    else -> null
                }
            }
        }
        return map
    }

    /**
     * Get the [Boolean] value of the argument the given its [key].
     */
    fun getBoolean(key: K): Boolean? =
        argumentValues.getValue(key.argumentKey) as? Boolean

    /**
     * Get the [Boolean] value of the argument the given its [key], or
     * [defaultValue] if the argument is not present.
     */
    fun getBooleanOrDefault(key: K, defaultValue: Boolean): Boolean =
        getBoolean(key) ?: defaultValue

    /**
     * Get the [Float] value of the argument the given its [key].
     */
    fun getFloat(key: K): Float? =
        argumentValues.getValue(key.argumentKey) as? Float

    /**
     * Get the [Float] value of the argument the given its [key], or
     * [defaultValue] if the argument is not present.
     */
    fun getFloatOrDefault(key: K, defaultValue: Float): Float =
        getFloat(key) ?: defaultValue

    /**
     * Get the [Int] value of the argument the given its [key].
     */
    fun getInt(key: K): Int? =
        argumentValues.getValue(key.argumentKey) as? Int

    /**
     * Get the [Int] value of the argument the given its [key], or
     * [defaultValue] if the argument is not present.
     */
    fun getIntOrDefault(key: K, defaultValue: Int): Int =
        getInt(key) ?: defaultValue

    /**
     * Get the [Long] value of the argument the given its [key].
     */
    fun getLong(key: K): Long? =
        argumentValues.getValue(key.argumentKey) as? Long

    /**
     * Get the [Long] value of the argument the given its [key], or
     * [defaultValue] if the argument is not present.
     */
    fun getLongOrDefault(key: K, defaultValue: Long): Long =
        getLong(key) ?: defaultValue

    /**
     * Get the [String] value of the argument the given its [key].
     */
    fun getString(key: K): String? =
        argumentValues.getValue(key.argumentKey) as? String

    /**
     * Get the [String] value of the argument the given its [key], or
     * [defaultValue] if the argument is not present.
     */
    fun getStringOrDefault(key: K, defaultValue: String): String =
        getString(key) ?: defaultValue
}
