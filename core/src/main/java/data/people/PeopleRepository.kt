package com.example.core.data.people

import com.example.core.data.people.datasource.PeopleLocalDataSource
import com.example.core.data.people.datasource.PeopleRemoteDataSource
import com.example.model.PopularActor
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class PeopleRepository @Inject constructor(
    private val remote: PeopleRemoteDataSource,
    private val local: PeopleLocalDataSource
){

    //------------ Popular capabilities ------------

    fun observePopularPeople() = local.pPeopleStore.observeEntries()

    fun getPopularPeople(page: Int, language: String) =
        flow {
            emit(remote.getPPeople(page, language))
        }

    fun savePopularPeople(page: Int, people: List<PopularActor>) {
        local.pPeopleStore.insert(page, people)
    }
}