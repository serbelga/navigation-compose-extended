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

package dev.sergiobelda.navigation.compose.extended.sample.ui.main

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import dev.sergiobelda.navigation.compose.extended.compiler.annotation.NavDestination
import dev.sergiobelda.navigation.compose.extended.sample.annotations.ui.SampleAppAnnotationsNavHost

@NavDestination(destinationId = "mainb")
fun main() = application {
    Window(
        resizable = false,
        onCloseRequest = ::exitApplication,
        title = "Sample App",
    ) {
        SampleAppAnnotationsNavHost()
    }
}
