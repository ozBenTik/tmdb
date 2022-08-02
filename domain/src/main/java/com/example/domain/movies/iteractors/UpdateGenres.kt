package com.example.domain.movies.iteractors

import androidx.paging.flatMap
import androidx.paging.map
import com.example.core.data.movies.datasource.localstore.GenresStore
import com.example.domain.FlowInteractor
import com.example.domain.movies.observers.ObservePagedNowPlayingMovies
import com.example.model.Genre
import com.example.model.GenreResponse
import data.movies.MoviesRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.onEach
import result.Result
import result.data
import timber.log.Timber
import util.AppCoroutineDispatchers
import javax.inject.Inject

class UpdateGenres @Inject constructor(
    private val moviesRepository: MoviesRepository,
    dispatchers: AppCoroutineDispatchers,
) : FlowInteractor<UpdateGenres.Params, GenreResponse>(dispatchers.io) {

    override suspend fun doWork(params: Params): Flow<Result<GenreResponse>> {
        return moviesRepository.getGenres()
            .onEach { result ->
                when (result) {
                    is Result.Error -> Timber.e(result.exception)
                    is Result.Success -> moviesRepository.saveGenres(
                        result.data.genreList
                    )
                }
            }
    }

    class Params()
}