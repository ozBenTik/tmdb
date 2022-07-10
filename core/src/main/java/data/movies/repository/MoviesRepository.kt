package data.movies.repository

import data.movies.datasource.MoviesLocalDataSource
import data.movies.datasource.MoviesRemoteDataSource
import javax.inject.Inject

class MoviesRepository @Inject constructor(
    private val localDataSource: MoviesLocalDataSource,
    private val remoteDataSource: MoviesRemoteDataSource
) {

//    fun forceRefresh(): Flow<Movie>
//    fun getNextPage(page: Int)

}