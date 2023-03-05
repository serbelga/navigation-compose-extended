package dev.sergiobelda.navigation.compose.extension

import androidx.navigation.NamedNavArgument

/**
 * Represents some Destination in the Navigation graph. It's defined by a
 * [destinationId] and a list of [arguments].
 */
abstract class NavDestination {
    /**
     * Identifier of Destination.
     */
    abstract val destinationId: String

    /**
     * List of [NamedNavArgument] to associate with destination.
     */
    open val arguments: List<NamedNavArgument> = emptyList()

    private val argumentsRoute: String
        get() = if (arguments.isNotEmpty()) {
            arguments.joinToString(
                separator = ARG_SEPARATOR
            ) {
                "/{${it.name}}"
            }
        } else {
            ""
        }

    /**
     * Route for the destination. It consists of [destinationId] and [arguments].
     */
    val route get() = destinationId + argumentsRoute

    companion object {
        private const val ARG_SEPARATOR = ""
    }
}

/**
 * Represents a TopLevel [NavDestination].
 */
abstract class TopLevelNavDestination : NavDestination()
