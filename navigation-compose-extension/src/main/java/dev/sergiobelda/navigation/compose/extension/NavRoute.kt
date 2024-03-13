package dev.sergiobelda.navigation.compose.extension

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

    private fun String.addArgumentsValues(): String {
        val parameters = destination.arguments.filter {
            !it.argument.isDefaultValuePresent && !it.argument.isNullable
        }
        val optionalParameters = destination.arguments.filter {
            it.argument.isDefaultValuePresent || it.argument.isNullable
        }
        val keyArgumentsMap = arguments.mapKeys { it.key.key }

        return this + buildString {
            parameters.forEach { namedNavArgument ->
                if (keyArgumentsMap.containsKey(namedNavArgument.name)) {
                    append(PARAM_SEPARATOR)
                    append(keyArgumentsMap[namedNavArgument.name].toString())
                } else throw Exception("Not present in arguments")
            }
            optionalParameters.takeIf { it.isNotEmpty() }?.let { list ->
                append(
                    list.joinToString(
                        prefix = QUERY_PARAM_PREFIX,
                        separator = QUERY_PARAM_SEPARATOR
                    ) { namedNavArgument ->
                        if (keyArgumentsMap.containsKey(namedNavArgument.name)) {
                            val value = keyArgumentsMap[namedNavArgument.name]
                            if (value != null) {
                                "${namedNavArgument.name}=${keyArgumentsMap[namedNavArgument.name]}"
                            } else ""
                        } else "${namedNavArgument.name}="
                    }
                )
            }
        }
    }
}
