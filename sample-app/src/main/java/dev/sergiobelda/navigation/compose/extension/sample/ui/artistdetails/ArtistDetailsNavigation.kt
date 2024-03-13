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
        },
        navArgument(ArtistDetailsNavArgumentKeys.ArtistNameNavArgumentKey.key) {
            type = NavType.StringType
            defaultValue = "artistName"
            nullable = false
        },
    )
}

class ArtistDetailsNavRoute(artistId: String) :
    NavRoute<ArtistDetailsNavArgumentKeys>(
        destination = ArtistDetailsNavDestination,
        arguments = mapOf(
            ArtistDetailsNavArgumentKeys.ArtistIdNavArgumentKey to artistId,
            // ArtistDetailsNavArgumentKeys.ArtistNameNavArgumentKey to artistName
        )
    )
