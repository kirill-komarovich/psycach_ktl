package com.psycach_ktl.repositories

import android.util.Log
import com.google.firebase.firestore.Query
import com.psycach_ktl.entities.FormResult
import kotlinx.coroutines.tasks.await

class FormResultRepository : BaseRepository {
    private val collection = database.collection(COLLECTION_NAME)

    fun buildListQuery(userIds: List<String?> = emptyList()): Query {
        return collection.whereIn("userId", userIds).orderBy("createdAt", Query.Direction.DESCENDING)
    }

    suspend fun save(formResult: FormResult) {
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
