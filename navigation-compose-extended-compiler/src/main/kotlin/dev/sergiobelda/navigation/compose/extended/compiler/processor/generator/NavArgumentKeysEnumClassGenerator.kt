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

import com.squareup.kotlinpoet.FunSpec
import com.squareup.kotlinpoet.KModifier
import com.squareup.kotlinpoet.PropertySpec
import com.squareup.kotlinpoet.TypeSpec

/**
 * Generates an enum class that contains the navigation arguments keys. The enum class inherits
 * from NavArgumentKey contains as many entries as [navArguments].
 */
internal class NavArgumentKeysEnumClassGenerator(
    private val name: String,
    private val navArguments: List<NavArgument>,
) {
    fun generate(): TypeSpec =
        TypeSpec.enumBuilder(name)
            .primaryConstructor(
                FunSpec.constructorBuilder()
                    .addParameter(
                        ARGUMENT_KEY_PARAM_NAME,
                        String::class,
                    )
                    .build(),
            )
            .addProperty(
                PropertySpec.builder(ARGUMENT_KEY_PARAM_NAME, String::class)
                    .initializer(ARGUMENT_KEY_PARAM_NAME)
                    .addModifiers(KModifier.OVERRIDE)
                    .build(),
            )
            .addSuperinterface(
                ClassNames.NavArgumentKey,
            )
            .addNavArguments()
            .build()

    private fun TypeSpec.Builder.addNavArguments() =
        apply {
            navArguments.forEach { navArgument ->
                addEnumConstant(
                    navArgument.name.formatNavArgumentKey(),
                    TypeSpec.anonymousClassBuilder()
                        .addSuperclassConstructorParameter("%S", navArgument.name)
                        .build(),
                )
            }
        }

    companion object {
        private const val ARGUMENT_KEY_PARAM_NAME = "argumentKey"
    }
}
