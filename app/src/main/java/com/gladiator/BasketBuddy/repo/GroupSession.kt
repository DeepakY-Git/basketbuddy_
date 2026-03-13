package com.gladiator.BasketBuddy.repo

import com.gladiator.BasketBuddy.model.Group
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

object GroupSession {

    private val _selectedGroup = MutableStateFlow<Group?>(null)
    val selectedGroup: StateFlow<Group?> = _selectedGroup

    fun setSelectedGroup(group: Group) {
        _selectedGroup.value = group
    }

    fun clear() {
        _selectedGroup.value = null
    }
}