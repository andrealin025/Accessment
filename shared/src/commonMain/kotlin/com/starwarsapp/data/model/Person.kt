package com.starwarsapp.data.model

import kotlinx.serialization.Serializable

@Serializable
data class Person(
    val name: String,
    val homeworld: String
)

