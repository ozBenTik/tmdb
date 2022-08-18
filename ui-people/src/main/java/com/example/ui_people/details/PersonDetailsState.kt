package com.example.ui_people.details

import com.example.model.PersonDetails
import com.example.model.PersonExtended
import util.UiMessage

data class PersonDetailsState (
    val details: PersonExtended = PersonExtended(),
    val detailsLoading: Boolean = false,
    val message: UiMessage? = null
){
    val refreshing: Boolean
        get() = detailsLoading

    companion object {
        val Empty = PersonDetailsState()
    }
}