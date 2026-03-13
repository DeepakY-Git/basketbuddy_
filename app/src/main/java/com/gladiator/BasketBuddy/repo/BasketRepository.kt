package com.gladiator.BasketBuddy.repo

import com.gladiator.BasketBuddy.model.ItemList
import com.gladiator.BasketBuddy.network.FirebaseClient
import kotlinx.coroutines.tasks.await

class BasketRepository {

    private val listsRef = FirebaseClient.listsRef

    suspend fun getListsForGroup(groupCode: String): Result<List<ItemList>> {
        return try {
            val snapshot = listsRef.child(groupCode).get().await()
            val lists = mutableListOf<ItemList>()

            for (child in snapshot.children) {
                val itemList = child.getValue(ItemList::class.java) ?: continue
                lists.add(itemList)
            }

            Result.success(lists.sortedBy { it.name.lowercase() })
        } catch (error: Exception) {
            Result.failure(error)
        }
    }
}