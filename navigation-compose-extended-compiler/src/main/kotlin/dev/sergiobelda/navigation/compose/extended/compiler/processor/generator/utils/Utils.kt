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

package dev.sergiobelda.navigation.compose.extended.compiler.processor.generator.utils

/**
 * Apply a Kotlin Property format to the given [String].
 */
internal fun String.toKotlinPropertyName(): String {
    // Replace any "_" or "-" followed by a letter or digit with the uppercase letter.
    // e.g. "_a" -> "A", "--a" -> "A", "__1" -> "_1", "--1" -> "_1"
    val pattern = "[_-]+[a-zA-Z0-9]".toRegex()
    val name =
        replace(pattern) { match ->
            if (match.value.last().isDigit()) {
                "_${match.value.last()}"
            } else {
                match.value.last().uppercase()
            }
        }

    // If the first character is a digit, prefix the name with an "_".
    return name.let {
        if (it.firstOrNull()?.isDigit() == true) "_$it" else it.trim().lowercaseFirstChar()
    }
}

internal fun String.formatName(): String = uppercaseFirstChar().trim()

private fun String.lowercaseFirstChar(): String = replaceFirstChar(Char::lowercase)

private fun String.uppercaseFirstChar(): String = replaceFirstChar(Char::titlecase)

/**
 * Add the "NavArgumentKey" suffix to the given [String].
 */
internal fun String.formatNavArgumentKey(): String = formatName().plus(NAV_ARGUMENT_KEY_SUFFIX)

private const val NAV_ARGUMENT_KEY_SUFFIX = "NavArgumentKey"
