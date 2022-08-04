package discover

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.domain.movies.observers.ObservePagedDiscovery
import com.example.domain.movies.observers.ObservePagedPopularMovies
import com.example.domain.users.iteractors.LogoutIteractor
import com.example.model.FilterParams
import com.example.model.Movie
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import org.threeten.bp.zone.ZoneRulesProvider.refresh
import util.AppCoroutineDispatchers
import javax.inject.Inject

@HiltViewModel
class DiscoverViewModel @Inject constructor(
    private val pagingInteractor: ObservePagedDiscovery,
    private val logoutIteractor: LogoutIteractor,
    private val dispatchers: AppCoroutineDispatchers
): ViewModel() {

    private val discoverParamsFilter = MutableStateFlow(FilterParams())

    fun fetchDiscover(discoveryParams: FilterParams) {
        discoverParamsFilter.tryEmit(discoveryParams)
    }
    fun applyFilters(filterParams: FilterParams) {
        discoverParamsFilter.tryEmit(filterParams)
    }

    val pagedList: Flow<PagingData<Movie>> =
        pagingInteractor.flow.cachedIn(viewModelScope)

    init {
        pagingInteractor(ObservePagedDiscovery.Params(discoverParamsFilter, PAGING_CONFIG))
    }

    fun logout() = viewModelScope.launch(dispatchers.io){
        logoutIteractor(LogoutIteractor.Params())
    }

    companion object {
        val PAGING_CONFIG = PagingConfig(
            pageSize = 20,
            initialLoadSize = 60,
        )
    }
}