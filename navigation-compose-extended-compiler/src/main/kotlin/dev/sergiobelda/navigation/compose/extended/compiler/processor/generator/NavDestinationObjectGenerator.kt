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
import com.squareup.kotlinpoet.CodeBlock
import com.squareup.kotlinpoet.KModifier
import com.squareup.kotlinpoet.LambdaTypeName
import com.squareup.kotlinpoet.MemberName
import com.squareup.kotlinpoet.ParameterizedTypeName.Companion.parameterizedBy
import com.squareup.kotlinpoet.PropertySpec
import com.squareup.kotlinpoet.TypeSpec
import com.squareup.kotlinpoet.asClassName
import com.squareup.kotlinpoet.buildCodeBlock

/**
 * TODO Add documentation
 */
internal class NavDestinationObjectGenerator(
    private val name: String,
    private val navArgumentKeysClass: ClassName,
    private val isTopLevelNavDestination: Boolean,
    private val destinationId: String,
    private val navArgumentParameters: List<KSValueParameter>,
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
                // TODO Add destinationId as const
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
            .build()
    }

    private fun argumentsMapProperty(): PropertySpec =
        PropertySpec.Companion.builder(
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
                    addStatement("%M(", MemberName("kotlin.collections", "mapOf"))
                    indent()
                    addNavArguments()
                    unindent()
                    addStatement(")")
                },
            )
            .build()

    private fun CodeBlock.Builder.addNavArguments() {
        navArgumentParameters.forEach { navArgumentParameter ->
            navArgumentParameter.name?.asString()?.let {
                addStatement(
                    "%T.%L to {",
                    navArgumentKeysClass,
                    it.formatNavArgumentKey(),
                )
                addNavArgumentBuilderProperties(navArgumentParameter)
                addStatement("},")
            }
        }
    }

    private fun CodeBlock.Builder.addNavArgumentBuilderProperties(parameter: KSValueParameter) {
        indent()
        val type = parameter.type.resolve().declaration.qualifiedName?.asString()?.parseType()
        addStatement("type = %T.$type", ClassNames.NavType)
        unindent()
    }

    private fun String.parseType(): String = when (this) {
        "kotlin.String" -> "StringType"
        "kotlin.Int" -> "IntType"
        "kotlin.Boolean" -> "BoolType"
        "kotlin.Float" -> "FloatType"
        "kotlin.Long" -> "LongType"
        else -> "StringType"
    }

    companion object {
        private const val ARGUMENTS_MAP_PROPERTY_NAME = "argumentsMap"
        private const val DESTINATION_ID_PROPERTY_NAME = "destinationId"
    }
}
