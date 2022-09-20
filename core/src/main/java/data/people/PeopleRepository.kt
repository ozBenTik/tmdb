package com.example.core.data.people

import com.example.core.data.people.datasource.PeopleLocalDataSource
import com.example.core.data.people.datasource.PeopleRemoteDataSource
import com.example.model.person.PersonCreditsResponse
import com.example.model.person.PersonDetails
import com.example.model.person.PopularPerson
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class PeopleRepository @Inject constructor(
    private val remote: PeopleRemoteDataSource,
    private val local: PeopleLocalDataSource
){

    //------------ Popular capabilities ------------

    fun getPopularPeople(page: Int, language: String) =
        flow {
            emit(remote.getPopularPeople(page, language))
        }

    fun savePopularPeople(page: Int, people: List<PopularPerson>) {
        local.popularPeopleStore.insert(page, people)
    }

    fun getCachedPersonById(personId: Int) =
        local.popularPeopleStore.getPersonById(personId)

    //------------ Person capabilities ------------

    fun observePersonDetails() = local.personDetailsStore.observeEntries()

    fun getPersonDetails(personId: Int) =
        flow {
            emit(remote.getPersonDetails(personId))
        }

    fun savePersonDetails(personId: Int, personDetails: PersonDetails) =
        local.personDetailsStore.insert(personId, personDetails)

    //------------ Person Credits ------------

    fun observePersonCredits() = local.personCreditsStore.observeEntries()

    fun getPersonCombinedCredits(personId: Int) =
        flow {
            emit(remote.getPersonCombinedCredits(personId))
        }

//    fun getPersonTVCredits(personId: Int) =
//        flow {
//            emit(remote.getPersonTVCredits(personId))
//        }

    fun savePersonCredits(personId: Int, personCredits: PersonCreditsResponse) =
        local.personCreditsStore.insert(personId, personCredits)
}