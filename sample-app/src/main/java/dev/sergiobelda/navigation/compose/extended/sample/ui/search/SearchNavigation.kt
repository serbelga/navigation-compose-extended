package dev.sergiobelda.navigation.compose.extended.sample.ui.search

import dev.sergiobelda.navigation.compose.extended.NavArgumentKey
import dev.sergiobelda.navigation.compose.extended.TopLevelNavDestination

object SearchNavDestination : TopLevelNavDestination<NavArgumentKey>() {
    override val destinationId: String = "search"
}
