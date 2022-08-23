package com.example.model.person

data class PersonExtended(
    val popular: PopularPerson = PopularPerson.Empty,
    val personal: PersonDetails = PersonDetails.Empty
)