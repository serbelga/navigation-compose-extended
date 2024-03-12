package dev.sergiobelda.navigation.compose.extension.sample.ui.artistdetails

import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable

@Composable
fun ArtistDetailsScreen(
    artistId: String,
    artistName: String
) {
    Row {
        Text(text = artistId)
        Text(text = artistName)
    }
}
