package dev.sergiobelda.navigation.compose.extension.sample.ui.artistdetails

import androidx.navigation.NamedNavArgument
import androidx.navigation.NavType
import androidx.navigation.navArgument
import dev.sergiobelda.navigation.compose.extension.NavArgumentKey
import dev.sergiobelda.navigation.compose.extension.NavDestination
import dev.sergiobelda.navigation.compose.extension.NavRoute

enum class ArtistDetailsNavArgKeys(override val key: String) : NavArgumentKey {
    ArtistIdNavArgumentKey("artistId"),
    ArtistNameNavArgumentKey("artistName")
}

object ArtistDetailsNavDestination : NavDestination<ArtistDetailsNavArgKeys>() {
    override val destinationId: String = "artistdetails"

    override val arguments: List<NamedNavArgument> = listOf(
        navArgument(ArtistDetailsNavArgKeys.ArtistIdNavArgumentKey.key) {
            type = NavType.StringType
            defaultValue = true
        },
        navArgument(ArtistDetailsNavArgKeys.ArtistNameNavArgumentKey.key) {},
    )
}

class ArtistDetailsNavRoute(artistId: String, artistName: String) :
    NavRoute<ArtistDetailsNavArgKeys>(
        destination = ArtistDetailsNavDestination,
        arguments = mapOf(
            ArtistDetailsNavArgKeys.ArtistIdNavArgumentKey.key to artistId,
            ArtistDetailsNavArgKeys.ArtistNameNavArgumentKey.key to artistName
        )
    )
