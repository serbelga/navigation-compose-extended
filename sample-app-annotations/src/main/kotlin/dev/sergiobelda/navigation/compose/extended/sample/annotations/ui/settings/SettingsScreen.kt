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

package dev.sergiobelda.navigation.compose.extended.sample.annotations.ui.settings

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import dev.sergiobelda.navigation.compose.extended.compiler.annotation.NavArgument
import dev.sergiobelda.navigation.compose.extended.compiler.annotation.NavDestination

@NavDestination(
    name = "Settings",
    destinationId = "settings",
)
@Composable
fun SettingsScreen(
    @NavArgument userId: Int,
    @NavArgument(defaultValue = "Default") text: String?, // Set default value for the NavArgument.
    @NavArgument(
        name = "custom-name",
        defaultValue = "true",
    ) result: Boolean, // Set a custom NavArgument name.
) {
    Box(modifier = Modifier.fillMaxSize()) {
        Column {
            Text(text = "Settings Screen")
            Text(text = "User ID: $userId")
            Text(text = "Text: $text")
            Text(text = "Result: $result")
        }
    }
}
