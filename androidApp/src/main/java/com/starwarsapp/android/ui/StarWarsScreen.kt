package com.starwarsapp.android.ui

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.starwarsapp.presentation.StarWarsUiState
import com.starwarsapp.presentation.StarWarsViewModel

@Composable
fun StarWarsScreen(viewModel: StarWarsViewModel) {
    val uiState by viewModel.uiState.collectAsState()
    
    var searchText by remember { mutableStateOf("") }
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Search Section
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedTextField(
                value = searchText,
                onValueChange = { searchText = it },
                label = { Text("Search People") },
                modifier = Modifier.weight(1f),
                singleLine = true
            )
            Button(
                onClick = { viewModel.searchPeople(searchText) },
                enabled = !uiState.isLoading
            ) {
                Text("Search")
            }
        }
        
        Spacer(modifier = Modifier.height(16.dp))
        
        // Content
        when {
            uiState.isLoading -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }
            uiState.error != null -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text(
                            text = "Error: ${uiState.error}",
                            color = MaterialTheme.colorScheme.error
                        )
                        Button(onClick = { viewModel.searchPeople(searchText) }) {
                            Text("Retry")
                        }
                    }
                }
            }
            uiState.people.isEmpty() -> {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text("Enter a search term and press Search")
                }
            }
            else -> {
                PeopleList(
                    people = uiState.people,
                    onPersonClick = { person ->
                        viewModel.loadPlanet(person.homeworld)
                    }
                )
            }
        }
    }
    
    // Planet Details Dialog
    if (uiState.showPlanetDetails) {
        PlanetDetailsDialog(
            planet = uiState.selectedPlanet,
            isLoading = uiState.isLoadingPlanet,
            error = uiState.planetError,
            onDismiss = { viewModel.closePlanetDetails() }
        )
    }
}

@Composable
fun PeopleList(
    people: List<com.starwarsapp.data.model.Person>,
    onPersonClick: (com.starwarsapp.data.model.Person) -> Unit
) {
    LazyColumn(
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(people, key = { it.name }) { person ->
            Card(
                onClick = { onPersonClick(person) },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = person.name,
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.padding(16.dp)
                )
            }
        }
    }
}

@Composable
fun PlanetDetailsDialog(
    planet: com.starwarsapp.data.model.Planet?,
    isLoading: Boolean,
    error: String?,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text("Planet Details") },
        text = {
            when {
                isLoading -> {
                    CircularProgressIndicator()
                }
                error != null -> {
                    Text("Error: $error", color = MaterialTheme.colorScheme.error)
                }
                planet != null -> {
                    Column(
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text("Name: ${planet.name}")
                        Text("Terrain: ${planet.terrain}")
                        Text("Gravity: ${planet.gravity}")
                        Text("Population: ${planet.population}")
                    }
                }
            }
        },
        confirmButton = {
            TextButton(onClick = onDismiss) {
                Text("Close")
            }
        }
    )
}

