# Navigation Compose extension

## Usage

### Without arguments

```kotlin
object SearchNavDestination : NavDestination<NavArgumentKey>() {
    override val destinationId: String = "search"
}

NavHost(startDestination = SearchNavDestination.route) {
    composable(route = SearchNavDestination.route) { ... }
}

// Navigate to Search destination
navAction.navigate(SearchResultNavDestination.navRoute())
```

### Defining arguments

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

NavHost {
    composable(
        route = SearchResultNavDestination.route,
        arguments = SearchResultNavDestination.arguments
    ) { ... }
}

// Navigate to Search Result destination
navAction.navigate(
    SearchResultNavDestination.navRoute(
        SearchResultNavArgumentKeys.SearchNavArgumentKey to search
    )
)
```
