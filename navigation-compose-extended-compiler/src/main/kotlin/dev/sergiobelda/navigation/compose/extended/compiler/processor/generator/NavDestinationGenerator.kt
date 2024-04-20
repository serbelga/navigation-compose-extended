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
import com.google.devtools.ksp.symbol.KSFunctionDeclaration
import com.squareup.kotlinpoet.ClassName
import com.squareup.kotlinpoet.FileSpec
import com.squareup.kotlinpoet.ksp.writeTo
import dev.sergiobelda.navigation.compose.extended.compiler.annotation.NavDestination

/**
 * Generate code for functions annotated with [NavDestination] parameter.
 */
internal class NavDestinationGenerator(
    private val codeGenerator: CodeGenerator,
) {
    @OptIn(KspExperimental::class)
    fun generate(
        functionDeclaration: KSFunctionDeclaration,
    ) {
        val annotation: NavDestination? = functionDeclaration
            .getAnnotationsByType(NavDestination::class)
            .firstOrNull()

        requireNotNull(annotation) {
            "NavDestination annotation not found in $functionDeclaration function."
        }

        val navArguments = annotation
            .arguments
            .map {
                NavArgument(
                    name = it.name,
                    type = it.type,
                    nullable = it.nullable,
                    defaultValue = it.defaultValue,
                )
            }

        val navArgumentNames = navArguments.groupBy { it.name }.values.filter { it.size > 1 }
        require(navArgumentNames.isEmpty()) {
            "NavArgument names must be unique. Duplicated names: ${navArgumentNames.joinToString { it.first().name }}"
        }

        val packageName = functionDeclaration.packageName.asString()
        val functionSimpleName = functionDeclaration.simpleName.asString()
        val name =
            annotation.name.takeUnless { it.isBlank() }?.formatName() ?: functionSimpleName
        val navArgumentKeysName = name + NAV_ARGUMENT_KEYS
        val navArgumentKeysClass = ClassName(packageName, navArgumentKeysName)
        val navDestinationName = name + NAV_DESTINATION
        val navDestinationClass = ClassName(packageName, navDestinationName)
        val safeNavArgsName = name + SAFE_NAV_ARGS

        val fileSpec = FileSpec.builder(
            packageName = packageName,
            fileName = name + NAVIGATION_FILE_NAME_SUFFIX,
        ).apply {
            addType(
                NavArgumentKeysEnumClassGenerator(
                    name = navArgumentKeysName,
                    navArguments = navArguments,
                ).generate(),
            )
            addType(
                NavDestinationObjectGenerator(
                    name = navDestinationName,
                    isTopLevelNavDestination = annotation.isTopLevelNavDestination,
                    destinationId = annotation.destinationId,
                    navArgumentKeysClass = navArgumentKeysClass,
                    navArguments = navArguments,
                    deepLinksUris = annotation.deepLinkUris,
                ).generate(),
            )
            addType(
                SafeNavArgsClassGenerator(
                    name = safeNavArgsName,
                    navDestinationClass = navDestinationClass,
                    navArgumentKeysClass = navArgumentKeysClass,
                    navArguments = navArguments,
                ).generate(),
            )
        }.build()

        fileSpec.writeTo(codeGenerator = codeGenerator, aggregating = false)
    }

    companion object {
        private const val NAVIGATION_FILE_NAME_SUFFIX = "Navigation"
        private const val NAV_ARGUMENT_KEYS = "NavArgumentKeys"
        private const val NAV_DESTINATION = "NavDestination"
        private const val SAFE_NAV_ARGS = "SafeNavArgs"
    }
}
