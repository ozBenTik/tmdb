package com.example.core.data.user

import android.app.Activity
import android.util.Log
import androidx.annotation.NonNull
import com.example.model.Movie
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.Task
import com.google.android.gms.tasks.Tasks
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import di.ApplicationScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.*
import result.Result
import timber.log.Timber
import util.AppCoroutineDispatchers
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRemoteDataSource @Inject constructor(
    private val firebaseAuth: FirebaseAuth,
    private val firebaseDB: FirebaseDatabase,
    @ApplicationScope private val externalScope: CoroutineScope,
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

    fun updateImageUrl(imageUrl: String) {
        firebaseAuth.currentUser?.let {
            val userRef = firebaseDB.reference
                .child("users")
                .child(it.uid)

            userRef.child("imageUrl").setValue(imageUrl)
        }
    }

    fun updateDisplayName(displayName: String) {
        firebaseAuth.currentUser?.let {
            val userRef = firebaseDB.reference
                .child("users")
                .child(it.uid)
            userRef.child("displayName").setValue(displayName)
        }
    }

    fun addFavorite(movieId: Int) = callbackFlow {

        val callback: (result: Result<Boolean>) ->  Unit = { res ->
            trySend(res)
        }

        val ref = firebaseAuth.currentUser?.let {
            firebaseDB.reference
                .child("favorites")
                .child(it.uid)
                .child("$movieId")
        }

        val onCompleteListener = OnCompleteListener<Void>{
            callback(Result.Success(it.isSuccessful))
        }

        val onFailureListener = OnFailureListener {
            callback(Result.Error(it))
        }

        ref
            ?.setValue(true)
            ?.addOnFailureListener(onFailureListener)
            ?.addOnCompleteListener(onCompleteListener)

        awaitClose {  }
    }

    fun removeFavorite(movieId: Int) = callbackFlow{

        val callback: (result: Result<Boolean>) ->  Unit = { res ->
            trySend(res)
        }

        val ref = firebaseAuth.currentUser?.let {
            firebaseDB.reference
                .child("favorites")
                .child(it.uid)
                .child("$movieId")
        }

        val onCompleteListener = OnCompleteListener<Void>{
            callback(Result.Success(it.isSuccessful))
        }

        ref
            ?.removeValue()
            ?.addOnCompleteListener(onCompleteListener)

        awaitClose {  }

    }

    fun observeFavorites() = callbackFlow {

        val callback: (result: List<Int>) ->  Unit = { res ->
            trySend(res)
        }

        val ref = firebaseAuth.currentUser?.let {
            firebaseDB.reference
                .child("favorites")
                .child(it.uid)
        }

        val listener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val favs = mutableListOf<Int>()
                (snapshot.value as? Map<String, Boolean>)?.forEach { map ->
                    favs.add(map.key.toInt())
                }
                callback(favs)
            }

            override fun onCancelled(error: DatabaseError) {}
        }

        ref?.addValueEventListener(listener)

        awaitClose { ref?.removeEventListener(listener) }
    }

    fun logout() {
        firebaseAuth.signOut()
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
}