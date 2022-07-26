//package com.example.domain.movies.iteractors
//
//import com.example.core.data.login.LoginHandler
//import com.example.core.data.login.LoginState
//import com.example.domain.FlowInteractor
//import com.example.domain.SubjectInteractor
//import com.example.model.CreditsResponse
//import data.movies.MoviesRepository
//import kotlinx.coroutines.flow.Flow
//import kotlinx.coroutines.flow.map
//import result.Result
//import util.AppCoroutineDispatchers
//import javax.inject.Inject
//
//class LoginUpdate @Inject constructor(
//    private val loginHandler: LoginHandler,
//    dispatchers: AppCoroutineDispatchers,
//) : SubjectInteractor<UpdateCredits.Params, LoginState>() {
//
//    override fun createObservable(params: UpdateCredits.Params): Flow<LoginState> {
//        return loginHandler.observeState()
//    }
//}