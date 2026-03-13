package com.gladiator.BasketBuddy.repo


import com.gladiator.BasketBuddy.model.User
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.tasks.await

class UserRepository {

    private val database = FirebaseDatabase.getInstance()
    private val usersRef = database.getReference("users")

    suspend fun loginUser(username: String, password: String): Result<User> {
        return try {

            val snapshot = usersRef.get().await()

            for (child in snapshot.children) {
                val user = child.getValue(User::class.java)

                if (user != null &&
                    user.username == username &&
                    user.password == password
                ) {
                    return Result.success(user)
                }
            }

            Result.failure(Exception("Invalid username or password"))

        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    // REGISTER USER
    suspend fun registerUser(user: User): Result<Boolean> {

        return try {

            val id = user.userId.toString()

            usersRef.child(id).setValue(user).await()

            Result.success(true)

        } catch (e: Exception) {

            Result.failure(e)

        }
    }
}