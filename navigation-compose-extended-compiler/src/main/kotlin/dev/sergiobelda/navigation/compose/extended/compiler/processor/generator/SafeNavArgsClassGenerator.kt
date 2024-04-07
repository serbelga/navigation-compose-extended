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
import com.squareup.kotlinpoet.FunSpec
import com.squareup.kotlinpoet.KModifier
import com.squareup.kotlinpoet.MemberName
import com.squareup.kotlinpoet.ParameterizedTypeName.Companion.parameterizedBy
import com.squareup.kotlinpoet.PropertySpec
import com.squareup.kotlinpoet.TypeSpec
import com.squareup.kotlinpoet.buildCodeBlock
import com.squareup.kotlinpoet.ksp.toTypeName

/**
 * TODO Add documentation
 */
internal class SafeNavArgsClassGenerator(
    private val name: String,
    private val navDestinationClass: ClassName,
    private val navArgumentKeysClass: ClassName,
    private val navArgumentParameters: List<KSValueParameter>,
) {
    fun generate(): TypeSpec =
        TypeSpec.classBuilder(name)
            .primaryConstructor(
                FunSpec.constructorBuilder()
                    .addParameter(
                        NAV_BACK_STACK_ENTRY_PARAMETER_NAME,
                        ClassNames.NavBackStackEntry,
                    )
                    .build(),
            )
            .addProperty(
                navArgsProperty(),
            )
            .addNavArgumentParametersGetters()
            .build()

    private fun navArgsProperty(): PropertySpec =
        PropertySpec
            .builder(
                NAV_ARGS_PROPERTY_NAME,
                ClassNames.NavArgs.parameterizedBy(navArgumentKeysClass),
            )
            .delegate(
                buildCodeBlock {
                    beginControlFlow("lazy")
                    addStatement(
                        "%T.%N(%N)",
                        navDestinationClass,
                        NAV_ARGS_PROPERTY_NAME,
                        NAV_BACK_STACK_ENTRY_PARAMETER_NAME,
                    )
                    endControlFlow()
                },
            )
            .addModifiers(KModifier.PRIVATE)
            .build()

    private fun TypeSpec.Builder.addNavArgumentParametersGetters() =
        apply {
            navArgumentParameters.forEach { navArgumentParameter ->
                navArgumentParameter.name?.asString()?.let { name ->
                    val type =
                        navArgumentParameter.type.resolve().toTypeName().copy(nullable = true)
                    val member: MemberName =
                        navArgumentParameter.type.resolve().mapToNavTypeGetter() ?: return@forEach
                    addProperty(
                        PropertySpec.builder(
                            name,
                            type,
                        ).getter(
                            FunSpec.getterBuilder()
                                .addStatement(
                                    "return %N.%M(%T.%N)",
                                    NAV_ARGS_PROPERTY_NAME,
                                    member,
                                    navArgumentKeysClass,
                                    name.formatNavArgumentKey(),
                                )
                                .build(),
                        ).build(),
                    )
                }
            }
        }

    companion object {
        private const val NAV_BACK_STACK_ENTRY_PARAMETER_NAME = "navBackStackEntry"
        private const val NAV_ARGS_PROPERTY_NAME = "navArgs"
    }
}
