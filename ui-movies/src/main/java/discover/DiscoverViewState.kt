package discover

import androidx.paging.PagingData
import com.example.model.FilterParams
import com.example.model.Movie
import util.UiMessage

data class DiscoverViewState(
    val movies: PagingData<Movie> = PagingData.empty(),
    val filterParams: FilterParams = FilterParams(),
    val moviesRefreshing: Boolean = false,
    val message: UiMessage? = null
) {
    val refreshing: Boolean
        get() = moviesRefreshing

    companion object {
        val Empty = DiscoverViewState()
    }
}