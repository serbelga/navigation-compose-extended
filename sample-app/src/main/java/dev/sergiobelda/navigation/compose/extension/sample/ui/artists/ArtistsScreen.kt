package dev.sergiobelda.navigation.compose.extension.sample.ui.artists

import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun ArtistsScreen(
    navigateToArtistDetails: (String) -> Unit
) {
    Button(onClick = { navigateToArtistDetails("Hola") }) {
        Text(text = "Navigate")
    }
}
