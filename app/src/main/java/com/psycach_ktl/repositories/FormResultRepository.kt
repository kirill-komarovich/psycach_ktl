package com.psycach_ktl.repositories

import android.util.Log
import com.google.firebase.firestore.FieldValue
import com.psycach_ktl.entities.FormResult
import kotlinx.coroutines.tasks.await

class FormResultRepository : BaseRepository {
    private val collection = database.collection("form_results")
    lateinit var error: Exception

    suspend fun save(formResult: FormResult): Boolean {
        return try {
            val data = formResult.toMap().toMutableMap()
            data["createdAt"] = FieldValue.serverTimestamp()

            val result = collection
                .add(data)
                .await()
            Log.d("SanResultViewModel", "result $result")
            true
        } catch (e: Exception) {
            error = e
            Log.d("SanResultViewModel", "error $error")
            false
        }
    }
}