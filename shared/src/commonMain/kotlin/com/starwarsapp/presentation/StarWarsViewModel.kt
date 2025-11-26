package com.starwarsapp.presentation

import com.starwarsapp.data.api.StarWarsApi
import com.starwarsapp.data.model.Person
import com.starwarsapp.data.model.Planet
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class StarWarsViewModel(
    private val api: StarWarsApi,
    private val coroutineScope: CoroutineScope = CoroutineScope(SupervisorJob() + Dispatchers.Default)
) {
    private val _uiState = MutableStateFlow(StarWarsUiState())
    val uiState: StateFlow<StarWarsUiState> = _uiState.asStateFlow()
    
    fun searchPeople(searchTerm: String) {
        if (searchTerm.isBlank()) {
            _uiState.value = _uiState.value.copy(
                people = emptyList(),
                isLoading = false,
                error = null
            )
            return
        }
        
        coroutineScope.launch {
            _uiState.value = _uiState.value.copy(
                isLoading = true,
                error = null
            )
            
            try {
                val people = api.getAllPeople(searchTerm)
                _uiState.value = _uiState.value.copy(
                    people = people,
                    isLoading = false,
                    error = null
                )
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoading = false,
                    error = e.message ?: "An error occurred"
                )
            }
        }
    }
    
    fun loadPlanet(homeworldUrl: String) {
        coroutineScope.launch {
            _uiState.value = _uiState.value.copy(
                isLoadingPlanet = true,
                planetError = null
            )
            
            try {
                val planet = api.getPlanet(homeworldUrl)
                _uiState.value = _uiState.value.copy(
                    selectedPlanet = planet,
                    isLoadingPlanet = false,
                    planetError = null,
                    showPlanetDetails = true
                )
            } catch (e: Exception) {
                _uiState.value = _uiState.value.copy(
                    isLoadingPlanet = false,
                    planetError = e.message ?: "Failed to load planet"
                )
            }
        }
    }
    
    fun closePlanetDetails() {
        _uiState.value = _uiState.value.copy(
            showPlanetDetails = false,
            selectedPlanet = null
        )
    }
}

data class StarWarsUiState(
    val people: List<Person> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null,
    val selectedPlanet: Planet? = null,
    val isLoadingPlanet: Boolean = false,
    val planetError: String? = null,
    val showPlanetDetails: Boolean = false
)
