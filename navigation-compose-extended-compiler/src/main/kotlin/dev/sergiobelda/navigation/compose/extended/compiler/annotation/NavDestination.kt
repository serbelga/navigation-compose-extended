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

package dev.sergiobelda.navigation.compose.extended.compiler.annotation

/**
 * Represents a navigation destination in the Navigation graph. Annotating a function or expression
 * with this annotation will generate a NavDestination object for this function.
 * You must set a [destinationId] that will be used to identify this destination.
 * The function name will be used as the navigation destination unless you set a
 * [name] value. You can indicate that this navigation destination is a top-level destination by
 * setting [isTopLevelNavDestination] to true. [deepLinkUris] is a list of deep links that will be
 * associated with this navigation destination.
 * In addition, an enum class that contains the navigation arguments keys will be generated and
 * a SafeNavArgs class to access to the argument values.
 */
@Retention(AnnotationRetention.BINARY)
@Target(AnnotationTarget.FUNCTION)
annotation class NavDestination(
    val destinationId: String,
    val name: String = "",
    val isTopLevelNavDestination: Boolean = false,
    // TODO: Add deepLinkUris to generator
    val deepLinkUris: Array<String> = [],
)
