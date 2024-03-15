# Navigation Compose Extended

## Usage

### Navigate without arguments

#### Define destinations

```kotlin
object SearchNavDestination : NavDestination<NavArgumentKey>() {
    override val destinationId: String = "search"
}

object SearchResultNavDestination : NavDestination<SearchResultNavArgumentKeys>() {
    override val destinationId: String = "searchresult"
}
```

#### NavHost

```kotlin
NavHost(
    navController = navController,
    startDestination = SearchNavDestination.route
) {
    composable(route = SearchNavDestination.route) { SearchScreen() }
    composable(route = SearchResultNavDestination.route) { SearchResultScreen() }
}
```

#### Navigate

```kotlin
// Navigate to SearchResult destination
SearchScreen(
    navigateToSearchResult = { search, category ->
        navAction.navigate(
            SearchResultNavDestination.navRoute()
        )
    }
)
```

### Navigate with arguments

#### Define navArguments

```kotlin
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
```

#### Set arguments in NavHost

```kotlin
NavHost {
    composable(
        route = SearchResultNavDestination.route,
        arguments = SearchResultNavDestination.arguments
    ) { 
        ...
    }
}
```

#### Navigate to Search Result destination

```kotlin
SearchScreen(
    navigateToSearchResult = { search, category ->
        navAction.navigate(
            SearchResultNavDestination.navRoute(
                SearchResultNavArgumentKeys.SearchNavArgumentKey to search,
            )
        )
    }
)
```
