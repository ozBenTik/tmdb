package db

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.model.Movie

@Entity(tableName = "popular_movies",)
data class PopularMovies (
    @PrimaryKey
    @ColumnInfo(name = "page") val page: Int,
    @ColumnInfo(name = "movie_list") val movies: List<Movie>,
)

@Entity(tableName = "top_rated_movies",)
data class TopRatedMovies (
    @PrimaryKey
    @ColumnInfo(name = "page") val page: Int,
    @ColumnInfo(name = "movie_list") val movies: List<Movie>,
)

@Entity(tableName = "now_playing_movies",)
data class NowPlayingMovies(
    @PrimaryKey
    @ColumnInfo(name = "page") val page: Int,
    @ColumnInfo(name = "movie_list") val movies: List<Movie>,
)

@Entity(tableName = "upcoming_movies",)
data class UpcomingMovies (
    @PrimaryKey
    @ColumnInfo(name = "page") val page: Int,
    @ColumnInfo(name = "movie_list") val movies: List<Movie>,
)

