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
import com.squareup.kotlinpoet.FunSpec
import com.squareup.kotlinpoet.KModifier
import com.squareup.kotlinpoet.PropertySpec
import com.squareup.kotlinpoet.TypeSpec

internal class NavArgumentsKeysEnumClassGenerator(
    private val name: String,
    private val navArgumentParameters: List<KSValueParameter>,
) {
    fun generate(): TypeSpec =
        // TODO Add argumentKey as const
        TypeSpec.enumBuilder(name)
            .primaryConstructor(
                FunSpec.constructorBuilder()
                    .addParameter(
                        "argumentKey",
                        String::class,
                    )
                    .build(),
            )
            .addProperty(
                PropertySpec.builder("argumentKey", String::class)
                    .initializer("argumentKey")
                    .addModifiers(KModifier.OVERRIDE)
                    .build(),
            )
            .addSuperinterface(
                ClassNames.NavArgumentKey,
            )
            .addNavArgumentParameters()
            .build()

    private fun TypeSpec.Builder.addNavArgumentParameters(): TypeSpec.Builder {
        navArgumentParameters.forEach { navArgumentParameter ->
            // TODO Add NavArgumentKey as const
            navArgumentParameter.name?.asString()?.formatName()?.plus("NavArgumentKey")?.let {
                addEnumConstant(
                    it,
                    TypeSpec.anonymousClassBuilder()
                        .addSuperclassConstructorParameter("%S", it)
                        .build(),
                )
            }
        }
        return this
    }
}
