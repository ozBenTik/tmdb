package com.example.ui_login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.model.Movie
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.currentCoroutineContext
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import timber.log.Timber
import java.lang.Exception

sealed class LoginResult {
    data class SUCCESS(val user: FirebaseUser?) : LoginResult()
    data class FAILURE(val exception: Exception?) : LoginResult()
}

class LoginViewModel: ViewModel() {

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