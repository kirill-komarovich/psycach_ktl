package com.psycach_ktl.entities

import com.google.firebase.firestore.DocumentId

data class FormResultItem(
    @DocumentId
    val id: String,
    val value: Int
)