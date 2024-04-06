/*
 * Copyright 2024 Sergio Belda
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package dev.sergiobelda.navigation.compose.extended.compiler.processor

import com.squareup.kotlinpoet.ClassName
import dev.sergiobelda.navigation.compose.extended.compiler.processor.Constants.AndroidXNavigationPackageName
import dev.sergiobelda.navigation.compose.extended.compiler.processor.Constants.NavArgumentBuilderSimpleClassName
import dev.sergiobelda.navigation.compose.extended.compiler.processor.Constants.NavArgumentKeySimpleClassName
import dev.sergiobelda.navigation.compose.extended.compiler.processor.Constants.NavDestinationSimpleClassName
import dev.sergiobelda.navigation.compose.extended.compiler.processor.Constants.NavigationComposeExtendedPackageName
import dev.sergiobelda.navigation.compose.extended.compiler.processor.Constants.TopLevelNavDestinationSimpleClassName

internal object ClassNames {
    val NavArgumentKey =
        ClassName(NavigationComposeExtendedPackageName, NavArgumentKeySimpleClassName)
    val NavArgumentBuilder =
        ClassName(AndroidXNavigationPackageName, NavArgumentBuilderSimpleClassName)

    val NavDestination =
        ClassName(NavigationComposeExtendedPackageName, NavDestinationSimpleClassName)
    val TopLevelNavDestination =
        ClassName(NavigationComposeExtendedPackageName, TopLevelNavDestinationSimpleClassName)
}
