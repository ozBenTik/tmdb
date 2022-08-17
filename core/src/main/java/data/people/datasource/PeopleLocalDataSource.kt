package com.example.core.data.people.datasource

import com.example.core.data.people.datasource.localstore.PersonDetailsStore
import com.example.core.data.people.datasource.localstore.PopularPeopleStore
import javax.inject.Inject

class PeopleLocalDataSource @Inject constructor(
    val popularPeopleStore: PopularPeopleStore,
    val personDetailsStore: PersonDetailsStore
)