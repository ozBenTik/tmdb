package filterbottomshit

import androidx.lifecycle.ViewModel
import com.example.domain.movies.iteractors.UpdateGenres
import com.example.domain.movies.observers.ObserveGenres
import com.example.model.FilterKey
import com.example.model.FilterParams
import com.example.model.Genre
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import result.data
import util.AppCoroutineDispatchers
import javax.inject.Inject

@HiltViewModel
class FilterViewModel @Inject constructor(
    val observeGenres: ObserveGenres,
    private val updateGenres: UpdateGenres,
    private val dispatchers: AppCoroutineDispatchers
) : ViewModel() {

    private var filterParams = FilterParams()

    var genres : Flow<List<Genre>?> = flow {
        updateGenres(UpdateGenres.Params()).map { result ->
            emit(result.data?.genreList)
        }.collect()
    }

    init {
        observeGenres(ObserveGenres.Params())
    }

    fun applyFilter(filterKey: FilterKey, value: Any) {
        filterParams.addFilter(filterKey, value)
    }

    fun removeFilter(filterKey: FilterKey, value: Any) {
        filterParams.removeFilter(filterKey, value)
    }

    fun retrieveParams(): FilterParams {
        return filterParams
    }

    fun setParams(filterParams: FilterParams) {
        this.filterParams = filterParams.copy()
    }
}