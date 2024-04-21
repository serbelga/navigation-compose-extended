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

import com.google.devtools.ksp.symbol.KSAnnotated
import com.google.devtools.ksp.symbol.KSAnnotation
import com.google.devtools.ksp.symbol.KSType
import dev.sergiobelda.navigation.compose.extended.annotation.NavArgument
import dev.sergiobelda.navigation.compose.extended.annotation.NavArgumentType
import dev.sergiobelda.navigation.compose.extended.annotation.NavDestination

/**
 * Map a [KSAnnotated] to the [NavDestination].
 */
internal fun KSAnnotated.toNavDestination(): NavDestination? =
    this.annotations.firstOrNull {
        it.shortName.getShortName() == NavDestination::class.simpleName &&
            it.annotationType.resolve().declaration.qualifiedName?.asString() == NavDestination::class.qualifiedName
    }?.toNavDestination()

private fun KSAnnotation.toNavDestination(): NavDestination =
    with(arguments) {
        NavDestination(
            destinationId = first { it.name?.asString() == NavDestination::destinationId.name }.value as String,
            name = (firstOrNull { it.name?.asString() == NavDestination::name.name }?.value as? String).orEmpty(),
            isTopLevelNavDestination = firstOrNull { it.name?.asString() == NavDestination::isTopLevelNavDestination.name }?.value as? Boolean ?: false,
            arguments = (firstOrNull { it.name?.asString() == NavDestination::arguments.name }?.value as? List<*>)?.mapNotNull {
                (it as? KSAnnotation)?.toNavArguments()
            }?.toTypedArray() ?: emptyArray(),
            deepLinkUris = (firstOrNull { it.name?.asString() == NavDestination::deepLinkUris.name }?.value as? List<*>)?.mapNotNull {
                it as? String
            }?.toTypedArray() ?: emptyArray(),
        )
    }

private fun KSAnnotation.toNavArguments(): NavArgument =
    with(arguments) {
        NavArgument(
            name = first { it.name?.asString() == NavArgument::name.name }.value as String,
            type = (first { it.name?.asString() == NavArgument::type.name }.value as KSType).toNavArgumentType(),
            nullable = firstOrNull { it.name?.asString() == NavArgument::nullable.name }?.value as? Boolean ?: false,
            defaultValue = (firstOrNull { it.name?.asString() == NavArgument::defaultValue.name }?.value as? String).orEmpty(),
        )
    }

private fun KSType.toNavArgumentType(): NavArgumentType =
    when (declaration.simpleName.asString()) {
        NavArgumentType.Boolean.name -> NavArgumentType.Boolean
        NavArgumentType.Float.name -> NavArgumentType.Float
        NavArgumentType.Int.name -> NavArgumentType.Int
        NavArgumentType.Long.name -> NavArgumentType.Long
        NavArgumentType.String.name -> NavArgumentType.String
        else -> throw IllegalArgumentException("Unknown NavArgumentType: $declaration}")
    }
