package com.gladiator.BasketBuddy.repo

import com.gladiator.BasketBuddy.model.User
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

object UserSession {

    private val _currentUser = MutableStateFlow<User?>(null)
    val currentUser: StateFlow<User?> = _currentUser

    val currentUserId: Int?
        get() = _currentUser.value?.userId

    fun setCurrentUser(user: User) {
        _currentUser.value = user
    }

    fun clear() {
        _currentUser.value = null
    }
}