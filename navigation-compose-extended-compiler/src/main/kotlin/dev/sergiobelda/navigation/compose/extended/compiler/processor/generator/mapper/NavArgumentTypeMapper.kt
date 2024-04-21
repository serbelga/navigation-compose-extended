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

package dev.sergiobelda.navigation.compose.extended.compiler.processor.generator.mapper

import com.squareup.kotlinpoet.MemberName
import com.squareup.kotlinpoet.TypeName
import com.squareup.kotlinpoet.asTypeName
import dev.sergiobelda.navigation.compose.extended.annotation.NavArgumentType
import dev.sergiobelda.navigation.compose.extended.compiler.processor.generator.names.MemberNames

/**
 * Converts a [NavArgumentType] to a NavType definition.
 */
internal fun NavArgumentType.toNavType(): String =
    when (this) {
        NavArgumentType.Boolean -> "BoolType"
        NavArgumentType.Float -> "FloatType"
        NavArgumentType.Int -> "IntType"
        NavArgumentType.Long -> "LongType"
        NavArgumentType.String -> "StringType"
    }

/**
 * Converts a [NavArgumentType] to a NavArgs getter function name.
 */
internal fun NavArgumentType.toNavArgsGetter(): MemberName =
    when (this) {
        NavArgumentType.Boolean -> MemberNames.NavArgsGetBoolean
        NavArgumentType.Float -> MemberNames.NavArgsGetFloat
        NavArgumentType.Int -> MemberNames.NavArgsGetInt
        NavArgumentType.Long -> MemberNames.NavArgsGetLong
        NavArgumentType.String -> MemberNames.NavArgsGetString
    }

/**
 * Converts a [NavArgumentType] to a [TypeName].
 */
internal fun NavArgumentType.asTypeName(): TypeName =
    when (this) {
        NavArgumentType.Boolean -> Boolean::class.asTypeName()
        NavArgumentType.Float -> Float::class.asTypeName()
        NavArgumentType.Int -> Int::class.asTypeName()
        NavArgumentType.Long -> Long::class.asTypeName()
        NavArgumentType.String -> String::class.asTypeName()
    }

/**
 * Converts a [String] to a value based on a given [NavArgumentType].
 */
internal fun String.toValue(
    navArgumentType: NavArgumentType,
): Any = when (navArgumentType) {
    NavArgumentType.Boolean -> this.trim().toBoolean()
    NavArgumentType.Float -> this.trim().toFloat()
    NavArgumentType.Int -> this.trim().toInt()
    NavArgumentType.Long -> this.trim().toLong()
    NavArgumentType.String -> "\"$this\""
}
