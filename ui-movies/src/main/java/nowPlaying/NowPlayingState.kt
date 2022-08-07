package nowPlaying

import androidx.paging.PagingData
import com.example.model.Genre
import com.example.model.Movie
import util.UiMessage

data class NowPlayingState(
    val genres: List<Genre> = emptyList(),
    val genresRefreshing: Boolean = false,
    val message: UiMessage? = null,
    val filterGenres: List<Int> = listOf(),
    val pagedList: PagingData<Movie> = PagingData.empty(),
) {
    val refreshing: Boolean
        get() = genresRefreshing

    companion object {
        val Empty = NowPlayingState()
    }
}