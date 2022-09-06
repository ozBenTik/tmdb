package upcoming

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.domain.movies.observers.paged.ObservePagedUpcomingMovies
import com.example.domain.users.iteractors.LogoutIteractor
import com.example.model.movie.Movie
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import util.AppCoroutineDispatchers
import javax.inject.Inject

@HiltViewModel
class UpcomingMoviesViewModel @Inject constructor(
    private val pagingInteractor: ObservePagedUpcomingMovies,
    private val logoutIteractor: LogoutIteractor,
    private val dispatchers: AppCoroutineDispatchers
): ViewModel() {
    val pagedList: Flow<PagingData<Movie>> =
        pagingInteractor.flow.cachedIn(viewModelScope)

    init {
        pagingInteractor(ObservePagedUpcomingMovies.Params(PAGING_CONFIG))
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