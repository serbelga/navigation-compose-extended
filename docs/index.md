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

```kotlin
dependencies {
    // Add AndroidX Navigation Compose dependency.
    implementation("androidx.navigation:navigation-compose:$NAV_VERSION")

    implementation("dev.sergiobelda.navigation.compose.extended:navigation-compose-extended:$VERSION")
    // Use KSP to generate NavDestinations.
    implementation("dev.sergiobelda.navigation.compose.extended:navigation-compose-extended-compiler:$VERSION")
    ksp("dev.sergiobelda.navigation.compose.extended:navigation-compose-extended-compiler:$VERSION")
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