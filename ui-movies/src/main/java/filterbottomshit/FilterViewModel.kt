package filterbottomshit

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.movies.iteractors.UpdateGenres
import com.example.domain.movies.observers.ObserveGenres
import com.example.model.FilterKey
import com.example.model.FilterParams
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import util.AppCoroutineDispatchers
import java.util.*
import javax.inject.Inject

@HiltViewModel
class FilterViewModel @Inject constructor(
    val observeGenres: ObserveGenres,
    private val updateGenres: UpdateGenres,
    private val dispatchers: AppCoroutineDispatchers
) : ViewModel() {

    private var filterParams = FilterParams()

    val genres = observeGenres.flow

    init {
        observeGenres(ObserveGenres.Params())
        loadData()
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

    private fun loadData() {
        viewModelScope.launch(dispatchers.io) {
            updateGenres(UpdateGenres.Params()).collect()
        }
    }
}