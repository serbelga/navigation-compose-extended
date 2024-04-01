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

package dev.sergiobelda.navigation.compose.extended.compiler.processor

import com.google.devtools.ksp.processing.CodeGenerator
import com.google.devtools.ksp.symbol.KSClassDeclaration
import com.google.devtools.ksp.symbol.KSDeclaration
import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.CodeBlock
import com.squareup.kotlinpoet.FileSpec
import com.squareup.kotlinpoet.FunSpec
import com.squareup.kotlinpoet.KModifier
import com.squareup.kotlinpoet.ParameterizedTypeName.Companion.plusParameter
import com.squareup.kotlinpoet.PropertySpec
import com.squareup.kotlinpoet.TypeSpec
import com.squareup.kotlinpoet.ksp.writeTo

class SafeNavArgsGenerator(private val codeGenerator: CodeGenerator) {
    fun generate(
        classDeclaration: KSClassDeclaration,
    ) {
        val packageName = classDeclaration.packageName.asString()
        val classSimpleName = classDeclaration.simpleName.asString()
        // TODO: Regex name and apply suffix
        val safeNavDestinationName = "${classSimpleName}SafeNavArgs"

        val navArgumentKey = classDeclaration.superTypes.first().resolve().arguments.first().type?.resolve()?.declaration
        // val navArgumentKey = classDeclaration.superTypes.find { it.resolve().declaration  } first().resolve().declaration.typeParameters.first()

        val fileSpec = FileSpec.builder(
            packageName = packageName,
            fileName = safeNavDestinationName,
        ).apply {
            addType(
                TypeSpec.classBuilder(safeNavDestinationName)
                    .primaryConstructor(
                        FunSpec.constructorBuilder()
                            // TODO: navBackStackEntry as String
                            .addParameter(
                                NavBackStackEntryParameter,
                                ClassName(AndroidXNavigationPackage, NavBackStackEntrySingleClassName),
                            )
                            .build(),
                    )
                    .addProperty(navArgsProperty(classSimpleName,
                        navArgumentKey
                    ))
                    .build(),
            )
        }.build()

        fileSpec.writeTo(codeGenerator = codeGenerator, aggregating = false)
    }
}

private fun navArgsProperty(
    navDestinationName: String,
    navArgumentKey: KSDeclaration?
): PropertySpec =
    PropertySpec
        .builder(NavArgsProperty, ClassName(NavigationComposeExtendedPackage, NavArgsSingleClassName).plusParameter(ClassName(navArgumentKey?.packageName?.asString()!!, navArgumentKey.simpleName.asString())))
        .delegate(
            CodeBlock.of("lazy { %N.%N(%N) }", navDestinationName, NavArgsProperty, NavBackStackEntryParameter)
        )
        .addModifiers(KModifier.PRIVATE)
        .build()

private const val NavArgsProperty = "navArgs"
private const val NavBackStackEntryParameter = "navBackStackEntry"
