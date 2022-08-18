package com.example.model

data class PersonExtended(
    val popular: PopularPerson = PopularPerson.Empty,
    val personal: PersonDetails = PersonDetails.Empty
)