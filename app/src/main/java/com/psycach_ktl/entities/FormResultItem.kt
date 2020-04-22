package com.psycach_ktl.entities

import com.google.firebase.firestore.DocumentId

data class FormResultItem(
    @DocumentId
    var id: String = "",
    var value: Int = -1
)