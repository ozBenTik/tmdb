package com.example.ui_people.details

import androidx.lifecycle.ViewModel
import com.example.domain.people.iteractors.UpdatePersonDetails
import com.example.domain.people.observers.ObservePersonDetails
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PersonViewModel @Inject constructor(
    private val updatePersonDetails: UpdatePersonDetails,
    private val observePersonDetails: ObservePersonDetails,
): ViewModel() {

}