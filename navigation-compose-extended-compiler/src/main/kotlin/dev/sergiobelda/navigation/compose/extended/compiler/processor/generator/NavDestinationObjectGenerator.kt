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
import com.squareup.kotlinpoet.CodeBlock
import com.squareup.kotlinpoet.FunSpec
import com.squareup.kotlinpoet.KModifier
import com.squareup.kotlinpoet.LambdaTypeName
import com.squareup.kotlinpoet.ParameterizedTypeName.Companion.parameterizedBy
import com.squareup.kotlinpoet.PropertySpec
import com.squareup.kotlinpoet.TypeSpec
import com.squareup.kotlinpoet.asClassName
import com.squareup.kotlinpoet.buildCodeBlock
import com.squareup.kotlinpoet.ksp.toTypeName

/**
 * NavDestination object generator.
 */
internal class NavDestinationObjectGenerator(
    private val name: String,
    private val isTopLevelNavDestination: Boolean,
    private val destinationId: String,
    private val navArgumentKeysClass: ClassName,
    private val navArgumentParameters: List<NavArgumentParameter>,
) {
    fun generate(): TypeSpec {
        val superClass = if (isTopLevelNavDestination) {
            ClassNames.TopLevelNavDestination
        } else {
            ClassNames.NavDestination
        }

        return TypeSpec.objectBuilder(name)
            .superclass(
                superClass.parameterizedBy(
                    navArgumentKeysClass,
                ),
            )
            .addProperty(
                PropertySpec.builder(
                    DESTINATION_ID_PROPERTY_NAME,
                    String::class,
                    KModifier.OVERRIDE,
                )
                    .initializer("%S", destinationId)
                    .build(),
            )
            .addProperty(
                argumentsMapProperty(),
            )
            .addSafeNavRoute()
            .build()
    }

    private fun argumentsMapProperty(): PropertySpec =
        PropertySpec.builder(
            ARGUMENTS_MAP_PROPERTY_NAME,
            Map::class.asClassName()
                .parameterizedBy(
                    navArgumentKeysClass,
                    LambdaTypeName.get(
                        receiver = ClassNames.NavArgumentBuilder,
                        returnType = Unit::class.asClassName(),
                    ),
                ),
        ).addModifiers(KModifier.OVERRIDE)
            .initializer(
                buildCodeBlock {
                    addStatement("%M(", MemberNames.MapOf)
                    indent()
                    addNavArgumentsToMap()
                    unindent()
                    addStatement(")")
                },
            )
            .build()

    private fun CodeBlock.Builder.addNavArgumentsToMap() {
        navArgumentParameters.forEach { navArgumentParameter ->
            addStatement(
                "%T.%L to {",
                navArgumentKeysClass,
                navArgumentParameter.name.formatNavArgumentKey(),
            )
            addNavArgumentBuilderProperties(navArgumentParameter)
            addStatement("},")
        }
    }

    private fun CodeBlock.Builder.addNavArgumentBuilderProperties(
        navArgumentParameter: NavArgumentParameter,
    ) {
        indent()
        val type = navArgumentParameter.parameter.type.resolve()
        addStatement("type = %T.${type.mapToNavType()}", ClassNames.NavType)
        if (type.isMarkedNullable) {
            addStatement("nullable = true")
        }
        if (navArgumentParameter.defaultValue.isNotBlank()) {
            val defaultValue = navArgumentParameter.defaultValue.toValue(type)
            addStatement("defaultValue = %L", defaultValue)
        }
        unindent()
    }

    private fun TypeSpec.Builder.addSafeNavRoute() =
        apply {
            if (navArgumentParameters.isNotEmpty()) {
                addFunction(
                    FunSpec.builder("navRoute")
                        .addNavArgumentsToNavRouteFunctionParameters()
                        .returns(
                            ClassNames.NavRoute.parameterizedBy(
                                navArgumentKeysClass,
                            ),
                        )
                        .addCode(
                            buildCodeBlock {
                                add("return %M(\n", MemberNames.NavRoute)
                                indent()
                                addNavArgumentsToNavRouteFunctionBody()
                                unindent()
                                add(")")
                            },
                        )
                        .build(),
                )
            }
        }

    private fun FunSpec.Builder.addNavArgumentsToNavRouteFunctionParameters() =
        apply {
            navArgumentParameters.forEach { navArgumentParameter ->
                addParameter(
                    navArgumentParameter.name,
                    navArgumentParameter.parameter.type.resolve().toTypeName(),
                )
            }
        }

    private fun CodeBlock.Builder.addNavArgumentsToNavRouteFunctionBody() =
        navArgumentParameters.forEach { navArgumentParameter ->
            addStatement(
                "%T.%L to %L,",
                navArgumentKeysClass,
                navArgumentParameter.name.formatNavArgumentKey(),
                navArgumentParameter.name,
            )
        }

    companion object {
        private const val ARGUMENTS_MAP_PROPERTY_NAME = "argumentsMap"
        private const val DESTINATION_ID_PROPERTY_NAME = "destinationId"
    }
}
