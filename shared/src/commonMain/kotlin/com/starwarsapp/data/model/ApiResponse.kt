package com.starwarsapp.data.model

import kotlinx.serialization.Serializable

@Serializable
data class PeopleResponse(
    val count: Int,
    val next: String? = null,
    val previous: String? = null,
    val results: List<Person>
)

