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
    private val arguments: Map<String, Any?> = emptyMap()
) where K : NavArgumentKey {

    /**
     * Navigation route. It consists of [destination] id and the [arguments] values.
     */
    internal val route: String =
        destination.destinationId.addArgumentParams()

    private fun String.addArgumentParams(): String {
        val parameters = buildList {
            destination.arguments.forEach {
                if (arguments.keys.contains(it.name)) {
                    add(arguments[it.name].toString())
                } else {
                    // TODO
                }
            }
        }
        return this + parameters.joinToString(
            prefix = ARG_SEPARATOR,
            separator = ARG_SEPARATOR
        ) { it }
    }

    companion object {
        private const val ARG_SEPARATOR = "/"
    }
}
