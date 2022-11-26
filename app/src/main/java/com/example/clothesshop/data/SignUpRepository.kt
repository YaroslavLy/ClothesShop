package com.example.clothesshop.data

import android.util.Log
import android.widget.Toast
import com.example.clothesshop.model.Category
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch


class SignUpRepository {
    private lateinit var auth: FirebaseAuth

     fun signUp(username: String, password: String): Flow<Boolean> = callbackFlow {
        auth = Firebase.auth

        auth.createUserWithEmailAndPassword(username, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.d("TAGGG", "createUserWithEmail:success")
                    //send email verification

                    if(auth.currentUser != null) {
                        auth.currentUser!!.sendEmailVerification()
                            .addOnSuccessListener {
                                Log.d("TAGGG", "Instructions Sent")
                                Firebase.auth.signOut()
                                trySend(true).isSuccess
                            }
                            .addOnFailureListener { e ->
                                //Toast.makeText(context, "Failed to send due to " + e.message, Toast.LENGTH_SHORT).show()
                            }
                    }
                    Firebase.auth.signOut()
                } else {
                    Log.w("TAGGG", "createUserWithEmail:failure", task.exception)
                    trySend(false).isFailure
                }
            }


//        else
//        {
//            delay(5000L)
//            if(auth.currentUser != null) {
//                auth.currentUser!!.sendEmailVerification()
//                    .addOnSuccessListener {
//                        Log.d("TAGGG", "Instructions Sent")
//                    }
//                    .addOnFailureListener { e ->
//                        //Toast.makeText(context, "Failed to send due to " + e.message, Toast.LENGTH_SHORT).show()
//                    }
//            }
//        }


        //auth.signInWithEmailAndPassword(username,password)
//        delay(1000L)
//        for (i in (1..160)) {
//            //var user = auth.currentUser
//            delay(1000L)
//            if (auth.currentUser != null) {
//                if (auth.currentUser!!.isEmailVerified()) {
//                    Log.i("212121", "djdjdjdj")
//                    return true
//                } else {
//                    Log.i("212121", "33333333333")
//                    Log.i("", auth.currentUser!!.email.toString())
//                }
//            }
//        }

         awaitClose {
             close()
         }
    }
}