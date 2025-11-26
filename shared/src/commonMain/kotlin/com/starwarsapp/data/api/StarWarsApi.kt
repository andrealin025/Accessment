package com.starwarsapp.data.api

import com.starwarsapp.data.model.PeopleResponse
import com.starwarsapp.data.model.Planet
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.*
import io.ktor.client.request.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.json.Json

interface StarWarsApi {
    suspend fun searchPeople(searchTerm: String, page: Int = 1): PeopleResponse
    suspend fun getPlanet(url: String): Planet
    suspend fun getAllPeople(searchTerm: String): List<com.starwarsapp.data.model.Person>
}

class StarWarsApiImpl(private val client: HttpClient) : StarWarsApi {
    
    override suspend fun searchPeople(searchTerm: String, page: Int): PeopleResponse {
        val url = if (page > 1) {
            // For pagination, we need to handle the next URL from previous response
            // But for the first call, we use the base URL with search parameter
            "https://swapi.dev/api/people/"
        } else {
            "https://swapi.dev/api/people/"
        }
        
        val response = client.get(url) {
            if (searchTerm.isNotBlank()) {
                parameter("search", searchTerm)
            }
            if (page > 1) {
                parameter("page", page)
            }
        }
        return response.body()
    }
    
    override suspend fun getPlanet(url: String): Planet {
        val response = client.get(url)
        return response.body()
    }
    
    override suspend fun getAllPeople(searchTerm: String): List<com.starwarsapp.data.model.Person> {
        val allPeople = mutableListOf<com.starwarsapp.data.model.Person>()
        var currentPage = 1
        var hasNext = true
        
        while (hasNext) {
            val response = searchPeople(searchTerm, currentPage)
            allPeople.addAll(response.results)
            
            hasNext = response.next != null
            currentPage++
        }
        
        return allPeople
    }
    
    companion object {
        fun create(): HttpClient {
            return HttpClient {
                install(ContentNegotiation) {
                    json(Json {
                        ignoreUnknownKeys = true
                        isLenient = true
                        encodeDefaults = false
                    })
                }
                install(Logging) {
                    level = LogLevel.INFO
                }
            }
        }
    }
}

