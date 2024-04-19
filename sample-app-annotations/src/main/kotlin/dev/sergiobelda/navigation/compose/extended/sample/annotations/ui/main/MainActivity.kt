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

package dev.sergiobelda.navigation.compose.extended.sample.annotations.ui.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.rememberNavController
import dev.sergiobelda.navigation.compose.extended.NavHost
import dev.sergiobelda.navigation.compose.extended.composable
import dev.sergiobelda.navigation.compose.extended.rememberNavAction
import dev.sergiobelda.navigation.compose.extended.sample.annotations.ui.home.HomeNavDestination
import dev.sergiobelda.navigation.compose.extended.sample.annotations.ui.home.HomeScreen
import dev.sergiobelda.navigation.compose.extended.sample.annotations.ui.settings.SettingsNavDestination
import dev.sergiobelda.navigation.compose.extended.sample.annotations.ui.settings.SettingsSafeNavArgs
import dev.sergiobelda.navigation.compose.extended.sample.annotations.ui.settings.SettingsScreen
import dev.sergiobelda.navigation.compose.extended.sample.annotations.ui.theme.SampleTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SampleTheme {
                val navController = rememberNavController()
                val navAction = rememberNavAction(navController)
                NavHost(navController = navController, startNavDestination = HomeNavDestination) {
                    composable(navDestination = HomeNavDestination) {
                        HomeScreen(
                            navigateToSettings = {
                                navAction.navigate(
                                    SettingsNavDestination.safeNavRoute(
                                        userId = 1,
                                    ),
                                )
                            },
                        )
                    }
                    composable(navDestination = SettingsNavDestination) { navBackStackEntry ->
                        val safeNavArgs = SettingsSafeNavArgs(navBackStackEntry)
                        SettingsScreen(
                            userId = safeNavArgs.userId ?: 0,
                            text = safeNavArgs.text,
                            result = safeNavArgs.customName ?: false,
                        )
                    }
                }
            }
        }
    }
}
