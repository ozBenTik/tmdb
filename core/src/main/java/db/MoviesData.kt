package db

import androidx.room.ColumnInfo
import androidx.room.Entity
import com.example.model.Movie

@Entity(
    tableName = "popular_movies",
)
data class PopularMovies (
    @ColumnInfo(name = "page") val page: Int,
    @ColumnInfo(name = "movie_list") val movies: List<Movie>,
)

@Entity(
    tableName = "top_rated_movies",
)
data class TopRatedMovies (
    @ColumnInfo(name = "page") val page: Int,
    @ColumnInfo(name = "movie_list") val movies: List<Movie>,
)

@Entity(
    tableName = "now_playing_movies",
)
data class NowPlayingMovies (
    @ColumnInfo(name = "page") val page: Int,
    @ColumnInfo(name = "movie_list") val movies: List<Movie>,
)

@Entity(
    tableName = "upcoming_movies",
)
data class UpcomingMovies (
    @ColumnInfo(name = "page") val page: Int,
    @ColumnInfo(name = "movie_list") val movies: List<Movie>,
)

