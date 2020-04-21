package com.psycach_ktl.entities

import com.psycach_ktl.enums.MethodologyTypes
import com.psycach_ktl.parcels.FormParcel
import com.google.firebase.Timestamp
import com.google.firebase.firestore.DocumentId
import com.google.firebase.firestore.Exclude
import com.google.firebase.firestore.IgnoreExtraProperties
import com.google.firebase.firestore.ServerTimestamp

@IgnoreExtraProperties
data class FormResult(
    @DocumentId
    @get:Exclude
    var id: String = "",
    val methodologyType: MethodologyTypes? = null,
    @get:Exclude
    val items: List<FormResultItem>,
    var userId: String? = null,
    @ServerTimestamp
    var createdAt: Timestamp? = null
) {

    constructor() : this(
        items = emptyList()
    )

    companion object {
        fun from(formParcel: FormParcel) : FormResult {
            val items = formParcel.items.map { FormResultItem(it.id, it.value) }

            return FormResult(methodologyType = formParcel.methodologyType, items = items)
        }

    }
}
