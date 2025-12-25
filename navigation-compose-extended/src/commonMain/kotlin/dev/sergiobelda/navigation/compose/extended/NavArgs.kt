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
import androidx.savedstate.read

/**
 * It resolves the argument values for given a [navBackStackEntry] for the [navDestination].
 */
class NavArgs<K> internal constructor(
    private val navDestination: NavDestination<K>,
    private val navBackStackEntry: NavBackStackEntry,
) where K : NavArgumentKey {
    private val argumentValues: Map<String, Any?> = buildArgumentValuesMap()

    private fun buildArgumentValuesMap(): Map<String, Any?> {
        val map = mutableMapOf<String, Any?>()
        navBackStackEntry.arguments?.read {
            navDestination.arguments.forEach { namedNavArgument ->
                map[namedNavArgument.name] =
                    when (namedNavArgument.argument.type) {
                        BoolType -> getBooleanOrNull(namedNavArgument.name)
                        FloatType -> getFloatOrNull(namedNavArgument.name)
                        IntType -> getIntOrNull(namedNavArgument.name)
                        LongType -> getLongOrNull(namedNavArgument.name)
                        StringType -> getStringOrNull(namedNavArgument.name)
                        else -> null
                    }
            }
        }
        return map
    }

    /**
     * Get the [Boolean] value of the argument the given its [key].
     */
    fun getBoolean(key: K): Boolean = argumentValues.getValue(key.argumentKey) as Boolean

    /**
     * Get the [Boolean] value of the argument given its [key], or null
     * if the argument is not present or value is not a [Boolean].
     */
    fun getBooleanOrNull(key: K): Boolean? = argumentValues[key.argumentKey] as? Boolean

    /**
     * Get the [Boolean] value of the argument given its [key], or [defaultValue]
     * if the argument is not present or value is not a [Boolean].
     */
    fun getBooleanOrDefault(
        key: K,
        defaultValue: Boolean,
    ): Boolean = getBooleanOrNull(key) ?: defaultValue

    /**
     * Get the [Float] value of the argument given its [key].
     */
    fun getFloat(key: K): Float = argumentValues.getValue(key.argumentKey) as Float

    /**
     * Get the [Float] value of the argument given its [key], or null
     * if the argument is not present or the key or value is not a [Float].
     */
    fun getFloatOrNull(key: K): Float? = argumentValues[key.argumentKey] as? Float

    /**
     * Get the [Float] value of the argument given its [key], or [defaultValue]
     * if the argument is not present.
     */
    fun getFloatOrDefault(
        key: K,
        defaultValue: Float,
    ): Float = getFloatOrNull(key) ?: defaultValue

    /**
     * Get the [Int] value of the argument given its [key].
     */
    fun getInt(key: K): Int = argumentValues.getValue(key.argumentKey) as Int

    /**
     * Get the [Int] value of the argument given its [key], or null
     * if the argument is not present or the key or value is not a [Int].
     */
    fun getIntOrNull(key: K): Int? = argumentValues[key.argumentKey] as? Int

    /**
     * Get the [Int] value of the argument the given its [key], or [defaultValue]
     * if the argument is not present or the key or value is not a [Int].
     */
    fun getIntOrDefault(
        key: K,
        defaultValue: Int,
    ): Int = getIntOrNull(key) ?: defaultValue

    /**
     * Get the [Long] value of the argument given its [key].
     */
    fun getLong(key: K): Long = argumentValues.getValue(key.argumentKey) as Long

    /**
     * Get the [Long] value of the argument given its [key], or null
     * if the argument is not present or the key or value is not a [Long].
     */
    fun getLongOrNull(key: K): Long? = argumentValues[key.argumentKey] as? Long

    /**
     * Get the [Long] value of the argument given its [key], or [defaultValue]
     * if the argument is not present or the key or value is not a [Long].
     */
    fun getLongOrDefault(
        key: K,
        defaultValue: Long,
    ): Long = getLongOrNull(key) ?: defaultValue

    /**
     * Get the [String] value of the argument given its [key].
     */
    fun getString(key: K): String = argumentValues.getValue(key.argumentKey) as String

    /**
     * Get the [String] value of the argument given its [key], or null
     * if the argument is not present or the key or value is not a [String].
     */
    fun getStringOrNull(key: K): String? = argumentValues[key.argumentKey] as? String

    /**
     * Get the [String] value of the argument given its [key], or [defaultValue]
     * if the argument is not present or the key or value is not a [String].
     */
    fun getStringOrDefault(
        key: K,
        defaultValue: String,
    ): String = getStringOrNull(key) ?: defaultValue
}
