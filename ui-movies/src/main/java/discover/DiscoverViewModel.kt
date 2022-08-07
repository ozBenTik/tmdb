package discover

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.domain.movies.observers.ObservePagedDiscovery
import com.example.domain.users.iteractors.LogoutIteractor
import com.example.model.FilterParams
import com.example.model.Movie
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import util.AppCoroutineDispatchers
import util.ObservableLoadingCounter
import util.UiMessageManager
import java.util.logging.Filter
import javax.inject.Inject

@HiltViewModel
class DiscoverViewModel @Inject constructor(
    private val pagingInteractor: ObservePagedDiscovery,
    private val logoutIteractor: LogoutIteractor,
    private val dispatchers: AppCoroutineDispatchers
) : ViewModel() {

    val discoverParamsFilter = MutableStateFlow(FilterParams())

    init {
        pagingInteractor(ObservePagedDiscovery.Params(discoverParamsFilter, PAGING_CONFIG))
        loadData()
    }

    private fun loadData() {
        viewModelScope.launch(dispatchers.io) {
            discoverParamsFilter.collect()
        }
    }

    fun applyFilters(filterParams: FilterParams) {
        discoverParamsFilter.tryEmit(filterParams)
    }

    fun requireParams(onParamsRequired: (params: FilterParams)->Unit) {
        onParamsRequired(discoverParamsFilter.value)
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