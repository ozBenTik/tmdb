package com.example.core.data.login

//import android.content.Context
//import android.util.Log
//import android.widget.Toast
//import com.google.firebase.auth.FirebaseAuth
//import javax.inject.Inject

//class LoginRemoteDataSource @Inject constructor(
//
//) {
//    fun login(email: String, password: String, context: Context) {
//        FirebaseAuth.getInstance().let { auth ->
//            auth.signInWithEmailAndPassword(email, password)
//                .addOnCompleteListener(context) { task ->
//                    if (task.isSuccessful) {
//                        // Sign in success, update UI with the signed-in user's information
//                        Log.d(TAG, "signInWithEmail:success")
//                        val user = auth.currentUser
//                        updateUI(user)
//                    } else {
//                        // If sign in fails, display a message to the user.
//                        Log.w(TAG, "signInWithEmail:failure", task.exception)
//                        Toast.makeText(baseContext, "Authentication failed.",
//                            Toast.LENGTH_SHORT).show()
//                        updateUI(null)
//                    }
//                }
//        }
//    }
//}