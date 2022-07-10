package db


//@Dao
//interface PopularDao {
//    @Query("SELECT * FROM popular_movies")
//    fun getAll(): Flow<List<Movie>>
//
//    @Query("DELETE FROM popular_movies")
//    suspend fun clearAll()
//
//    @Insert(onConflict = OnConflictStrategy.REPLACE)
//    suspend fun insertAll(popularMovies: PopularMovies)
//}
//
//@Dao
//interface TopRatedDao {
//    @Query("SELECT * FROM top_rated_movies")
//    fun getAll(): Flow<List<Movie>>
//
//    @Query("DELETE FROM top_rated_movies")
//    suspend fun clearAll()
//
//    @Insert(onConflict = OnConflictStrategy.REPLACE)
//    suspend fun insertAll(topRatedMovies: TopRatedMovies)
//}
//
//@Dao
//interface NowPlayingDao {
//    @Query("SELECT * FROM now_playing_movies")
//    fun getAll(): Flow<List<Movie>>
//
//    @Query("DELETE FROM now_playing_movies")
//    suspend fun clearAll()
//
//    @Insert(onConflict = OnConflictStrategy.REPLACE)
//    suspend fun insertAll(nowPlayingMovies: NowPlayingMovies)
//}
//
//@Dao
//interface UpcomingDao {
//    @Query("SELECT * FROM upcoming_movies")
//    fun getAll(): Flow<List<Movie>>
//
//    @Query("DELETE FROM upcoming_movies")
//    suspend fun clearAll()
//
//    @Insert(onConflict = OnConflictStrategy.REPLACE)
//    suspend fun insertAll(upcomingMovies: UpcomingMovies)
//}
