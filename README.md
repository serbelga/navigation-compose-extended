# Navigation Compose Extended

## Usage

### Navigate without arguments

#### Define destinations

```kotlin
object SearchNavDestination : NavDestination<NavArgumentKey>() {
    override val destinationId: String = "search"
}

object SearchResultNavDestination : NavDestination<NavArgumentKey>() {
    override val destinationId: String = "searchresult"
}
```

#### Set destinations in `NavHost`

```kotlin
val navController = rememberNavController()
NavHost(
    navController = navController,
    startDestination = SearchNavDestination.route
) {
    composable(route = SearchNavDestination.route) { SearchScreen() }
    composable(route = SearchResultNavDestination.route) { SearchResultScreen() }
}
```

#### Navigate to Search Result destination using `NavAction`

```kotlin
val navController = rememberNavController()
val navAction = rememberNavAction(navController)
NavHost(
    navController = navController,
    startDestination = SearchNavDestination.route
) {
    composable(route = SearchNavDestination.route) {
        SearchScreen(
            navigateToSearchResult = {
                navAction.navigate(
                    SearchResultNavDestination.navRoute()
                )
            }
        )
    }
```

### Navigate with arguments

#### Define `argumentsMap`

```kotlin
enum class SearchResultNavArgumentKeys(override val argumentKey: String) : NavArgumentKey {
    SearchNavArgumentKey("search"),
    CategoryNavArgumentKey("category")
}

object SearchResultNavDestination : NavDestination<SearchResultNavArgumentKeys>() {
    override val destinationId: String = "searchresult"

    override val argumentsMap: Map<SearchResultNavArgumentKeys, NavArgumentBuilder.() -> Unit> =
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

#### Set arguments in `NavHost`

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

#### Navigate to Search Result destination with arguments

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
