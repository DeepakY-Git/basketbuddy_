//package com.gladiator.BasketBuddy.viewmodel
//
//import androidx.lifecycle.ViewModel
//import com.gladiator.BasketBuddy.model.Group
//import kotlinx.coroutines.flow.MutableStateFlow
//import kotlinx.coroutines.flow.StateFlow
//
//class CollaborationViewModel: ViewModel() {
//
//    private val _groups= MutableStateFlow<List<Group>>(emptyList())
//
//    val groups: StateFlow<List<Group>> = _groups
//
//    fun setGroups(groupList: List<Group>){
//        _groups.value=groupList
//    }
//}

package com.gladiator.BasketBuddy.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gladiator.BasketBuddy.model.Group
import com.gladiator.BasketBuddy.repo.GroupRepository
import com.gladiator.BasketBuddy.repo.GroupSession
import com.gladiator.BasketBuddy.repo.UserSession
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class CollaborationViewModel(
    private val repository: GroupRepository = GroupRepository()
) : ViewModel() {

    private val allGroups = mutableListOf<Group>()
    private val _groups= MutableStateFlow<List<Group>>(emptyList())
    private val _message = MutableStateFlow<String?>(null)

    val groups: StateFlow<List<Group>> = _groups
    val message: StateFlow<String?> = _message

    fun loadGroups() {
        val userId = UserSession.currentUserId

        if (userId == null) {
            _message.value = "Login again to continue"
            _groups.value = emptyList()
            return
        }

        viewModelScope.launch {
            val result = repository.getGroupsForUser(userId)

            result.onSuccess { groupList ->
                allGroups.clear()
                allGroups.addAll(groupList)
                _groups.value = groupList
                _message.value = null
            }

            result.onFailure {
                _message.value = it.message ?: "Unable to load groups"
                _groups.value = emptyList()
            }
        }
    }

    fun onSearch(query: String) {
        val searchText = query.trim()

        _groups.value = if (searchText.isBlank()) {
            allGroups.toList()
        } else {
            allGroups.filter {
                it.groupName.contains(searchText, ignoreCase = true) ||
                        it.groupCode.contains(searchText, ignoreCase = true)
            }
        }
    }

    fun clearMessage() {
        _message.value = null
    }

    fun onGroupSelected(group: Group) {
        GroupSession.setSelectedGroup(group)
    }
}