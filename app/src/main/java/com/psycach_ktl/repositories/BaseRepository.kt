package com.psycach_ktl.repositories

import com.google.firebase.firestore.FirebaseFirestore

interface BaseRepository {
    val database: FirebaseFirestore
        get() = FirebaseFirestore.getInstance()
}