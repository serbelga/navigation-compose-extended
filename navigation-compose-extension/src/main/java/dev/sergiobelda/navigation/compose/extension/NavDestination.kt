package dev.sergiobelda.navigation.compose.extension

import androidx.navigation.NamedNavArgument
import androidx.navigation.NavArgumentBuilder
import androidx.navigation.NavDeepLink
import androidx.navigation.navArgument

/**
 * Represents some Destination in the Navigation graph. It's defined by a
 * [destinationId] and a list of [arguments].
 */
abstract class NavDestination<K> where K : NavArgumentKey {
    /**
     * Identifier of Destination.
     */
    abstract val destinationId: String

    /**
     * Define the navigation arguments to this destination.
     */
    open val navArguments: Map<K, NavArgumentBuilder.() -> Unit> = emptyMap()

    /**
     * List of [NamedNavArgument] associated with destination.
     */
    val arguments: List<NamedNavArgument>
        get() = navArguments.map {
            navArgument(it.key.argumentKey, it.value)
        }

    /**
     * List of [NavDeepLink] associated with destination.
     */
    open val navDeepLinks: List<NavDeepLink> = emptyList()

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
                separator = PARAM_SEPARATOR
            ) {
                "{${it.name}}"
            } + optionalParameters.takeIf { it.isNotEmpty() }?.joinToString(
                prefix = QUERY_PARAM_PREFIX,
                separator = QUERY_PARAM_SEPARATOR
            ) { "${it.name}={${it.name}}" }.orEmpty()
        }

    /**
     * Route to this destination. It consists of [destinationId] and [arguments].
     */
    val route get() = destinationId + argumentsRoute
}

/**
 * Represents a TopLevel [NavDestination].
 */
abstract class TopLevelNavDestination<K> : NavDestination<K>() where K : NavArgumentKey
