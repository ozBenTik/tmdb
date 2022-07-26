package com.example.core.data.user

import com.example.core.data.user.AuthenticatedUserInfoBasic
import com.example.core.data.user.FirebaseUserInfo
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.*
import timber.log.Timber
import javax.inject.Inject
import di.ApplicationScope
import util.AppCoroutineDispatchers
import javax.inject.Singleton

@Singleton
class UserRemoteDataSource @Inject constructor(
    private val firebase: FirebaseAuth,
    @ApplicationScope private val externalScope: CoroutineScope,
    private val dispatchers: AppCoroutineDispatchers,
) {

    private val basicUserInfo: SharedFlow<AuthenticatedUserInfoBasic?> =
        callbackFlow<FirebaseAuth> {
            val authStateListener: ((FirebaseAuth) -> Unit) = { auth ->
                // This callback gets always executed on the main thread because of Firebase
                trySend(auth)
            }
            firebase.addAuthStateListener(authStateListener)
            awaitClose { firebase.removeAuthStateListener(authStateListener) }
        }
            .map { authState ->
                // This map gets executed in the Flow's context
                processAuthState(authState)
            }
            .shareIn(
                scope = externalScope,
                replay = 1,
                started = SharingStarted.WhileSubscribed()
            )

    fun getBasicUserInfo(): Flow<AuthenticatedUserInfoBasic?> = basicUserInfo

    private suspend fun processAuthState(auth: FirebaseAuth): AuthenticatedUserInfoBasic? {
        // Listener that saves the [FirebaseUser], fetches the ID token
        // and updates the user ID observable.
        Timber.d("Received a FirebaseAuth update.")

        if (auth.currentUser == null) {
            return FirebaseUserInfo(null)
        }

        // Send the current user for observers
        return FirebaseUserInfo(auth.currentUser)
    }
}