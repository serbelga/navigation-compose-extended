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

package dev.sergiobelda.navigation.compose.extended.sample.ui.home

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material.icons.rounded.AccountBox
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ScaffoldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import dev.sergiobelda.navigation.compose.extended.NavAction
import dev.sergiobelda.navigation.compose.extended.NavArgumentKey
import dev.sergiobelda.navigation.compose.extended.NavDestination
import dev.sergiobelda.navigation.compose.extended.NavHost
import dev.sergiobelda.navigation.compose.extended.composable
import dev.sergiobelda.navigation.compose.extended.navigation
import dev.sergiobelda.navigation.compose.extended.rememberNavAction
import dev.sergiobelda.navigation.compose.extended.sample.R
import dev.sergiobelda.navigation.compose.extended.sample.ui.search.SearchNavDestination
import dev.sergiobelda.navigation.compose.extended.sample.ui.search.initial.SearchInitialNavDestination
import dev.sergiobelda.navigation.compose.extended.sample.ui.search.initial.SearchInitialScreen
import dev.sergiobelda.navigation.compose.extended.sample.ui.search.result.SearchResultNavArgumentKeys
import dev.sergiobelda.navigation.compose.extended.sample.ui.search.result.SearchResultNavDestination
import dev.sergiobelda.navigation.compose.extended.sample.ui.search.result.SearchResultScreen
import dev.sergiobelda.navigation.compose.extended.sample.ui.yourlibrary.YourLibraryNavDestination
import dev.sergiobelda.navigation.compose.extended.sample.ui.yourlibrary.YourLibraryScreen

private enum class HomeNavigationBarItem(
    val navDestination: NavDestination<NavArgumentKey>,
    val icon: ImageVector,
    @StringRes val labelResId: Int,
) {
    Search(SearchNavDestination, Icons.Rounded.Search, R.string.search),
    YourLibrary(YourLibraryNavDestination, Icons.Rounded.AccountBox, R.string.your_library),
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    navigateToSettings: () -> Unit,
) {
    val navController = rememberNavController()
    val navAction = rememberNavAction(navController)
    Scaffold(
        topBar = {
            TopAppBar(
                title = {},
                actions = {
                    IconButton(
                        onClick = navigateToSettings,
                    ) {
                        Icon(Icons.Outlined.Settings, contentDescription = null)
                    }
                },
            )
        },
        bottomBar = {
            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val currentDestination = navBackStackEntry?.destination
            NavigationBar {
                HomeNavigationBarItem.entries.forEach { item ->
                    NavigationBarItem(
                        selected = currentDestination?.hierarchy?.any { it.route == item.navDestination.route } == true,
                        onClick = { navAction.navigate(item.navDestination.navRoute()) },
                        icon = { Icon(item.icon, contentDescription = null) },
                        label = { Text(text = stringResource(item.labelResId)) },
                    )
                }
            }
        },
        contentWindowInsets = ScaffoldDefaults.contentWindowInsets.only(sides = WindowInsetsSides.Bottom),
    ) { paddingValues ->
        NavHost(
            navController = navController,
            startNavDestination = SearchNavDestination,
            modifier = Modifier.padding(paddingValues),
        ) {
            searchNavDestination(navAction)
            yourLibraryNavDestination()
        }
    }
}

private fun NavGraphBuilder.searchNavDestination(
    navAction: NavAction,
) {
    navigation(
        navDestination = SearchNavDestination,
        startNavDestination = SearchInitialNavDestination,
        arguments = emptyList(),
    ) {
        composable(
            navDestination = SearchInitialNavDestination,
        ) {
            SearchInitialScreen(
                navigateToSearchResult = { search, category ->
                    navAction.navigate(
                        SearchResultNavDestination.navRoute(
                            SearchResultNavArgumentKeys.SearchNavArgumentKey to search,
                        ),
                    )
                },
            )
        }
        composable(
            navDestination = SearchResultNavDestination,
        ) {
            val navArgs = SearchResultNavDestination.navArgs(it)
            val search = navArgs.getStringOrDefault(
                SearchResultNavArgumentKeys.SearchNavArgumentKey,
                "",
            )
            val category = navArgs.getStringOrDefault(
                SearchResultNavArgumentKeys.CategoryNavArgumentKey,
                "",
            )
            SearchResultScreen(
                navigateBack = { navAction.navigateUp() },
                search = search,
                category = category,
            )
        }
    }
}

private fun NavGraphBuilder.yourLibraryNavDestination() {
    composable(
        navDestination = YourLibraryNavDestination,
    ) {
        YourLibraryScreen()
    }
}
