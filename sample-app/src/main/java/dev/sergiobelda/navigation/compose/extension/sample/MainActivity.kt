package dev.sergiobelda.navigation.compose.extension.sample

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dev.sergiobelda.navigation.compose.extension.NavSafeArgs
import dev.sergiobelda.navigation.compose.extension.rememberAction
import dev.sergiobelda.navigation.compose.extension.sample.ui.search.SearchNavDestination
import dev.sergiobelda.navigation.compose.extension.sample.ui.search.SearchScreen
import dev.sergiobelda.navigation.compose.extension.sample.ui.searchresult.SearchResultNavArgumentKeys
import dev.sergiobelda.navigation.compose.extension.sample.ui.searchresult.SearchResultNavDestination
import dev.sergiobelda.navigation.compose.extension.sample.ui.searchresult.SearchResultNavRoute
import dev.sergiobelda.navigation.compose.extension.sample.ui.searchresult.SearchResultScreen
import dev.sergiobelda.navigation.compose.extension.sample.ui.theme.SampleTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
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
    val action = rememberAction(navController)
    Scaffold(
        bottomBar = {
            BottomAppBar {

            }
        }
    ) { paddingValues ->
        NavHost(
            navController = navController,
            startDestination = SearchNavDestination.route,
            modifier = Modifier.padding(paddingValues)
        ) {
            composable(
                route = SearchNavDestination.route
            ) {
                SearchScreen(
                    navigateToSearchResults = { search, category ->
                        action.navigate(SearchResultNavRoute(search))
                    }
                )
            }
            composable(
                route = SearchResultNavDestination.route,
                arguments = SearchResultNavDestination.arguments
            ) {
                val navSafeArgs = NavSafeArgs(SearchResultNavDestination, it)
                val search =
                    navSafeArgs.getString(SearchResultNavArgumentKeys.SearchNavArgumentKey)
                        .toString()
                val category =
                    navSafeArgs.getString(SearchResultNavArgumentKeys.CategoryNavArgumentKey)
                        .toString()
                SearchResultScreen(search, category)
            }
        }
    }
}
