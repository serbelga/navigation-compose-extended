## Create Destinations

The `NavDestination` represents some Destination in the Navigation graph.

```kotlin
object SearchNavDestination : NavDestination<NavArgumentKey>() {
    override val destinationId: String = "search"
}

object SearchResultNavDestination : NavDestination<NavArgumentKey>() {
    override val destinationId: String = "searchresult"
}
```

Using the `NavDestination` into the NavHost:

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

We can navigate to some destination using the actions functions provided by the `NavAction` class. 
The `NavAction.navigate` function receive a `NavRoute` instance to navigate to some destination.
This `NavRoute` associated with a destination can be obtained using the `navRoute()` function in the `NavDestination` class.
In the following code, we navigate to the `SearchResultNavDestination`:

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
}
```

## Navigate with arguments

The `NavArgumentKey` represents some argument in the Navigation graph. 
We can define multiple keys for our destinations.

```kotlin
enum class SearchResultNavArgumentKeys(override val argumentKey: String) : NavArgumentKey {
    SearchNavArgumentKey("search"),
    CategoryNavArgumentKey("category")
}
```

Next, we set the `NavArgumentKey` into the `NavDestination` using the `argumentsMap` property.

```kotlin
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

The `NavRoute` class will generate automatically the route with the arguments depending on if they 
are nullable or have a default value.

Arguments can be set to the `NavHost` using the `arguments` property:

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

The `navRoute()` function in the `NavDestination` class can receive the arguments as parameters. 
In the following code, we are navigating to `SearchResultNavDestination` and we set a value for 
the `SearchNavArgumentKey` argument.

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

## Navigate with Deep Links

In the `AndroidManifest.xml`:

```xml
<activity
    ...>
    <intent-filter>
        <action android:name="android.intent.action.VIEW" />
        <category android:name="android.intent.category.DEFAULT" />
        <category android:name="android.intent.category.BROWSABLE" />
        <data android:host="searchresult" android:scheme="sample" />
    </intent-filter>
</activity>
```

Next, we define the deep link uri in the `NavDestination` `deepLinkUris` property:

```kotlin
object SearchResultNavDestination : NavDestination<SearchResultNavArgumentKeys>() {
    
    // ...
    
    override val deepLinkUris: List<String> =
        listOf(
            "sample://searchresult",
        )
}
```

Set the deep links in the `NavHost`:

```kotlin
NavHost {
    composable(
        route = SearchResultNavDestination.route,
        deepLinks = SearchResultNavDestination.deepLinks,
    ) {
        ...
    }
}
```

Trigger the deep link using adb:

```shell
adb shell am start -a android.intent.action.VIEW -d "sample://searchresult"
```

## Create Top Level Destinations

There's a special kind of destination to represent the top level destinations in the navigation graph.
This is useful for destinations associated to items in a BottomNavigationBar where we can navigate to 
different screens by clicking on each navigation bar item. To avoid building up a large stack of destinations
on the back stack when we click on navigation items and to avoid multiple copies of the same destination 
when we reselect the same item, we can use this kind of destination.

```kotlin
object SearchNavDestination : TopLevelNavDestination<NavArgumentKey>() {
    override val destinationId: String = "search"
}
```
