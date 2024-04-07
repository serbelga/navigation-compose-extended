# Navigation Compose Extended

[![Maven Central](https://img.shields.io/maven-central/v/dev.sergiobelda.navigation.compose.extended/navigation-compose-extended)](https://search.maven.org/search?q=g:dev.sergiobelda.navigation.compose.extended)

Navigation Compose Extended is a complementary library for AndroidX Jetpack Navigation Compose to
improve creation of navigation elements, as destination routes, arguments, deep links, … in a
more idiomatic way than using literals.

Visit the [project website](https://sergiobelda.dev/navigation-compose-extended/) for documentation 
and API Reference.

## Download

```kotlin
dependencies {
    // Add AndroidX Navigation Compose dependency.
    implementation("androidx.navigation:navigation-compose:$nav_version")

    implementation("dev.sergiobelda.navigation.compose.extended:navigation-compose-extended:$version")
    // Use KSP to generate NavDestinations with annotations.
    implementation("dev.sergiobelda.navigation.compose.extended:navigation-compose-extended-compiler:$version")
    ksp("dev.sergiobelda.navigation.compose.extended:navigation-compose-extended-compiler:$version")
}
```

## Create NavDestinations using Annotations

```kotlin
@NavDestination(
    destinationId = "settings",
    name = "Settings", // Optional: NavDestination name.
    isTopLevelNavDestination = true, // Optional: Mark NavDestination as a top-level destination. 
)
@Composable
fun SettingsScreen(
    @NavArgument userId: Int,
    @NavArgument(defaultValue = "Default") text: String?, // Set default value for the NavArgument.
    @NavArgument(name = "custom-name", defaultValue = "true") result: Boolean, // Set a custom NavArgument name.
    viewModel: SettingsViewModel
) {
    Box(modifier = Modifier.fillMaxSize()) {
        Column {
            Text(text = "Settings Screen")
            Text(text = "User ID: $userId")
            Text(text = "Text: $text")
            Text(text = "Result: $result")
        }
    }
}
```

<details>
  <summary>The compiler will generate following classes</summary>

### NavArgumentKey enum class

Contains the NavArgument keys to create the navigation arguments and retrieve values.

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

### NavDestination object

Contains the destinationId associated with this navigation destination, a Map<NavArgumentKey, NavArgumentBuilder> that
specifies navigation arguments properties and a `safeNavRoute` function with the navigation arguments as parameters that returns 
a `NavRoute` instance that can be used to navigate to this destination.

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

    public fun safeNavRoute(
        userId: Int,
        text: String? = "Default",
        customName: Boolean = true,
    ): NavRoute<SettingsNavArgumentKeys> = navRoute(
        SettingsNavArgumentKeys.UserIdNavArgumentKey to userId,
        SettingsNavArgumentKeys.TextNavArgumentKey to text,
        SettingsNavArgumentKeys.CustomNameNavArgumentKey to customName,
    )
}
```

### SafeNavArgs class

A class definition that contains getters to retrieve navigation arguments values.

```kotlin
public class SettingsSafeNavArgs(
  navBackStackEntry: NavBackStackEntry,
) {
  private val navArgs: NavArgs<SettingsNavArgumentKeys> by lazy {
    SettingsNavDestination.navArgs(navBackStackEntry)
  }
    
  public val userId: Int?
    get() = navArgs.getInt(SettingsNavArgumentKeys.UserIdNavArgumentKey)

  public val text: String?
    get() = navArgs.getString(SettingsNavArgumentKeys.TextNavArgumentKey)

  public val customName: Boolean?
    get() = navArgs.getBoolean(SettingsNavArgumentKeys.CustomNameNavArgumentKey)
}
```
</details>

### Generate the Navigation Graph

```kotlin
NavHost(navController = navController, startNavDestination = HomeNavDestination) {
    composable(navDestination = HomeNavDestination) {
        HomeScreen(
            navigateToSettings = {
                navAction.navigate(
                    SettingsNavDestination.safeNavRoute(
                        userId = 1,
                    ),
                )
            },
        )
    }
    composable(navDestination = SettingsNavDestination) { navBackStackEntry ->
        val safeNavArgs = SettingsSafeNavArgs(navBackStackEntry)
        SettingsScreen(
            userId = safeNavArgs.userId ?: 0,
            text = safeNavArgs.text,
            result = safeNavArgs.customName ?: false,
        )
    }
}
```

> [!NOTE]  
> There's also a couple of wrappers that receive the `NavDestination` types to create the navigation graph: `NavHost`, `composable`, `dialog`, ...

## Create NavDestinations programmatically

## License

```
   Copyright 2024 Sergio Belda

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
```
