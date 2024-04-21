# Overview

Navigation Compose Extended is a complementary library for AndroidX Jetpack Navigation Compose to 
improve creation of navigation elements, as destination routes, arguments, deep links, … in a 
more idiomatic way than using literals.

With this library you can define models for your navigation destinations, navigation arguments, ...
and it automatically creates the navigation routes, arguments and deep link variables for the NavHost.

It also provides functions to navigate to navigation destinations using these models and arguments 
keys, and functions to retrieve arguments values in a more secure way.

## Download

[![Maven Central](https://img.shields.io/maven-central/v/dev.sergiobelda.navigation.compose.extended/navigation-compose-extended)](https://search.maven.org/search?q=g:dev.sergiobelda.navigation.compose.extended)

??? note "Android only"

    ```kotlin
    dependencies {
        // Add AndroidX Navigation Compose dependency.
        implementation("androidx.navigation:navigation-compose:$nav_version")
    
        implementation("dev.sergiobelda.navigation.compose.extended:navigation-compose-extended:$version") 
        
        // Optional: Use Annotations to generate NavDestinations.
        implementation("dev.sergiobelda.navigation.compose.extended:navigation-compose-extended-annotation:$version")
        ksp("dev.sergiobelda.navigation.compose.extended:navigation-compose-extended-compiler:$version")
    }
    ```

??? note "Multiplatform"

    ```kotlin
    kotlin {
    
        sourceSets {
            val commonMain by getting {
                dependencies {
                    // Add Jetbrains Navigation Compose Multiplatform dependency.
                    implementation("org.jetbrains.androidx.navigation:navigation-compose:$jetbrains_nav_version")
    
                    implementation("dev.sergiobelda.navigation.compose.extended:navigation-compose-extended:$version")
                    // Optional: Use Annotations to generate NavDestinations.
                    implementation("dev.sergiobelda.navigation.compose.extended:navigation-compose-extended-annotation:$version")
                }
            }
        }
    }
    
    // If use Annotations, add compiler dependency.
    dependencies {
        add("kspCommonMainMetadata", "dev.sergiobelda.navigation.compose.extended:navigation-compose-extended-compiler:$version")
    }
    
    // Workaround for KSP only in Common Main.
    // https://github.com/google/ksp/issues/567
    tasks.withType<org.jetbrains.kotlin.gradle.dsl.KotlinCompile<*>>().all {
        if (name != "kspCommonMainKotlinMetadata") {
            dependsOn("kspCommonMainKotlinMetadata")
        }
    }
    
    kotlin.sourceSets.commonMain {
        kotlin.srcDir("build/generated/ksp/metadata/commonMain/kotlin")
    }
    ```

```kotlin
@NavDestination(
    destinationId = "settings",
    name = "Settings", // Optional: NavDestination name.
)
@Composable
fun SettingsScreen(
    @NavArgument userId: Int,
    @NavArgument(defaultValue = "Default") text: String?, // Set default value for the NavArgument.
    @NavArgument(name = "custom-name", defaultValue = "true") result: Boolean, // Set a custom NavArgument name.
) {
```

```kotlin
val navController = rememberNavController()
val navAction = rememberNavAction(navController)
NavHost(navController = navController, startNavDestination = HomeNavDestination) {
    composable(navDestination = HomeNavDestination) {
        HomeScreen(
            navigateToSettings = { userId ->
                navAction.navigate(
                    SettingsNavDestination.safeNavRoute(userId = userId)
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
