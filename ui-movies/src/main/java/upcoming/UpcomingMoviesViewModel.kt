package upcoming

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.domain.movies.observers.ObservePagedUpcomingMovies
import com.example.domain.users.iteractors.SignoutIteractor
import com.example.model.Movie
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import util.AppCoroutineDispatchers
import javax.inject.Inject

@HiltViewModel
class UpcomingMoviesViewModel @Inject constructor(
    private val pagingInteractor: ObservePagedUpcomingMovies,
    private val signoutUpdate: SignoutIteractor,
    private val dispatchers: AppCoroutineDispatchers,
): ViewModel() {
    val pagedList: Flow<PagingData<Movie>> =
        pagingInteractor.flow.cachedIn(viewModelScope)

    init {
        pagingInteractor(ObservePagedUpcomingMovies.Params(PAGING_CONFIG))
    }

    fun signOut() {
        viewModelScope.launch(dispatchers.io) {
            signoutUpdate(SignoutIteractor.Params()).collect()
        }
    }

    companion object {
        val PAGING_CONFIG = PagingConfig(
            pageSize = 20,
            initialLoadSize = 60,
        )
    }
}