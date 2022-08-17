package com.example.ui_people.lobby

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.domain.people.observers.ObservePagedPopularPeople
import com.example.domain.users.iteractors.LogoutIteractor
import com.example.model.Person
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import util.AppCoroutineDispatchers
import javax.inject.Inject

@HiltViewModel
class PeopleLobbyViewModel @Inject constructor(
    private val pagingInteractor: ObservePagedPopularPeople,
    private val logoutIteractor: LogoutIteractor,
    private val dispatchers: AppCoroutineDispatchers
) : ViewModel() {

    private val language = MutableStateFlow("en-US")

    val pagedList: Flow<PagingData<Person>> =
        pagingInteractor.flow.cachedIn(viewModelScope)

    init {
        pagingInteractor(ObservePagedPopularPeople.Params(language = language, pagingConfig = PAGING_CONFIG))
        loadData()
    }

    private fun loadData() {
        viewModelScope.launch(dispatchers.io) {
            language.collect()
        }
    }

    fun applyLanguage(language: String) {
        this.language.tryEmit(language)
    }

    fun logout()  = viewModelScope.launch(dispatchers.io) {
        logoutIteractor(LogoutIteractor.Params()).collect()
    }

    companion object {
        val PAGING_CONFIG = PagingConfig(
            pageSize = 20,
            initialLoadSize = 60,
        )
    }
}