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

import com.google.devtools.ksp.KspExperimental
import com.google.devtools.ksp.getAnnotationsByType
import com.google.devtools.ksp.processing.CodeGenerator
import com.google.devtools.ksp.processing.KSPLogger
import com.google.devtools.ksp.symbol.KSFunctionDeclaration
import com.squareup.kotlinpoet.FileSpec
import com.squareup.kotlinpoet.ksp.writeTo
import dev.sergiobelda.navigation.compose.extended.compiler.annotation.NavArgument
import dev.sergiobelda.navigation.compose.extended.compiler.annotation.NavDestination

/**
 * TODO Add documentation
 */
internal class NavDestinationGenerator(
    private val logger: KSPLogger,
    private val codeGenerator: CodeGenerator,
) {
    @OptIn(KspExperimental::class)
    fun generate(
        functionDeclaration: KSFunctionDeclaration,
    ) {
        val packageName = functionDeclaration.packageName.asString()
        val functionSimpleName = functionDeclaration.simpleName.asString()

        val annotation: NavDestination? = functionDeclaration
            .getAnnotationsByType(NavDestination::class)
            .firstOrNull()

        requireNotNull(annotation) {
            "NavDestination annotation not found in $functionDeclaration function."
        }

        val navArgumentParameters = functionDeclaration
            .parameters
            .filter {
                it.getAnnotationsByType(NavArgument::class).toList().isNotEmpty()
            }

        val name =
            annotation.name.takeUnless { it.isBlank() }?.formatName() ?: functionSimpleName
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
                NavArgumentsKeysEnumClassGenerator(
                    name = navArgumentKeysName,
                    navArgumentParameters = navArgumentParameters,
                ).generate(),
            )
            addType(
                NavDestinationObjectGenerator(
                    packageName = packageName,
                    name = navDestinationName,
                    navArgumentKeysName = navArgumentKeysName,
                    isTopLevelNavDestination = annotation.isTopLevelNavDestination,
                    destinationId = annotation.destinationId,
                    navArgumentParameters = navArgumentParameters,
                ).generate(),
            )
            addType(
                SafeNavArgsClassGenerator(
                    name = safeNavArgsName,
                ).generate(),
            )
        }.build()

        fileSpec.writeTo(codeGenerator = codeGenerator, aggregating = false)
    }
}
