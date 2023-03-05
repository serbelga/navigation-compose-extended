package dev.sergiobelda.navigation.compose.extension

/**
 * Represents the navigation route to reach some destination. [Action.navigate] receives a
 * [NavRoute] object.
 *
 * @param destination Navigation destination.
 * @param arguments List of arguments passed in this route.
 *
 * ```kotlin
 *  // Without arguments:
 *  object ArtistsNavRoute : NavRoute(ArtistsNavDestination)
 *  // Navigate:
 *  action.navigate(ArtistsNavRoute)
 * ```
 *
 * ```kotlin
 *  // Defining arguments:
 *  class ArtistDetailsNavRoute(artistId: Int) : NavRoute(ArtistDetailsNavDestination, artistId)
 *  // Navigate:
 *  action.navigate(ArtistDetailsNavRoute(1))
 * ```
 */
abstract class NavRoute(val destination: NavDestination, private vararg val arguments: Any) {

    /**
     * Navigation route. It consists of [destination] id and the [arguments] values.
     */
    internal val route: String =
        destination.destinationId.addArgumentParams()

    private fun String.addArgumentParams(): String =
        this + arguments.joinToString(
            prefix = ARG_SEPARATOR,
            separator = ARG_SEPARATOR
        ) { it.toString() }

    companion object {
        private const val ARG_SEPARATOR = "/"
    }
}
