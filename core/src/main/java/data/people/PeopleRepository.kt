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

    fun observePopularPeople() = local.popularPeopleStore.observeEntries()

    fun getPopularPeople(page: Int, language: String) =
        flow {
            emit(remote.getPopularPeople(page, language))
        }

    fun savePopularPeople(page: Int, people: List<PopularActor>) {
        local.popularPeopleStore.insert(page, people)
    }
}