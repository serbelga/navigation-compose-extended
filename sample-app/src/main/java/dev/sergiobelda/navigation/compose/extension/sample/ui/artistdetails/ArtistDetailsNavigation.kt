package dev.sergiobelda.navigation.compose.extension.sample.ui.artistdetails

import androidx.navigation.NamedNavArgument
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavType
import androidx.navigation.navArgument
import dev.sergiobelda.navigation.compose.extension.NavDestination
import dev.sergiobelda.navigation.compose.extension.NavRoute

object ArtistDetailsNavDestination : NavDestination() {
    override val destinationId: String = "artistdetails"

    override val arguments: List<NamedNavArgument> = listOf(
        navArgument(ArtistIdArgKey) {
            type = NavType.StringType
        },
        navArgument(ArtistNameArgKey) {},
    )

    fun navArgArtistId(navBackStackEntry: NavBackStackEntry): String =
        navBackStackEntry.arguments?.getString(ArtistIdArgKey).orEmpty()

    fun navArgArtistName(navBackStackEntry: NavBackStackEntry): String =
        navBackStackEntry.arguments?.getString(ArtistNameArgKey).orEmpty()
}

class ArtistDetailsNavRoute(artistId: String, artistName: String) :
    NavRoute(
        destination = ArtistDetailsNavDestination,
        arguments = mapOf(
            ArtistIdArgKey to artistId,
            ArtistNameArgKey to artistName
        )
    )

private const val ArtistIdArgKey: String = "artistId"
private const val ArtistNameArgKey: String = "artistName"
