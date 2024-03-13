package dev.sergiobelda.navigation.compose.extension

import androidx.navigation.NamedNavArgument

/**
 * Represents the navigation route to reach some destination. [Action.navigate] receives a
 * [NavRoute] object.
 *
 * @param destination Navigation destination.
 * @param arguments List of arguments passed in this route.
 */
abstract class NavRoute<K>(
    val destination: NavDestination<K>,
    private val arguments: Map<K, Any?> = emptyMap()
) where K : NavArgumentKey {

    /**
     * Navigation route. It consists of [destination] id and the [arguments] values.
     */
    internal val route: String =
        destination.destinationId.addArgumentsValues()

    private val argumentsKeyStringMap: Map<String, Any?> get() =
        arguments.mapKeys { it.key.argumentKey }

    private fun String.addArgumentsValues(): String {
        val parameters = destination.arguments.filter {
            !it.argument.isDefaultValuePresent && !it.argument.isNullable
        }
        val optionalParameters = destination.arguments.filter {
            it.argument.isDefaultValuePresent || it.argument.isNullable
        }

        return this + buildString {
            parameters.forEach { namedNavArgument ->
                if (argumentsKeyStringMap.containsKey(namedNavArgument.name)) {
                    append(PARAM_SEPARATOR)
                    append(argumentsKeyStringMap[namedNavArgument.name].toString())
                } else throw Exception("Not present in arguments")
            }
            optionalParameters.takeIf { it.isNotEmpty() }?.let { list ->
                appendOptionalParameters(list)
            }
        }
    }

    private fun StringBuilder.appendOptionalParameters(list: List<NamedNavArgument>) {
        append(
            list.joinToString(
                prefix = QUERY_PARAM_PREFIX,
                separator = QUERY_PARAM_SEPARATOR
            ) { namedNavArgument ->
                if (argumentsKeyStringMap.containsKey(namedNavArgument.name)) {
                    val value = argumentsKeyStringMap[namedNavArgument.name]
                    when {
                        value != null -> {
                            "${namedNavArgument.name}=${argumentsKeyStringMap[namedNavArgument.name]}"
                        }
                        !namedNavArgument.argument.isNullable -> {
                            // TODO:
                            throw Exception()
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
                            // TODO:
                            throw Exception()
                        }
                        else -> ""
                    }
                }
            }
        )
    }
}
