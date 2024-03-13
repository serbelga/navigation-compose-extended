package dev.sergiobelda.navigation.compose.extension.sample

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import dev.sergiobelda.navigation.compose.extension.rememberAction
import dev.sergiobelda.navigation.compose.extension.rememberNavSafeArgs
import dev.sergiobelda.navigation.compose.extension.sample.ui.albums.AlbumsNavDestination
import dev.sergiobelda.navigation.compose.extension.sample.ui.albums.AlbumsScreen
import dev.sergiobelda.navigation.compose.extension.sample.ui.artistdetails.ArtistDetailsNavArgumentKeys
import dev.sergiobelda.navigation.compose.extension.sample.ui.artistdetails.ArtistDetailsNavDestination
import dev.sergiobelda.navigation.compose.extension.sample.ui.artistdetails.ArtistDetailsNavRoute
import dev.sergiobelda.navigation.compose.extension.sample.ui.artistdetails.ArtistDetailsScreen
import dev.sergiobelda.navigation.compose.extension.sample.ui.artists.ArtistsNavDestination
import dev.sergiobelda.navigation.compose.extension.sample.ui.artists.ArtistsScreen
import dev.sergiobelda.navigation.compose.extension.sample.ui.theme.SampleTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            SampleTheme {
                val navController = rememberNavController()
                val action = rememberAction(navController)
                NavHost(
                    navController = navController,
                    startDestination = ArtistsNavDestination.route
                ) {
                    composable(route = AlbumsNavDestination.route) {
                        AlbumsScreen()
                    }
                    composable(route = ArtistsNavDestination.route) {
                        ArtistsScreen(
                            navigateToArtistDetails = { artistId ->
                                action.navigate(ArtistDetailsNavRoute(artistId, "World"))
                            }
                        )
                    }
                    addArtistDetails()
                }
            }
        }
    }
}

private fun NavGraphBuilder.addArtistDetails() {
    composable(
        route = ArtistDetailsNavDestination.route,
        arguments = ArtistsNavDestination.arguments
    ) { navBackStackEntry ->
        val navSafeArgs = rememberNavSafeArgs(ArtistDetailsNavDestination, navBackStackEntry)
        val artistId =
            navSafeArgs.getString(ArtistDetailsNavArgumentKeys.ArtistIdNavArgumentKey).orEmpty()
        val artistName =
            navSafeArgs.getString(ArtistDetailsNavArgumentKeys.ArtistNameNavArgumentKey).orEmpty()
        ArtistDetailsScreen(artistId, artistName)
    }
}
