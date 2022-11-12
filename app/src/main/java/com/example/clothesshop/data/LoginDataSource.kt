package com.example.clothesshop.data


import com.example.clothesshop.model.LoggedInUser
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.delay
import java.io.IOException

/**
 * Class that handles authentication w/ login credentials and retrieves user information.
 */
class LoginDataSource {
    private lateinit var auth: FirebaseAuth
    suspend  fun  login(username: String, password: String): Result<LoggedInUser> {
        try {
            auth = Firebase.auth
            auth.signInWithEmailAndPassword(username,password)
            delay(1000L)
            val user= auth.currentUser
            if(user != null) {
                return Result.Success(
                    LoggedInUser(
                        user.uid,
                        username
                    )
                )
            }else
            {
                return Result.Error(IOException("Error logging in"))
            }
        } catch (e: Throwable) {
            return Result.Error(IOException("Error logging in", e))
        }
    }

    fun logout() {
        // TODO: revoke authentication
    }
}