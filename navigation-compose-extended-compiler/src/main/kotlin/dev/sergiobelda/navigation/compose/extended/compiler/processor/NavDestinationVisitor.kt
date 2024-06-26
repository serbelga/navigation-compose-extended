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
import com.google.devtools.ksp.symbol.KSFunctionDeclaration
import com.google.devtools.ksp.symbol.KSVisitorVoid
import dev.sergiobelda.navigation.compose.extended.compiler.processor.generator.NavDestinationGenerator

internal class NavDestinationVisitor(
    codeGenerator: CodeGenerator,
) : KSVisitorVoid() {
    private val navDestinationGenerator = NavDestinationGenerator(codeGenerator)

    override fun visitFunctionDeclaration(function: KSFunctionDeclaration, data: Unit) {
        navDestinationGenerator.generate(
            functionDeclaration = function,
        )
    }
}
