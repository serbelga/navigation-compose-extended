package dev.sergiobelda.navigation.compose.extension.sample.ui.artistdetails

import androidx.navigation.NamedNavArgument
import androidx.navigation.NavType
import androidx.navigation.navArgument
import dev.sergiobelda.navigation.compose.extension.NavArgumentKey
import dev.sergiobelda.navigation.compose.extension.NavDestination
import dev.sergiobelda.navigation.compose.extension.NavRoute

enum class ArtistDetailsNavArgumentKeys(override val key: String) : NavArgumentKey {
    ArtistIdNavArgumentKey("artistId"),
    ArtistNameNavArgumentKey("artistName")
}

object ArtistDetailsNavDestination : NavDestination<ArtistDetailsNavArgumentKeys>() {
    override val destinationId: String = "artistdetails"

    override val arguments: List<NamedNavArgument> = listOf(
        navArgument(ArtistDetailsNavArgumentKeys.ArtistIdNavArgumentKey.key) {
            type = NavType.StringType
            defaultValue = true
        },
        navArgument(ArtistDetailsNavArgumentKeys.ArtistNameNavArgumentKey.key) {},
    )
}

class ArtistDetailsNavRoute(artistId: String, artistName: String) :
    NavRoute<ArtistDetailsNavArgumentKeys>(
        destination = ArtistDetailsNavDestination,
        arguments = mapOf(
            ArtistDetailsNavArgumentKeys.ArtistIdNavArgumentKey.key to artistId,
            ArtistDetailsNavArgumentKeys.ArtistNameNavArgumentKey.key to artistName
        )
    )
