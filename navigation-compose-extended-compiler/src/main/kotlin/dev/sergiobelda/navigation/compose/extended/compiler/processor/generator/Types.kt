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

import com.google.devtools.ksp.symbol.KSType
import com.squareup.kotlinpoet.MemberName

/**
 * Kotlin Primitive types.
 */
private object KotlinTypeName {
    const val BOOLEAN = "kotlin.Boolean"
    const val FLOAT = "kotlin.Float"
    const val INT = "kotlin.Int"
    const val LONG = "kotlin.Long"
    const val STRING = "kotlin.String"
}

/**
 * NavArgument valid types.
 */
enum class NavArgumentType {
    BOOLEAN,
    FLOAT,
    INT,
    LONG,
    STRING,
}

/**
 * Convert a [KSType] to a [NavArgumentType].
 *
 * @throws RuntimeException if the type is invalid.
 */
internal fun KSType.toNavArgumentType(): NavArgumentType =
    when (this.declaration.qualifiedName?.asString()) {
        KotlinTypeName.BOOLEAN -> NavArgumentType.BOOLEAN
        KotlinTypeName.FLOAT -> NavArgumentType.FLOAT
        KotlinTypeName.INT -> NavArgumentType.INT
        KotlinTypeName.LONG -> NavArgumentType.LONG
        KotlinTypeName.STRING -> NavArgumentType.STRING
        else -> throw RuntimeException("Invalid type, $this cannot be used a NavArgument type.")
    }

/**
 * Converts a [KSType] to a NavType definition.
 */
internal fun KSType.mapToNavType(): String {
    val navArgumentType = toNavArgumentType()
    return when (navArgumentType) {
        NavArgumentType.BOOLEAN -> "BoolType"
        NavArgumentType.FLOAT -> "FloatType"
        NavArgumentType.INT -> "IntType"
        NavArgumentType.LONG -> "LongType"
        NavArgumentType.STRING -> "StringType"
    }
}

/**
 * Converts a [KSType] to a NavArgs getter function name.
 */
internal fun KSType.mapToNavArgsGetter(): MemberName {
    val navArgumentType = toNavArgumentType()
    return when (navArgumentType) {
        NavArgumentType.BOOLEAN -> MemberNames.NavArgsGetBoolean
        NavArgumentType.FLOAT -> MemberNames.NavArgsGetFloat
        NavArgumentType.INT -> MemberNames.NavArgsGetInt
        NavArgumentType.LONG -> MemberNames.NavArgsGetLong
        NavArgumentType.STRING -> MemberNames.NavArgsGetString
    }
}

/**
 * Converts a [String] to a value based on a given [KSType].
 */
internal fun String.toValue(type: KSType): Any {
    val navArgumentType = type.toNavArgumentType()
    return when (navArgumentType) {
        NavArgumentType.BOOLEAN -> this.trim().toBoolean()
        NavArgumentType.FLOAT -> this.trim().toFloat()
        NavArgumentType.INT -> this.trim().toInt()
        NavArgumentType.LONG -> this.trim().toLong()
        NavArgumentType.STRING -> "\"$this\""
    }
}
