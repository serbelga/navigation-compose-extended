package dev.sergiobelda.navigation.compose.extension.sample.ui.searchresult

import androidx.navigation.NavArgumentBuilder
import androidx.navigation.NavType
import dev.sergiobelda.navigation.compose.extension.NavArgumentKey
import dev.sergiobelda.navigation.compose.extension.NavDestination
import dev.sergiobelda.navigation.compose.extension.NavRoute

enum class SearchResultNavArgumentKeys(override val argumentKey: String) : NavArgumentKey {
    SearchNavArgumentKey("search"),
    CategoryNavArgumentKey("category")
}

object SearchResultNavDestination : NavDestination<SearchResultNavArgumentKeys>() {
    override val destinationId: String = "searchresult"

    override val navArguments: Map<SearchResultNavArgumentKeys, NavArgumentBuilder.() -> Unit> =
        mapOf(
            SearchResultNavArgumentKeys.SearchNavArgumentKey to {
                type = NavType.StringType
            },
            SearchResultNavArgumentKeys.CategoryNavArgumentKey to {
                type = NavType.StringType
                nullable = true
                defaultValue = "All"
            }
        )
}

class SearchResultNavRoute(search: String, category: String? = null) :
    NavRoute<SearchResultNavArgumentKeys>(
        destination = SearchResultNavDestination,
        arguments = mapOf(
            SearchResultNavArgumentKeys.SearchNavArgumentKey to search,
            SearchResultNavArgumentKeys.CategoryNavArgumentKey to category
        )
    )
