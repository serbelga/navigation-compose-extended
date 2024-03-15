package dev.sergiobelda.navigation.compose.extended.sample.ui.search

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    navigateToSearchResults: (search: String, category: String) -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(title = { Text(text = "Search") })
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(4.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            var search by rememberSaveable { mutableStateOf("") }
            OutlinedTextField(
                value = search,
                onValueChange = { search = it },
                label = { Text(text = "Search") },
                modifier = Modifier.fillMaxWidth()
            )
            Button(onClick = { navigateToSearchResults(search, "") }) {
                Text(text = "Navigate")
            }
        }
    }
}
