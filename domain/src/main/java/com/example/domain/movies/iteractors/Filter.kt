package com.example.domain.movies.iteractors
import com.example.domain.FlowInteractor
import com.example.model.FilterParams
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import result.Result
import util.AppCoroutineDispatchers
import javax.inject.Inject

class Filter @Inject constructor(
    dispatchers: AppCoroutineDispatchers,
) : FlowInteractor<Filter.Params, FilterParams>(dispatchers.io) {

    private var filter: FilterParams = FilterParams()

    override suspend fun doWork(params: Params): Flow<Result<FilterParams>> {
        return flow {
            emit(Result.Success(filter))
        }
    }

    data class Params(val filter: FilterParams)
}