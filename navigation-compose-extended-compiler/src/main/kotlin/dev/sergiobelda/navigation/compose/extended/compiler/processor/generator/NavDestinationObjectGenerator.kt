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

import com.google.devtools.ksp.symbol.KSValueParameter
import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.KModifier
import com.squareup.kotlinpoet.LambdaTypeName
import com.squareup.kotlinpoet.ParameterizedTypeName.Companion.parameterizedBy
import com.squareup.kotlinpoet.PropertySpec
import com.squareup.kotlinpoet.TypeSpec
import com.squareup.kotlinpoet.asClassName

/**
 * TODO Add documentation
 */
internal class NavDestinationObjectGenerator(
    val packageName: String,
    val name: String,
    val navArgumentKeysName: String,
    val isTopLevelNavDestination: Boolean,
    val destinationId: String,
    val navArgumentParameters: List<KSValueParameter>,
) {
    private val navArgumentKeysClassName = ClassName(packageName, navArgumentKeysName)

    fun generate(): TypeSpec {
        val superClass = if (isTopLevelNavDestination) {
            ClassNames.TopLevelNavDestination
        } else {
            ClassNames.NavDestination
        }

        return TypeSpec.objectBuilder(name)
            .superclass(
                superClass.parameterizedBy(
                    navArgumentKeysClassName,
                ),
            )
            .addProperty(
                // TODO Add destinationId as const
                PropertySpec.builder("destinationId", String::class, KModifier.OVERRIDE)
                    .initializer("%S", destinationId)
                    .build(),
            )
            .addProperty(
                argumentsMapProperty(),
            )
            .build()
    }

    private fun argumentsMapProperty(): PropertySpec =
        PropertySpec.Companion.builder(
            "argumentsMap",
            Map::class.asClassName()
                .parameterizedBy(
                    navArgumentKeysClassName,
                    LambdaTypeName.get(
                        receiver = ClassNames.NavArgumentBuilder,
                        returnType = Unit::class.asClassName(),
                    ),
                ),
        ).addModifiers(KModifier.OVERRIDE)
            .initializer("mapOf()")
            .build()
}
