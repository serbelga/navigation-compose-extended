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

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import dev.sergiobelda.navigation.compose.extended.composable
import dev.sergiobelda.navigation.compose.extended.rememberNavAction
import dev.sergiobelda.navigation.compose.extended.sample.ui.home.HomeNavDestination
import dev.sergiobelda.navigation.compose.extended.sample.ui.home.HomeScreen
import dev.sergiobelda.navigation.compose.extended.sample.ui.settings.SettingsNavDestination
import dev.sergiobelda.navigation.compose.extended.sample.ui.settings.SettingsScreen

@Composable
fun MainScreen() {
    val navController = rememberNavController()
    val navAction = rememberNavAction(navController)
    NavHost(
        navController = navController,
        startDestination = HomeNavDestination.route,
    ) {
        composable(
            navDestination = HomeNavDestination,
        ) {
            HomeScreen(
                navigateToSettings = {
                    navAction.navigate(SettingsNavDestination.navRoute())
                },
            )
        }
        composable(
            navDestination = SettingsNavDestination,
        ) {
            SettingsScreen()
        }
    }
}
