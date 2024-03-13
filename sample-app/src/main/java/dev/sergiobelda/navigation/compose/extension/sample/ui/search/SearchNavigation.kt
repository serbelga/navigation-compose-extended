package dev.sergiobelda.navigation.compose.extension.sample.ui.search

import dev.sergiobelda.navigation.compose.extension.NavArgumentKey
import dev.sergiobelda.navigation.compose.extension.NavDestination

object SearchNavDestination : NavDestination<NavArgumentKey>() {
    override val destinationId: String = "search"
}
