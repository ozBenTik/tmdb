package com.example.ui_login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.users.iteractors.LoginIteractor
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import timber.log.Timber
import util.AppCoroutineDispatchers
import util.ObservableLoadingCounter
import util.UiMessageManager
import util.collectStatus
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor() :ViewModel() {

    sealed class LoginResult {
        data class SUCCESS(val user: FirebaseUser?) : LoginResult()
        data class FAILURE(val exception: Exception?) : LoginResult()
    }

    val loginState = MutableSharedFlow<LoginResult>(replay = 1)

    fun login(email: String, password: String) = viewModelScope.launch {
        FirebaseAuth.getInstance().signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                viewModelScope.launch {
                    if (task.isSuccessful) {
                        Timber.i("signInWithEmail:success")
                        loginState.emit(LoginResult.SUCCESS(task.result.user))
                    } else {
                        Timber.e("signInWithEmail:failure", task.exception)
                        loginState.emit(LoginResult.FAILURE(task.exception))
                    }
                }
            }
    }
}