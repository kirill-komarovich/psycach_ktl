package com.psycach_ktl.repositories

import com.google.firebase.firestore.Query
import com.google.firebase.firestore.SetOptions
import com.google.firebase.functions.FirebaseFunctions
import com.psycach_ktl.entities.UserProfile
import com.psycach_ktl.enums.UserRoles
import kotlinx.coroutines.tasks.await

class UserRepository : BaseRepository {
    private val collection = database.collection(COLLECTION_NAME)
    private val functions = FirebaseFunctions.getInstance()

    suspend fun updateUserRole(userId: String, role: UserRoles) {
        collection.document(userId).set(mapOf("role" to role), SetOptions.merge()).await()
    }

    suspend fun findProfile(userId: String): UserProfile? {
        val result = collection.document(userId).get().await()

        return result.toObject(UserProfile::class.java)
    }

    fun buildPsychologistsListQuery(userId: String): Query {
        return collection.document(userId).collection(PSYCHOLOGISTS_COLLECTION_NAME).limit(PER_PAGE.toLong())
    }

    suspend fun addPsychologist(email: String) {
        val data = hashMapOf("email" to email)

        functions.getHttpsCallable(ADD_PSYCHOLOGIST_FUNCTION).call(data).await()
    }

    suspend fun removePsychologist(id: String) {
        val data = hashMapOf("id" to id)

        functions.getHttpsCallable(REMOVE_PSYCHOLOGIST_FUNCTION).call(data).await()
    }

    companion object {
        private const val COLLECTION_NAME = "users"
        private const val PSYCHOLOGISTS_COLLECTION_NAME = "psychologists"
        private const val CLIENTS_COLLECTION_NAME = "clients"
        private const val ADD_PSYCHOLOGIST_FUNCTION = "addPsychologist"
        private const val REMOVE_PSYCHOLOGIST_FUNCTION = "removePsychologist"
        private const val PER_PAGE = 10
    }
}