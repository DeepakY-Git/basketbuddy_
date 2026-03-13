package com.gladiator.BasketBuddy.repo


//import com.gladiator.BasketBuddy.model.Group
//import com.google.firebase.database.FirebaseDatabase
//import kotlinx.coroutines.tasks.await
//
//class GroupRepository {
//
//    private val database = FirebaseDatabase.getInstance()
//    private val groupsRef = database.getReference("groups")
//
//    suspend fun createGroup(group: Group): Result<Boolean> {
//        return try {
//
//            groupsRef.child(group.groupCode).setValue(group).await()
//
//            Result.success(true)
//
//        } catch (e: Exception) {
//
//            Result.failure(e)
//
//        }
//    }
//
//    suspend fun getGroups(): Result<List<Group>> {
//
//        return try {
//
//            val snapshot = groupsRef.get().await()
//
//            val groupList = mutableListOf<Group>()
//
//            for (child in snapshot.children) {
//
//                val group = child.getValue(Group::class.java)
//
//                if (group != null) {
//                    groupList.add(group)
//                }
//            }
//
//            Result.success(groupList)
//
//        } catch (e: Exception) {
//
//            Result.failure(e)
//
//        }
//    }
//}


import com.gladiator.BasketBuddy.model.Group
import com.gladiator.BasketBuddy.network.FirebaseClient
import kotlinx.coroutines.tasks.await

class GroupRepository {

    private val groupsRef = FirebaseClient.groupsRef
    private val userGroupsRef = FirebaseClient.userGroupsRef

    suspend fun createGroup(group: Group): Result<Unit> {
        return try {
            groupsRef.child(group.groupCode).setValue(group).await()
            userGroupsRef
                .child(group.ownerId.toString())
                .child(group.groupCode)
                .setValue(true)
                .await()

            Result.success(Unit)
        } catch (error: Exception) {
            Result.failure(error)
        }
    }

    suspend fun joinGroup(groupCode: String, userId: Int): Result<Unit> {
        return try {
            val groupSnapshot = groupsRef.child(groupCode).get().await()

            if (!groupSnapshot.exists()) {
                return Result.failure(IllegalArgumentException("Group not found"))
            }

            userGroupsRef
                .child(userId.toString())
                .child(groupCode)
                .setValue(true)
                .await()

            Result.success(Unit)
        } catch (error: Exception) {
            Result.failure(error)
        }
    }

    suspend fun getGroupsForUser(userId: Int): Result<List<Group>> {
        return try {
            val membershipSnapshot = userGroupsRef.child(userId.toString()).get().await()
            val groups = mutableListOf<Group>()

            for (groupNode in membershipSnapshot.children) {
                val groupCode = groupNode.key ?: continue
                val groupSnapshot = groupsRef.child(groupCode).get().await()
                val group = groupSnapshot.getValue(Group::class.java) ?: continue
                groups.add(group)
            }

            Result.success(groups.sortedBy { it.groupName.lowercase() })
        } catch (error: Exception) {
            Result.failure(error)
        }
    }
}