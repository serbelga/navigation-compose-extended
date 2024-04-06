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

import com.google.devtools.ksp.KspExperimental
import com.google.devtools.ksp.getAnnotationsByType
import com.google.devtools.ksp.processing.CodeGenerator
import com.google.devtools.ksp.processing.KSPLogger
import com.google.devtools.ksp.symbol.KSFunctionDeclaration
import com.google.devtools.ksp.symbol.KSValueParameter
import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.FileSpec
import com.squareup.kotlinpoet.FunSpec
import com.squareup.kotlinpoet.KModifier
import com.squareup.kotlinpoet.LambdaTypeName
import com.squareup.kotlinpoet.ParameterizedTypeName.Companion.parameterizedBy
import com.squareup.kotlinpoet.PropertySpec
import com.squareup.kotlinpoet.TypeSpec
import com.squareup.kotlinpoet.asClassName
import com.squareup.kotlinpoet.ksp.writeTo
import dev.sergiobelda.navigation.compose.extended.compiler.annotation.NavArgument
import dev.sergiobelda.navigation.compose.extended.compiler.annotation.NavDestination
import java.util.Locale

internal class NavDestinationGenerator(
    private val logger: KSPLogger,
    private val codeGenerator: CodeGenerator,
) {
    @OptIn(KspExperimental::class)
    fun generate(
        functionDeclaration: KSFunctionDeclaration,
    ) {
        val packageName = functionDeclaration.packageName.asString()
        val classSimpleName = functionDeclaration.simpleName.asString()
        val annotation: NavDestination? = functionDeclaration
            .getAnnotationsByType(NavDestination::class)
            .firstOrNull()

        val navArgumentParameters = functionDeclaration
            .parameters
            .filter {
                it.getAnnotationsByType(NavArgument::class).toList().isNotEmpty()
            }

        requireNotNull(annotation) {
            "NavDestination annotation not found in $functionDeclaration function."
        }

        val name =
            annotation.name.takeUnless { it.isBlank() }?.formatName() ?: classSimpleName
        val destinationId = annotation.destinationId

        // TODO: Move to constants
        val fileName = "${name}Navigation"
        val navArgumentKeysName = "${name}NavArgumentKeys"
        val navDestinationName = "${name}NavDestination"
        val safeNavArgsName = "${name}SafeNavArgs"

        val fileSpec = FileSpec.builder(
            packageName = packageName,
            fileName = fileName,
        ).apply {
            addType(
                navArgumentsKeysEnumClass(
                    name = navArgumentKeysName,
                    navArgumentParameters = navArgumentParameters,
                ),
            )
            addType(
                navDestinationObject(
                    packageName = packageName,
                    name = navDestinationName,
                    navArgumentKeysName = navArgumentKeysName,
                    isTopLevelNavDestination = annotation.isTopLevelNavDestination,
                    destinationId = destinationId,
                    navArgumentParameters = navArgumentParameters,
                ),
            )
            addType(safeNavArgsClass(safeNavArgsName))
        }.build()

        fileSpec.writeTo(codeGenerator = codeGenerator, aggregating = false)
    }

    private fun navArgumentsKeysEnumClass(
        name: String,
        navArgumentParameters: List<KSValueParameter>,
    ): TypeSpec =
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
            .addNavArgumentParameters(navArgumentParameters)
            .build()

    private fun TypeSpec.Builder.addNavArgumentParameters(
        navArgumentParameters: List<KSValueParameter>,
    ): TypeSpec.Builder {
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

    private fun navDestinationObject(
        packageName: String,
        name: String,
        navArgumentKeysName: String,
        isTopLevelNavDestination: Boolean,
        destinationId: String,
        navArgumentParameters: List<KSValueParameter>,
    ): TypeSpec {
        val superClass = if (isTopLevelNavDestination) {
            ClassNames.TopLevelNavDestination
        } else {
            ClassNames.NavDestination
        }

        return TypeSpec.objectBuilder(name)
            .superclass(
                superClass.parameterizedBy(
                    ClassName(packageName, navArgumentKeysName)
                ),
            )
            .addProperty(
                // TODO Add destinationId as const
                PropertySpec.builder("destinationId", String::class, KModifier.OVERRIDE)
                    .initializer("%S", destinationId)
                    .build(),
            )
            .addProperty(
                argumentsMapProperty(
                    packageName = packageName,
                    navArgumentKeysName = navArgumentKeysName,
                    navArgumentParameters = navArgumentParameters,
                ),
            )
            .build()
    }

    private fun argumentsMapProperty(
        packageName: String,
        navArgumentKeysName: String,
        navArgumentParameters: List<KSValueParameter>,
    ): PropertySpec =
        PropertySpec.builder(
            "argumentsMap",
            Map::class.asClassName()
                .parameterizedBy(
                    ClassName(packageName, navArgumentKeysName),
                    LambdaTypeName.get(
                        receiver = ClassNames.NavArgumentBuilder,
                        returnType = Unit::class.asClassName(),
                    ),
                ),
        ).addModifiers(KModifier.OVERRIDE)
            .initializer("mapOf()")
            .build()

    private fun safeNavArgsClass(name: String): TypeSpec =
        TypeSpec.classBuilder(name)
            .build()

    private fun String.formatName(): String =
        replaceFirstChar {
            if (it.isLowerCase()) it.titlecase(Locale.ROOT) else it.toString()
        }.trim()
}
