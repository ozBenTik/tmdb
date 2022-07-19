package upcoming

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.domain.movies.observers.ObservePagedUpcomingMovies
import com.example.model.Movie
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class UpcomingMoviesViewModel @Inject constructor(
    private val pagingInteractor: ObservePagedUpcomingMovies
): ViewModel() {
    val pagedList: Flow<PagingData<Movie>> =
        pagingInteractor.flow.cachedIn(viewModelScope)

    init {
        pagingInteractor(ObservePagedUpcomingMovies.Params(PAGING_CONFIG))
    }

    companion object {
        val PAGING_CONFIG = PagingConfig(
            pageSize = 20,
            initialLoadSize = 60,
        )
    }
}