package details
import com.example.model.PersonMoviePart
import com.example.model.Movie
import util.UiMessage

data class DetailsViewState(
    val movieDetails: Pair<Movie, Boolean>? = null,
    val movieRefreshing: Boolean = false,
    val recommendations: List<Movie> = emptyList(),
    val recommendationsRefreshing: Boolean = false,
    val actors: List<PersonMoviePart> = emptyList(),
    val actorsRefreshing: Boolean = false,
    val message: UiMessage? = null
) {
    val refreshing: Boolean
        get() = movieRefreshing || recommendationsRefreshing || actorsRefreshing

    companion object {
        val Empty = DetailsViewState()
    }
}