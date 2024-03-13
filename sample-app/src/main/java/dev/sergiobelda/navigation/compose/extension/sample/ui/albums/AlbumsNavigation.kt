package dev.sergiobelda.navigation.compose.extension.sample.ui.albums

import dev.sergiobelda.navigation.compose.extension.NavArgumentKey
import dev.sergiobelda.navigation.compose.extension.NavRoute
import dev.sergiobelda.navigation.compose.extension.TopLevelNavDestination

object AlbumsNavDestination : TopLevelNavDestination<NavArgumentKey>() {
    override val destinationId: String = "albums"
}

object AlbumsNavRoute : NavRoute<NavArgumentKey>(destination = AlbumsNavDestination)
