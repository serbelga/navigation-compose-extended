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
import com.squareup.kotlinpoet.ParameterSpec
import com.squareup.kotlinpoet.ParameterizedTypeName.Companion.parameterizedBy
import com.squareup.kotlinpoet.PropertySpec
import com.squareup.kotlinpoet.TypeSpec
import com.squareup.kotlinpoet.asClassName
import com.squareup.kotlinpoet.buildCodeBlock
import dev.sergiobelda.navigation.compose.extended.annotation.NavArgument
import dev.sergiobelda.navigation.compose.extended.compiler.processor.generator.extensions.hasDefaultValue
import dev.sergiobelda.navigation.compose.extended.compiler.processor.generator.mapper.asTypeName
import dev.sergiobelda.navigation.compose.extended.compiler.processor.generator.mapper.toNavType
import dev.sergiobelda.navigation.compose.extended.compiler.processor.generator.mapper.toValue
import dev.sergiobelda.navigation.compose.extended.compiler.processor.generator.names.ClassNames
import dev.sergiobelda.navigation.compose.extended.compiler.processor.generator.names.MemberNames
import dev.sergiobelda.navigation.compose.extended.compiler.processor.generator.utils.formatNavArgumentKey

/**
 * Generates the NavDestination object for the navigation destination.
 */
internal class NavDestinationObjectGenerator(
    private val name: String,
    private val isTopLevelNavDestination: Boolean,
    private val destinationId: String,
    private val navArgumentKeysClass: ClassName,
    private val navArguments: Array<NavArgument>,
    private val deepLinksUris: Array<String>,
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
            .addArgumentsMapProperty()
            .addDeepLinksUrisProperty()
            .addSafeNavRouteFunction()
            .build()
    }

    private fun TypeSpec.Builder.addArgumentsMapProperty() =
        apply {
            if (navArguments.isNotEmpty()) {
                addProperty(
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
                                add(")")
                            },
                        )
                        .build(),
                )
            }
        }

    private fun CodeBlock.Builder.addNavArgumentsToMap() {
        navArguments.forEach { navArgument ->
            addStatement(
                "%T.%L to {",
                navArgumentKeysClass,
                navArgument.name.formatNavArgumentKey(),
            )
            addNavArgumentBuilderProperties(navArgument)
            addStatement("},")
        }
    }

    private fun CodeBlock.Builder.addNavArgumentBuilderProperties(
        navArgument: NavArgument,
    ) {
        indent()
        val type = navArgument.type
        addStatement("type = %T.${type.toNavType()}", ClassNames.NavType)
        if (navArgument.nullable) {
            addStatement("nullable = true")
        }
        if (navArgument.hasDefaultValue) {
            val defaultValue = navArgument.defaultValue.toValue(type)
            addStatement("defaultValue = %L", defaultValue)
        }
        unindent()
    }

    private fun TypeSpec.Builder.addDeepLinksUrisProperty() =
        apply {
            if (deepLinksUris.isNotEmpty()) {
                addProperty(
                    PropertySpec.builder(
                        DEEP_LINK_URIS_PROPERTY_NAME,
                        List::class.asClassName().parameterizedBy(String::class.asClassName()),
                    ).addModifiers(KModifier.OVERRIDE)
                        .initializer(
                            buildCodeBlock {
                                addStatement("%M(", MemberNames.ListOf)
                                indent()
                                add(
                                    deepLinksUris.joinToString(
                                        postfix = "\n",
                                        separator = ",\n",
                                    ) { "\"$it\"" },
                                )
                                unindent()
                                add(")")
                            },
                        )
                        .build(),
                )
            }
        }

    private fun TypeSpec.Builder.addSafeNavRouteFunction() =
        apply {
            if (navArguments.isNotEmpty()) {
                addFunction(
                    FunSpec.builder("safeNavRoute")
                        .addNavArgumentsToSafeNavRouteFunctionParameters()
                        .returns(
                            ClassNames.NavRoute.parameterizedBy(
                                navArgumentKeysClass,
                            ),
                        )
                        .addCode(
                            buildCodeBlock {
                                add("return %M(\n", MemberNames.NavRoute)
                                indent()
                                addNavArgumentsToSafeNavRouteFunctionBody()
                                unindent()
                                add(")")
                            },
                        )
                        .build(),
                )
            }
        }

    private fun FunSpec.Builder.addNavArgumentsToSafeNavRouteFunctionParameters() =
        apply {
            navArguments.forEach { navArgument ->
                addParameter(
                    ParameterSpec.builder(
                        navArgument.name,
                        navArgument.type.asTypeName(),
                    ).apply {
                        navArgument.takeIf { it.hasDefaultValue }?.let {
                            defaultValue(
                                "%L",
                                it.defaultValue.toValue(
                                    navArgument.type,
                                ),
                            )
                        }
                    }.build(),
                )
            }
        }

    private fun CodeBlock.Builder.addNavArgumentsToSafeNavRouteFunctionBody() =
        navArguments.forEach { navArgument ->
            addStatement(
                "%T.%L to %L,",
                navArgumentKeysClass,
                navArgument.name.formatNavArgumentKey(),
                navArgument.name,
            )
        }

    companion object {
        private const val ARGUMENTS_MAP_PROPERTY_NAME = "argumentsMap"
        private const val DESTINATION_ID_PROPERTY_NAME = "destinationId"
        private const val DEEP_LINK_URIS_PROPERTY_NAME = "deepLinkUris"
    }
}
