package com.gladiator.BasketBuddy.viewmodel


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gladiator.BasketBuddy.model.Group
import com.gladiator.BasketBuddy.repo.GroupRepository
import com.gladiator.BasketBuddy.repo.UserSession
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class HomeViewModel(
    private val repository: GroupRepository = GroupRepository()
) : ViewModel() {

    private val _message = MutableStateFlow<String?>(null)
    val message: StateFlow<String?> = _message

    fun createGroup(
        groupName: String,
        groupCode: String,
        onSuccess: () -> Unit
    ) {
        val ownerId = UserSession.currentUserId

        if (groupName.isBlank()) {
            _message.value = "Enter a group name"
            return
        }

        if (groupCode.isBlank()) {
            _message.value = "Generate a group code first"
            return
        }

        if (ownerId == null) {
            _message.value = "Login again to continue"
            return
        }

        viewModelScope.launch {

            val group = Group(
                groupCode = groupCode,
                groupName = groupName,
                ownerId = ownerId
            )

            val result = repository.createGroup(group)

            result.onSuccess {
                _message.value = null
                onSuccess()
            }

            result.onFailure {
                _message.value = it.message ?: "Unable to create group"
            }

        }
    }

    fun joinGroup(groupCode: String, onSuccess: () -> Unit) {
        val userId = UserSession.currentUserId

        if (groupCode.isBlank()) {
            _message.value = "Enter a group code"
            return
        }

        if (userId == null) {
            _message.value = "Login again to continue"
            return
        }

        viewModelScope.launch {
            val result = repository.joinGroup(groupCode.trim(), userId)

            result.onSuccess {
                _message.value = null
                onSuccess()
            }

            result.onFailure {
                _message.value = it.message ?: "Unable to join group"
            }
        }
    }

    fun clearMessage() {
        _message.value = null
    }
}