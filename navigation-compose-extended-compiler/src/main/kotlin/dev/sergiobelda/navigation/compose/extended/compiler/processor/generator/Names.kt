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

package dev.sergiobelda.navigation.compose.extended.compiler.processor.generator

import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.MemberName

/**
 * [ClassName]s used for navigation destination generation.
 */
internal object ClassNames {
    val NavArgs =
        ClassName(NAVIGATION_COMPOSE_EXTENDED_PACKAGE_NAME, "NavArgs")
    val NavArgumentKey =
        ClassName(NAVIGATION_COMPOSE_EXTENDED_PACKAGE_NAME, "NavArgumentKey")
    val NavArgumentBuilder =
        ClassName(ANDROIDX_NAVIGATION_PACKAGE_NAME, "NavArgumentBuilder")
    val NavBackStackEntry =
        ClassName(ANDROIDX_NAVIGATION_PACKAGE_NAME, "NavBackStackEntry")
    val NavDestination =
        ClassName(NAVIGATION_COMPOSE_EXTENDED_PACKAGE_NAME, "NavDestination")
    val NavRoute =
        ClassName(NAVIGATION_COMPOSE_EXTENDED_PACKAGE_NAME, "NavRoute")
    val NavType =
        ClassName(ANDROIDX_NAVIGATION_PACKAGE_NAME, "NavType")
    val TopLevelNavDestination =
        ClassName(NAVIGATION_COMPOSE_EXTENDED_PACKAGE_NAME, "TopLevelNavDestination")
}

/**
 * [MemberName]s used for navigation destination generation.
 */
internal object MemberNames {
    val MapOf = MemberName(KOTLIN_COLLECTIONS, "mapOf")
    val NavArgsGetBoolean = MemberName("", "getBoolean")
    val NavArgsGetFloat = MemberName("", "getFloat")
    val NavArgsGetInt = MemberName("", "getInt")
    val NavArgsGetLong = MemberName("", "getLong")
    val NavArgsGetString = MemberName("", "getString")
    val NavRoute = MemberName("", "navRoute")
}

private const val ANDROIDX_NAVIGATION_PACKAGE_NAME = "androidx.navigation"
private const val KOTLIN_COLLECTIONS = "kotlin.collections"
private const val NAVIGATION_COMPOSE_EXTENDED_PACKAGE_NAME =
    "dev.sergiobelda.navigation.compose.extended"
