!!! info

    In the next documentation, annotations are used to create navigation elements, but we can also create them programmatically.

The `NavDestination` represents some Destination in the Navigation graph.

## Create a NavDestination

```kotlin
@NavDestination(
    destinationId = "home",
    name = "Home", // Optional: NavDestination name.
    isTopLevelNavDestination = true, // Optional: Mark NavDestination as a top-level destination.
)
@Composable
fun HomeScreen() {}
```

The compiler will generate a `NavDestination` object associated with this destination.

```kotlin
public object HomeNavDestination : NavDestination<HomeNavArgumentKeys>() {
  override val destinationId: String = "home"
}
```

## Using the NavDestination into the NavHost

```kotlin
NavHost(navController = navController, startNavDestination = HomeNavDestination) {
    composable(navDestination = HomeNavDestination) {
        HomeScreen()
    }
    composable(navDestination = SettingsNavDestination) {
        SettingsScreen()
    }
}
```

!!! note

    Here we are using wrappers (`NavHost`, `composable`) that receive the `NavDestination` type to create the navigation graph. Visit the 
    [API Reference](https://sergiobelda.dev/navigation-compose-extended/api/navigation-compose-extended/dev.sergiobelda.navigation.compose.extended/index.html) 
    for more information.

`NavDestination` base class also offers `route`, `arguments` and `deepLinks` getters that can be used as follows if we don't want to use these wrappers:

```kotlin
NavHost(navController = navController, startDestination = HomeNavDestination.route) {
    composable(
        route = HomeNavDestination.route,
        deepLinks = HomeNavDestination.deepLinks
    ) {
        HomeScreen()
    }
    composable(
        route = HomeNavDestination.route,
        arguments = HomeNavDestination.arguments
    ) {
        SettingsScreen()
    }
}
```

## Navigate

We can navigate to some destination using the actions functions provided by the `NavAction` class.
The `NavAction.navigate()` function receive a `NavRoute` instance to navigate to some destination.
This `NavRoute` associated with a destination can be obtained using the `navRoute()` function in the `NavDestination` class or
the `safeNavRoute()` function if we are using annotations.
In the following code, we navigate to the `SettingsNavDestination`:

```kotlin
val navAction = rememberNavAction(navController)
NavHost(navController = navController, startNavDestination = HomeNavDestination) {
    composable(navDestination = HomeNavDestination) {
        HomeScreen(
            navigateToSettings = {
                navAction.navigate(
                    SettingsNavDestination.navRoute()
                )
            },
        )
    }
```

## Navigate with arguments

If we are using annotations, we can use the `@NavArgument` annotation in the function parameters:

```kotlin
@NavDestination(
    name = "Settings",
    destinationId = "settings",
)
@Composable
fun SettingsScreen(
    @NavArgument userId: Int,
    @NavArgument(defaultValue = "Default") text: String?, // Set default value for the NavArgument.
    @NavArgument(name = "custom-name", defaultValue = "true") result: Boolean, // Set a custom NavArgument name.
    viewModel: SettingsViewModel
) {}
```

The compiler will generate an enum class containing the navigation arguments keys for this navigation destination. The `NavArgumentKey` represents the navigation argument's key.

```kotlin
public enum class SettingsNavArgumentKeys(
  override val argumentKey: String,
) : NavArgumentKey {
  UserIdNavArgumentKey("userId"),
  TextNavArgumentKey("text"),
  CustomNameNavArgumentKey("customName"),
  ;
}
```

The compiler also set the `argumentsMap` property in the `NavDestination` that associate each `NavArgumentKey` with its properties.

```kotlin
public object SettingsNavDestination : NavDestination<SettingsNavArgumentKeys>() {
  override val destinationId: String = "settings"

  override val argumentsMap: Map<SettingsNavArgumentKeys, NavArgumentBuilder.() -> Unit> = mapOf(
    SettingsNavArgumentKeys.UserIdNavArgumentKey to {
      type = NavType.IntType
    },
    SettingsNavArgumentKeys.TextNavArgumentKey to {
      type = NavType.StringType
      nullable = true
      defaultValue = "Default"
    },
    SettingsNavArgumentKeys.CustomNameNavArgumentKey to {
      type = NavType.BoolType
      defaultValue = true
    },
  )
```

!!! note

    If we don't use annotations, we should create this enum class and set the `argumentsMap` programmatically.

If we use annotations, we can use the generated `safeNavRoute()` function with the navigation arguments as parameters:

```kotlin
composable(navDestination = HomeNavDestination) {
    HomeScreen(
        navigateToSettings = {
            navAction.navigate(
                SettingsNavDestination.safeNavRoute(
                    userId = 1,
                    text = "Text",
                    customName = true
                )
            )
        },
    )
}
```

otherwise, we must use `navRoute()` function associating the NavArgumentKey to its value.

```kotlin
composable(navDestination = HomeNavDestination) {
    HomeScreen(
        navigateToSettings = {
            navAction.navigate(
                SettingsNavDestination.navRoute(
                    SettingsNavArgumentKeys.UserIdNavArgumentKey to 1,
                    SettingsNavArgumentKeys.TextNavArgumentKey to "Text",
                    SettingsNavArgumentKeys.CustomNameNavArgumentKey to true
                )
            )
        },
    )
}
```

## Retrieving the navigation arguments values

The value of navigation arguments can be obtained using the `NavArgs` class. The `NavDestination.navArgs()` provides an instance
of this class. There are multiple getters to retrieve values:

```kotlin
composable(navDestination = SettingsNavDestination) { navBackStackEntry ->
    val navArgs = SettingsNavDestination.navArgs(navBackStackEntry)
    val userId = navArgs.getInt(SettingsNavArgumentKeys.UserIdNavArgumentKey) ?: 0
    SettingsScreen(
        userId = userId,
```

If we use annotations, a `SafeNavArgs` class is generated with getters for each navigation argument:

```kotlin
composable(navDestination = SettingsNavDestination) { navBackStackEntry ->
    val navArgs = SettingsSafeNavArgs(navBackStackEntry)
    SettingsScreen(
        userId =  navArgs.userId ?: 0,
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
        <data android:scheme="sample" android:host="home" />
    </intent-filter>
</activity>
```

If we are defining the navigation destinations using the `@NavDestination` annotation, we can use the property `deepLinkUris` as follows:

```kotlin
@NavDestination(
    destinationId = "home",
    deepLinkUris = [
        "sample://home",
    ]
)
@Composable
fun HomeScreen(navigateToSettings: () -> Unit) {}
```

otherwise, we should set the list of deepLink uris in the `NavDestination` object:

```kotlin
object HomeNavDestination : NavDestination<HomeNavArgumentKeys>() {
  override val deepLinkUris: List<String> = listOf(
    "sample://home",
    "sample://home_secondary"
  )
```

Trigger the deep link using adb:

```shell
adb shell am start -a android.intent.action.VIEW -d "sample://home"
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
