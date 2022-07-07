package db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.model.Movie
import kotlinx.coroutines.flow.Flow


@Dao
interface PopularDao {
    @Query("SELECT * FROM popular_movies")
    fun getAll(): Flow<List<Movie>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(popularMovies: PopularMovies)
}

@Dao
interface TopRatedDao {
    @Query("SELECT * FROM top_rated_movies")
    fun getAll(): Flow<List<Movie>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(topRatedMovies: TopRatedMovies)
}

@Dao
interface NowPlayingDao {
    @Query("SELECT * FROM now_playing_movies")
    fun getAll(): Flow<List<Movie>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(nowPlayingMovies: NowPlayingMovies)
}

@Dao
interface UpcomingDao {
    @Query("SELECT * FROM upcoming_movies")
    fun getAll(): Flow<List<Movie>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(upcomingMovies: UpcomingMovies)
}
