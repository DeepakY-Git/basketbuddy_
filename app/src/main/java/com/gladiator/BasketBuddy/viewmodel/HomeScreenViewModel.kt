package com.gladiator.BasketBuddy.viewmodel


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gladiator.BasketBuddy.model.Group
import com.gladiator.BasketBuddy.repo.GroupRepository
import kotlinx.coroutines.launch

class HomeViewModel(
    private val repository: GroupRepository = GroupRepository()
) : ViewModel() {

    fun createGroup(
        groupName: String,
        groupCode: String,
        ownerId: Int,
        onSuccess: () -> Unit
    ) {

        viewModelScope.launch {

            val group = Group(
                groupCode = groupCode,
                groupName = groupName,
                ownerId = ownerId
            )

            val result = repository.createGroup(group)

            result.onSuccess {
                onSuccess()
            }

        }
    }
}