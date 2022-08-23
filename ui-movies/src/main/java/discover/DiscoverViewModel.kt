package discover

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.core.data.movies.datasource.localstore.MoviesStore
import com.example.domain.movies.observers.ObservePagedDiscovery
import com.example.domain.users.iteractors.LogoutIteractor
import com.example.model.movie.FilterParams
import com.example.model.movie.Movie
import dagger.hilt.android.lifecycle.HiltViewModel
import di.Discovery
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import util.AppCoroutineDispatchers
import javax.inject.Inject

@HiltViewModel
class DiscoverViewModel @Inject constructor(
    @Discovery val store: MoviesStore,
    private val pagingInteractor: ObservePagedDiscovery,
    private val logoutIteractor: LogoutIteractor,
    private val dispatchers: AppCoroutineDispatchers
) : ViewModel() {

    private val discoverFilters = MutableStateFlow(value = FilterParams())

    init {
        pagingInteractor(ObservePagedDiscovery.Params(discoverFilters, PAGING_CONFIG))
        loadData()
    }

    private fun loadData() {
        viewModelScope.launch(dispatchers.io) {
            discoverFilters.collect()
        }
    }

    fun applyFilters(filterParams: FilterParams) {
        discoverFilters.tryEmit(
            FilterParams(
                filterParams.language,
                filterParams.release_dateFrom,
                filterParams.release_dateTo,
                filterParams.genres
            )
        )
    }

    fun requireFilters(onParamsRequired: (params: FilterParams) -> Unit) {
        onParamsRequired(discoverFilters.value)
    }

    val pagedList: Flow<PagingData<Movie>> =
        pagingInteractor.flow.cachedIn(viewModelScope)


    fun logout() = viewModelScope.launch(dispatchers.io) {
        logoutIteractor(LogoutIteractor.Params())
    }

    companion object {
        val PAGING_CONFIG = PagingConfig(
            pageSize = 20,
            initialLoadSize = 60,
        )
    }
}