package dev.sergiobelda.navigation.compose.extension.sample

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.runtime.remember
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dev.sergiobelda.navigation.compose.extension.Action
import dev.sergiobelda.navigation.compose.extension.sample.ui.albums.AlbumsNavDestination
import dev.sergiobelda.navigation.compose.extension.sample.ui.albums.AlbumsScreen
import dev.sergiobelda.navigation.compose.extension.sample.ui.artistdetails.ArtistDetailsNavDestination
import dev.sergiobelda.navigation.compose.extension.sample.ui.artistdetails.ArtistDetailsScreen
import dev.sergiobelda.navigation.compose.extension.sample.ui.artistdetails.navToArtistDetails
import dev.sergiobelda.navigation.compose.extension.sample.ui.artists.ArtistsNavDestination
import dev.sergiobelda.navigation.compose.extension.sample.ui.artists.ArtistsScreen
import dev.sergiobelda.navigation.compose.extension.sample.ui.theme.SampleTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SampleTheme {
                val navController = rememberNavController()
                val action = remember(navController) { Action(navController) }
                NavHost(
                    navController = navController,
                    startDestination = ArtistsNavDestination.route
                ) {
                    composable(route = AlbumsNavDestination.route) {
                        AlbumsScreen()
                    }
                    composable(route = ArtistsNavDestination.route) {
                        ArtistsScreen(
                            navigateToArtistDetails = action.navToArtistDetails
                        )
                    }
                    composable(
                        route = ArtistDetailsNavDestination.route,
                        arguments = ArtistsNavDestination.arguments
                    ) { navBackStackEntry ->
                        val artistId: String =
                            ArtistDetailsNavDestination.navArgArtistId(navBackStackEntry)
                        ArtistDetailsScreen(artistId)
                    }
                }
            }
        }
    }
}
