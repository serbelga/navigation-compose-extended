package dev.sergiobelda.navigation.compose.extension

import androidx.navigation.NamedNavArgument
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavDeepLink

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
     * List of [NamedNavArgument] to associate with destination.
     */
    open val arguments: List<NamedNavArgument> = emptyList()

    /**
     * List of [NavDeepLink] to associate with destination.
     */
    open val deepLinks: List<NavDeepLink> = emptyList()

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
     * Route for the destination. It consists of [destinationId] and [arguments].
     */
    val route get() = destinationId + argumentsRoute

    fun navArgs(navBackStackEntry: NavBackStackEntry, navArgumentKey: K): String =
        navBackStackEntry.arguments?.getString(navArgumentKey.key).orEmpty()
}

/**
 * Represents a TopLevel [NavDestination].
 */
abstract class TopLevelNavDestination<K> : NavDestination<K>() where K : NavArgumentKey
