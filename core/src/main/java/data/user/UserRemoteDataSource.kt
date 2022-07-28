package com.example.core.data.user

import com.google.firebase.auth.FirebaseAuth
import di.ApplicationScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.*
import timber.log.Timber
import util.AppCoroutineDispatchers
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRemoteDataSource @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    @ApplicationScope private val externalScope: CoroutineScope,
    private val dispatchers: AppCoroutineDispatchers,
) {

    private val basicUserInfo: SharedFlow<AuthenticatedUserInfoBasic?> =
        callbackFlow<FirebaseAuth> {
            val authStateListener: ((FirebaseAuth) -> Unit) = { auth ->
                // This callback gets always executed on the main thread because of Firebase
                trySend(auth)
            }
            firebaseAuth.addAuthStateListener(authStateListener)
            awaitClose { firebaseAuth.removeAuthStateListener(authStateListener) }
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

    fun login(email: String, password: String) = callbackFlow {

        val authStateListener: ((Boolean) -> Unit) = { auth ->
            // This callback gets always executed on the main thread because of Firebase
            trySend(auth)
        }

        firebaseAuth.signInWithEmailAndPassword(email, password).addOnCompleteListener {
            authStateListener(it.isSuccessful)
        }

        awaitClose {  }
    }

    fun logout() {
        firebaseAuth.signOut()
    }
}