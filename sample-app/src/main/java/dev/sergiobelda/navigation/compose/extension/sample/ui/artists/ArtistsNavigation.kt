package dev.sergiobelda.navigation.compose.extension.sample.ui.artists

import dev.sergiobelda.navigation.compose.extension.NavArgumentKey
import dev.sergiobelda.navigation.compose.extension.TopLevelNavDestination

object ArtistsNavDestination : TopLevelNavDestination<NavArgumentKey>() {
    override val destinationId: String = "artists"
}
