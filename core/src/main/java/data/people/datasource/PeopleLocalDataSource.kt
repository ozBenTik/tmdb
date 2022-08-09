package com.example.core.data.people.datasource

import com.example.core.data.people.datasource.localstore.PPeopleStore
import javax.inject.Inject

class PeopleLocalDataSource @Inject constructor(
    val pPeopleStore: PPeopleStore,
)