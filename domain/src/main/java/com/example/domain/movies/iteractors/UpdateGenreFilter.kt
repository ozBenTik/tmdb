package com.example.domain.movies.iteractors
import com.example.domain.FlowInteractor
import com.example.model.Movie
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import result.Result
import util.AppCoroutineDispatchers
import javax.inject.Inject

class UpdateGenreFilter @Inject constructor(
    dispatchers: AppCoroutineDispatchers,
) : FlowInteractor<UpdateGenreFilter.Params, List<Movie>>(dispatchers.computation) {

    override suspend fun doWork(params: Params): Flow<Result<List<Movie>>> {
        return flow {
            params.movies.takeIf { params.genreIds.isNotEmpty() }?.filter {
                params.genreIds.containsAll(it.genreList)
            } ?: params.movies
        }
    }

    data class Params(val genreIds: List<Int>, val movies: List<Movie>)
}