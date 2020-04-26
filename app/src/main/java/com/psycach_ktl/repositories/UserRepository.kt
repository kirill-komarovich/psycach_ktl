package com.psycach_ktl.repositories

import com.google.firebase.firestore.SetOptions
import com.psycach_ktl.entities.UserProfile
import com.psycach_ktl.enums.UserRoles
import kotlinx.coroutines.tasks.await

class UserRepository : BaseRepository {
    private val collection = database.collection(COLLECTION_NAME)

    suspend fun updateUserRole(userId: String, role: UserRoles) {
        collection.document(userId).set(mapOf("role" to role), SetOptions.merge()).await()
    }

    suspend fun findProfile(userId: String): UserProfile? {
        val result = collection.document(userId).get().await()

        return result.toObject(UserProfile::class.java)
    }

    companion object {
        private const val COLLECTION_NAME = "users"
    }
}