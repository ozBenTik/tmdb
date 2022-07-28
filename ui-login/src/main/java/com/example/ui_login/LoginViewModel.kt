package com.example.ui_login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.users.iteractors.LoginIteractor
import com.example.domain.users.iteractors.LogoutIteractor
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import result.data
import util.AppCoroutineDispatchers
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    val loginIteractor: LoginIteractor,
    private val dispatchers: AppCoroutineDispatchers,
    ): ViewModel() {

    val loginState = MutableSharedFlow<Boolean>(replay = 1)

    fun login(email: String, password: String) = viewModelScope.launch(dispatchers.io) {
        loginIteractor(LoginIteractor.Params(email, password)).collect {
            it.data?.let { loggedIn -> loginState.tryEmit(loggedIn) }
        }
    }
}