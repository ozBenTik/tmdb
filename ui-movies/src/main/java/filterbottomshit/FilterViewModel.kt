package filterbottomshit

import androidx.lifecycle.ViewModel
import com.example.domain.movies.iteractors.UpdateGenres
import com.example.domain.movies.observers.ObserveGenres
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
    val observeGenres: ObserveGenres,
    private val updateGenres: UpdateGenres,
    private val dispatchers: AppCoroutineDispatchers
) : ViewModel() {

    var filterParams = FilterParams()
    set(value) {
        applyFilter(FilterKey.LANGUAGE, filterParams.language)
        applyFilter(FilterKey.RELEASE_DATE_FROM, filterParams.release_dateFrom)
        applyFilter(FilterKey.RELEASE_DATE_TO, filterParams.release_dateTo)
        applyFilter(FilterKey.GENRES, filterParams.genres)
        field = value
    }

    var genres: Flow<List<Genre>?> = flow {
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

//    fun setParams(filterParams: FilterParams) {
//        this.filterParams.apply {
//            applyFilter(FilterKey.LANGUAGE, filterParams.language)
//            applyFilter(FilterKey.RELEASE_DATE_FROM, filterParams.release_dateFrom)
//            applyFilter(FilterKey.RELEASE_DATE_TO, filterParams.release_dateTo)
//            applyFilter(FilterKey.GENRES, filterParams.genres)
//        }
//    }
}