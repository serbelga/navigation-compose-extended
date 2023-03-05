package dev.sergiobelda.navigation.compose.extension.sample.ui.artistdetails

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun ArtistDetailsScreen(
    artistId: String
) {
    Text(text = artistId)
}
