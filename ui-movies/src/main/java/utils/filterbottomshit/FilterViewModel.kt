package utils.filterbottomshit

import androidx.lifecycle.ViewModel
import com.example.domain.movies.iteractors.UpdateGenres
import com.example.model.FilterKey
import com.example.model.FilterParams
import com.example.model.Genre
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import result.data
import util.AppCoroutineDispatchers
import javax.inject.Inject

@HiltViewModel
class FilterViewModel @Inject constructor(
    private val updateGenres: UpdateGenres,
    private val dispatchers: AppCoroutineDispatchers
) : ViewModel() {

    var filterParams: FilterParams = FilterParams()

    val genres: Flow<List<Genre>?> = flow {
        updateGenres(UpdateGenres.Params()).map { result ->
            emit(result.data?.genreList)
        }.collect()
    }.flowOn(dispatchers.io)

    fun addFilter(filterKey: FilterKey, value: Any) {
        filterParams.addFilter(filterKey, value)
    }

    fun removeFilter(filterKey: FilterKey, value: Any) {
        filterParams.removeFilter(filterKey, value)
    }
}