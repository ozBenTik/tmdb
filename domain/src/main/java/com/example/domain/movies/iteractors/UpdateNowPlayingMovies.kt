package com.example.domain.movies.iteractors
import com.example.domain.FlowInteractor
import com.example.model.MovieResponse
import data.movies.MoviesRepository
import data.movies.MoviesStore
import di.NowPlaying
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.onEach
import result.Result
import timber.log.Timber
import util.AppCoroutineDispatchers
import javax.inject.Inject

class UpdateNowPlayingMovies @Inject constructor(
    private val moviesRepository: MoviesRepository,
    @NowPlaying val nowPlayingStore: MoviesStore,
    dispatchers: AppCoroutineDispatchers,
) : FlowInteractor<UpdateNowPlayingMovies.Params, MovieResponse>(dispatchers.io) {

    override suspend fun doWork(params: Params): Flow<Result<MovieResponse>> {
        val page = when {
            params.page >= 1 -> params.page
            params.page == Page.NEXT_PAGE -> {
                val lastPage = nowPlayingStore.getLastPage()
                lastPage + 1
            }
            else -> 1
        }

        return moviesRepository.getNowPlayingMovies(page)
            .onEach { result ->
                when (result) {
                    is Result.Error -> Timber.e(result.exception)
                    is Result.Success -> moviesRepository.saveNowPlayingMovies(
                        result.data.page,
                        result.data.movieList
                    )
                }
            }
    }

    data class Params(val page: Int)

    object Page {
        const val NEXT_PAGE = -1
        const val REFRESH = -2
    }
}