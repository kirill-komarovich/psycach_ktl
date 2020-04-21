package com.psycach_ktl.repositories

import android.util.Log
import com.psycach_ktl.entities.FormResult
import kotlinx.coroutines.tasks.await

class FormResultRepository(private val userId: String? = null) : BaseRepository {
    private val collection = database.collection(COLLECTION_NAME)

    suspend fun findAll(page: Int, perPage: Int, userIds: List<String?> = listOf(userId)): List<FormResult>? {
        val result = collection
            .whereIn("userId", userIds)
            .limit(perPage.toLong())
            .get()
            .await()

        val objects = result.toObjects(FormResult::class.java).toList()
        Log.d("FormResultRepository", objects.joinToString(", "))
        return objects
    }

    suspend fun save(formResult: FormResult) {
        Log.d("FormResultRepository", "formResult $formResult")

        database.runBatch { batch ->
            val formResultRef = collection.document()
            batch.set(formResultRef, formResult)
            val itemsCollection = formResultRef.collection(ITEMS_COLLECTION_NAME)

            formResult.items.forEach { item ->
                val formResultItemRef = itemsCollection.document(item.id)
                batch.set(formResultItemRef, item)
            }
        }.await()
    }

    companion object {
        const val COLLECTION_NAME = "form_results"
        const val ITEMS_COLLECTION_NAME = "items"
    }
}
