package com.example.ui_people.details

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.people.iteractors.UpdatePersonCredits
import com.example.domain.people.iteractors.UpdatePersonDetails
import com.example.domain.people.observers.ObservePersonCredits
import com.example.domain.people.observers.ObservePersonDetails
import com.example.domain.users.iteractors.LogoutIteractor
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import util.*
import javax.inject.Inject

@HiltViewModel
class PersonDetailsViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val updatePersonDetails: UpdatePersonDetails,
    private val updatePersonCredits: UpdatePersonCredits,
    private val observePersonDetails: ObservePersonDetails,
    private val observePersonCredits: ObservePersonCredits,
    private val logoutIteractor: LogoutIteractor,
    private val dispatchers: AppCoroutineDispatchers
) : ViewModel() {

    private val personLoadingState = ObservableLoadingCounter()
    private val creditsLoadingState = ObservableLoadingCounter()
    private val uiMessageManager = UiMessageManager()

    val state: StateFlow<PersonDetailsState> = combine(
        observePersonDetails.flow,
        observePersonCredits.flow,
        personLoadingState.observable,
        creditsLoadingState.observable,
        uiMessageManager.message,
    ) { personExtended, credits, personLoading,creditsLoading, message ->

        PersonDetailsState(
            details = personExtended,
            credits = credits,
            detailsLoading = personLoading,
            creditsLoading = creditsLoading,
            message = message
        )

    }.stateIn(
        scope = viewModelScope,
        SharingStarted.WhileSubscribed(5000),
        PersonDetailsState.Empty
    )

    private val personId = savedStateHandle.get<Int>("person_id") ?: kotlin.run {
        viewModelScope.launch {
            uiMessageManager.emitMessage(UiMessage("Unknown Person id"))
        }
        0
    }

    init {
        observePersonDetails(ObservePersonDetails.Params(personId))
        observePersonCredits(ObservePersonCredits.Params(personId))
        loadDetails(personId)
    }

    private fun loadDetails(id: Int) {

        viewModelScope.launch(dispatchers.io) {
            updatePersonCredits(UpdatePersonDetails.Params(id))
                .collectStatus(
                    creditsLoadingState,
                    uiMessageManager
                )
        }

        viewModelScope.launch(dispatchers.io) {
            updatePersonDetails(UpdatePersonDetails.Params(id))
                .collectStatus(
                    personLoadingState,
                    uiMessageManager
                )
        }
    }

    fun logout() = viewModelScope.launch(dispatchers.io) {
        logoutIteractor(LogoutIteractor.Params()).collect()
    }
}