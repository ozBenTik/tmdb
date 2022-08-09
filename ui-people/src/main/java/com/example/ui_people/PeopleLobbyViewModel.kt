package com.example.ui_people

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.bumptech.glide.Glide.init
import com.example.domain.movies.observers.ObservePagedTopRatedMovies
import com.example.domain.people.observers.ObservePagedPPeople
import com.example.domain.users.iteractors.LogoutIteractor
import com.example.model.FilterParams
import com.example.model.Movie
import com.example.model.PopularActor
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import org.intellij.lang.annotations.Language
import util.AppCoroutineDispatchers
import javax.inject.Inject

@HiltViewModel
class PeopleLobbyViewModel @Inject constructor(
    private val pagingInteractor: ObservePagedPPeople,
    private val logoutIteractor: LogoutIteractor,
    private val dispatchers: AppCoroutineDispatchers
) : ViewModel() {

    private val language = MutableStateFlow("en-US")

    init {
        pagingInteractor(ObservePagedPPeople.Params(language = language, pagingConfig = PAGING_CONFIG))
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