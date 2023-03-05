package dev.sergiobelda.navigation.compose.extension.sample.ui.artistdetails

import androidx.navigation.NamedNavArgument
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavType
import androidx.navigation.navArgument
import dev.sergiobelda.navigation.compose.extension.Action
import dev.sergiobelda.navigation.compose.extension.NavDestination
import dev.sergiobelda.navigation.compose.extension.NavRoute

object ArtistDetailsNavDestination : NavDestination() {
    override val destinationId: String = "artistdetails"

    private const val ArtistIdArgKey: String = "artistId"

    override val arguments: List<NamedNavArgument> = listOf(
        navArgument(ArtistIdArgKey) { type = NavType.StringType }
    )

    fun navArgArtistId(navBackStackEntry: NavBackStackEntry): String =
        navBackStackEntry.arguments?.getString(ArtistIdArgKey) ?: ""
}

class ArtistDetailsNavRoute(artistId: String) : NavRoute(ArtistDetailsNavDestination, artistId)

val Action.navToArtistDetails: (artistId: String) -> Unit
    get() = { artistId ->
        navigate(ArtistDetailsNavRoute(artistId))
    }
