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
                            navigateToArtistDetails = { artistId ->
                                action.navigate(ArtistDetailsNavRoute(artistId, "World"))
                            }
                        )
                    }
                    composable(
                        route = ArtistDetailsNavDestination.route,
                        arguments = ArtistsNavDestination.arguments
                    ) { navBackStackEntry ->
                        /*val artistId: String = ArtistDetailsNavDestination.getNavArgumentValue<String>(
                            navBackStackEntry,
                            ArtistDetailsNavArgumentKeys.ArtistIdNavArgumentKey
                        ).orEmpty()*/
                        /*val artistName: String = ArtistDetailsNavDestination.getNavArgumentValue<String>(
                            navBackStackEntry,
                            ArtistDetailsNavArgumentKeys.ArtistNameNavArgumentKey
                        ).orEmpty()*/
                        // val navArgs = ArtistDetailsNavDestination.navArgs(navBackStackEntry)
                        //val artistId = navArgs.getArgumentValue<String>(ArtistDetailsNavArgumentKeys.ArtistIdNavArgumentKey).orEmpty()
                        //val artistName = navArgs.getArgumentValue<String>(ArtistDetailsNavArgumentKeys.ArtistNameNavArgumentKey).orEmpty()

                        val artistId = ArtistDetailsNavDestination.getString(
                            navBackStackEntry,
                            ArtistDetailsNavArgumentKeys.ArtistIdNavArgumentKey
                        ).orEmpty()
                        val artistName = ArtistDetailsNavDestination.getString(
                            navBackStackEntry,
                            ArtistDetailsNavArgumentKeys.ArtistNameNavArgumentKey
                        ).orEmpty()
                        ArtistDetailsScreen(artistId, artistName)
                    }
                }
            }
        }
    }
}
