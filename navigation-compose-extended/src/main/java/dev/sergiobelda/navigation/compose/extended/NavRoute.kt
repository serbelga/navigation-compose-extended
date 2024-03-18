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

/**
 * Represents the navigation route to reach some destination. [NavAction.navigate] receives a
 * [NavRoute] object.
 *
 * @param destination Navigation destination.
 * @param arguments List of arguments passed in this route.
 */
class NavRoute<K> internal constructor(
    val destination: NavDestination<K>,
    private val arguments: Map<K, Any?> = emptyMap(),
) where K : NavArgumentKey {

    /**
     * Navigation route. It consists of [destination] id and the [arguments] values.
     */
    internal val route: String =
        destination.destinationId.addArgumentsValues()

    /**
     * The [arguments] transformed into a Map<String, Any?> where the key is the argument string key.
     */
    private val argumentsKeyStringMap: Map<String, Any?>
        get() = arguments.mapKeys { it.key.argumentKey }

    /**
     * Returns the part of the route that contains the arguments values.
     */
    private fun String.addArgumentsValues(): String {
        val optionalParameters = destination.arguments.filter {
            it.argument.isDefaultValuePresent || it.argument.isNullable
        }
        val parameters = destination.arguments.filter {
            !it.argument.isDefaultValuePresent && !it.argument.isNullable
        }

        return this + buildString {
            appendRequiredParameters(parameters)
            appendOptionalParameters(optionalParameters)
        }
    }

    /**
     * Generate the string from the list of required parameters and append if required parameters
     * have been added. For example:
     * - Given no required parameters, the result will be a single '/'.
     * - Given one required parameter, `param1`, the result will be: /value1.
     * - Given multiple required parameters, `param1` and `param2`, the result will be: /value1/value2.
     *
     * @throws IllegalArgumentException if an argument with a key is not present in the arguments.
     */
    private fun StringBuilder.appendRequiredParameters(parameters: List<NamedNavArgument>) {
        parameters.takeIf { it.isNotEmpty() }?.forEach { namedNavArgument ->
            if (argumentsKeyStringMap.containsKey(namedNavArgument.name)) {
                append(PARAM_SEPARATOR)
                append(argumentsKeyStringMap[namedNavArgument.name].toString())
            } else {
                throw IllegalArgumentException("${namedNavArgument.name} not present in arguments for destination ${destination}.")
            }
        } ?: append(PARAM_SEPARATOR)
    }

    /**
     * Generate the string from the list of optional parameters and append if optional parameters
     * have been added. For example:
     * - Given no optional parameters, the result will be an empty string.
     * - Given one optional parameter, `param1`, the result will be: ?param1=value1.
     * - Given multiple optional parameters, `param1` and `param2`, the result will be: ?param1=value1&param2=value2.
     *
     * @throws IllegalArgumentException if an argument with a key is not nullable and its value is null.
     */
    private fun StringBuilder.appendOptionalParameters(optionalParameters: List<NamedNavArgument>) {
        optionalParameters.takeIf { it.isNotEmpty() }?.let { list ->
            append(
                list.joinToString(
                    prefix = QUERY_PARAM_PREFIX,
                    separator = QUERY_PARAM_SEPARATOR,
                ) { namedNavArgument ->
                    // Check if the argument is present in the arguments map, if not, check if it has a default value.
                    if (argumentsKeyStringMap.containsKey(namedNavArgument.name)) {
                        val value = argumentsKeyStringMap[namedNavArgument.name]
                        when {
                            value != null -> {
                                "${namedNavArgument.name}=${argumentsKeyStringMap[namedNavArgument.name]}"
                            }

                            !namedNavArgument.argument.isNullable -> {
                                throw IllegalArgumentException("Argument with key ${namedNavArgument.name} is not nullable.")
                            }

                            else -> ""
                        }
                    } else {
                        val defaultValue = namedNavArgument.argument.defaultValue
                        when {
                            defaultValue != null -> {
                                "${namedNavArgument.name}=${namedNavArgument.argument.defaultValue}"
                            }

                            !namedNavArgument.argument.isNullable -> {
                                throw IllegalArgumentException("Argument with key ${namedNavArgument.name} is not nullable.")
                            }

                            else -> ""
                        }
                    }
                }.takeIf { it != QUERY_PARAM_PREFIX }.orEmpty(),
            )
        }
    }
}
