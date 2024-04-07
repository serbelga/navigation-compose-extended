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
import com.google.devtools.ksp.processing.Resolver
import com.google.devtools.ksp.processing.SymbolProcessor
import com.google.devtools.ksp.symbol.KSAnnotated
import com.google.devtools.ksp.validate
import dev.sergiobelda.navigation.compose.extended.compiler.annotation.NavDestination

internal class NavDestinationProcessor(
    codeGenerator: CodeGenerator,
) : SymbolProcessor {

    private val navDestinationValidator = NavDestinationValidator()

    private val navDestinationVisitor = NavDestinationVisitor(codeGenerator)

    override fun process(resolver: Resolver): List<KSAnnotated> {
        var symbols: List<KSAnnotated> = emptyList()
        val annotationName = NavDestination::class.qualifiedName
        if (annotationName != null) {
            val resolvedSymbols = resolver
                .getSymbolsWithAnnotation(annotationName)
                .toList()
            val validatedSymbols = resolvedSymbols.filter { it.validate() }.toList()
            validatedSymbols
                .filter {
                    navDestinationValidator.isValid(it)
                }
                .forEach {
                    it.accept(navDestinationVisitor, Unit)
                }
            symbols = resolvedSymbols - validatedSymbols.toSet()
        }
        return symbols
    }
}
