package com.example.ui_people.details

import com.example.model.person.PersonCreditsResponse
import com.example.model.person.PersonExtended
import util.UiMessage

data class PersonDetailsState(
    val details: PersonExtended = PersonExtended(),
    val credits: PersonCreditsResponse = PersonCreditsResponse(),
    val detailsLoading: Boolean = false,
    val creditsLoading: Boolean = false,
    val message: UiMessage? = null
){
    val refreshing: Boolean
        get() = detailsLoading

    companion object {
        val Empty = PersonDetailsState()
    }
}