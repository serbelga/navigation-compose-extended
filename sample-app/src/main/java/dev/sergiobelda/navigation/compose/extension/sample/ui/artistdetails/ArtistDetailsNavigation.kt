package dev.sergiobelda.navigation.compose.extension.sample.ui.artistdetails

import androidx.navigation.NavArgumentBuilder
import androidx.navigation.NavDeepLink
import androidx.navigation.NavType
import dev.sergiobelda.navigation.compose.extension.NavArgumentKey
import dev.sergiobelda.navigation.compose.extension.NavDestination
import dev.sergiobelda.navigation.compose.extension.NavRoute

enum class ArtistDetailsNavArgumentKeys(override val argumentKey: String) : NavArgumentKey {
    ArtistIdNavArgumentKey("artistId"),
    ArtistNameNavArgumentKey("artistName")
}

object ArtistDetailsNavDestination : NavDestination<ArtistDetailsNavArgumentKeys>() {
    override val destinationId: String = "artistdetails"

    override val navArguments: Map<ArtistDetailsNavArgumentKeys, NavArgumentBuilder.() -> Unit> =
        mapOf(
            ArtistDetailsNavArgumentKeys.ArtistIdNavArgumentKey to {
                type = NavType.StringType
            },
            ArtistDetailsNavArgumentKeys.ArtistNameNavArgumentKey to {
                type = NavType.StringType
                defaultValue = "artistName"
                nullable = false
            }
        )
}

class ArtistDetailsNavRoute(artistId: String, artistName: String) :
    NavRoute<ArtistDetailsNavArgumentKeys>(
        destination = ArtistDetailsNavDestination,
        arguments = mapOf(
            ArtistDetailsNavArgumentKeys.ArtistIdNavArgumentKey to artistId,
            ArtistDetailsNavArgumentKeys.ArtistNameNavArgumentKey to artistName
        )
    )
