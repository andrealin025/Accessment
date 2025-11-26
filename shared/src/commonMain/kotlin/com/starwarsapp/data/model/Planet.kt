package com.starwarsapp.data.model

import kotlinx.serialization.Serializable

@Serializable
data class Planet(
    val name: String,
    val terrain: String,
    val gravity: String,
    val population: String
)

