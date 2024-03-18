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

package dev.sergiobelda.navigation.compose.extended.sample

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ScaffoldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dev.sergiobelda.navigation.compose.extended.rememberNavAction
import dev.sergiobelda.navigation.compose.extended.sample.ui.search.SearchNavDestination
import dev.sergiobelda.navigation.compose.extended.sample.ui.search.SearchScreen
import dev.sergiobelda.navigation.compose.extended.sample.ui.searchresult.SearchResultNavArgumentKeys
import dev.sergiobelda.navigation.compose.extended.sample.ui.searchresult.SearchResultNavDestination
import dev.sergiobelda.navigation.compose.extended.sample.ui.searchresult.SearchResultScreen
import dev.sergiobelda.navigation.compose.extended.sample.ui.searchresult.customNavRoute
import dev.sergiobelda.navigation.compose.extended.sample.ui.theme.SampleTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            SampleTheme {
                MainScreen()
            }
        }
    }
}

@Composable
fun MainScreen() {
    val navController = rememberNavController()
    val navAction = rememberNavAction(navController)
    Scaffold(
        bottomBar = {
            BottomAppBar {
                NavigationBarItem(
                    selected = true,
                    onClick = { navAction.navigate(SearchNavDestination.navRoute()) },
                    icon = { Icon(Icons.Rounded.Search, contentDescription = null) },
                    label = { Text(text = "Search") },
                )
            }
        },
        contentWindowInsets = ScaffoldDefaults.contentWindowInsets.only(sides = WindowInsetsSides.Bottom)
    ) { paddingValues ->
        NavHost(
            navController = navController,
            startDestination = SearchNavDestination.route,
            modifier = Modifier.padding(paddingValues),
        ) {
            composable(
                route = SearchNavDestination.route,
            ) {
                SearchScreen(
                    navigateToSearchResults = { search, category ->
                        navAction.navigate(
                            SearchResultNavDestination.customNavRoute(search),
                        )
                    },
                )
            }
            composable(
                route = SearchResultNavDestination.route,
                arguments = SearchResultNavDestination.arguments,
            ) {
                val navSafeArgs = SearchResultNavDestination.navSafeArgs(it)
                val search = navSafeArgs.getStringOrDefault(
                    SearchResultNavArgumentKeys.SearchNavArgumentKey,
                    ""
                )
                val category = navSafeArgs.getStringOrDefault(
                    SearchResultNavArgumentKeys.CategoryNavArgumentKey,
                    ""
                )
                SearchResultScreen(search, category)
            }
        }
    }
}
