package toprated

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.domain.movies.observers.paged.ObservePagedTopRatedMovies
import com.example.domain.users.iteractors.LogoutIteractor
import com.example.model.movie.Movie
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import util.AppCoroutineDispatchers
import javax.inject.Inject

@HiltViewModel
class TopRatedMoviesViewModel @Inject constructor(
    private val pagingInteractor: ObservePagedTopRatedMovies,
    private val logoutIteractor: LogoutIteractor,
    private val dispatchers: AppCoroutineDispatchers
): ViewModel() {
    val pagedList: Flow<PagingData<Movie>> =
        pagingInteractor.flow.cachedIn(viewModelScope)

    init {
        pagingInteractor(ObservePagedTopRatedMovies.Params(PAGING_CONFIG))
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