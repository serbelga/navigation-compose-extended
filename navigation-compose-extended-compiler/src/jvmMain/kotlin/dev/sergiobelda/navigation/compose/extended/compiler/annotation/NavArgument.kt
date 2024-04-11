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
 * Represents a navigation argument in a navigation destination. Annotating a parameter with this
 * annotation will generate a `NavArgumentKey` entry in the navigation destination and will register
 * this argument in the navigation destination's arguments list.
 * The parameter name will be used as the argument key unless you set a [name] value. You can
 * also set a [defaultValue] for this argument.
 */
@Retention(AnnotationRetention.BINARY)
@Target(AnnotationTarget.VALUE_PARAMETER)
annotation class NavArgument(
    val name: String = "",
    val defaultValue: String = "",
)
