package dev.sergiobelda.navigation.compose.extension.sample.ui.albums

import dev.sergiobelda.navigation.compose.extension.Action
import dev.sergiobelda.navigation.compose.extension.NavRoute
import dev.sergiobelda.navigation.compose.extension.TopLevelNavDestination

object AlbumsNavDestination : TopLevelNavDestination() {
    override val destinationId: String = "albums"
}

object AlbumsNavRoute : NavRoute(AlbumsNavDestination)

val Action.navToAlbums: () -> Unit
    get() = {
        navigate(AlbumsNavRoute)
    }
