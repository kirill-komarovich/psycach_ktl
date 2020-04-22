package com.psycach_ktl.entities

import com.psycach_ktl.enums.MethodologyTypes
import com.psycach_ktl.parcels.FormParcel
import com.google.firebase.Timestamp
import com.google.firebase.firestore.DocumentId
import com.google.firebase.firestore.Exclude
import com.google.firebase.firestore.IgnoreExtraProperties
import com.google.firebase.firestore.ServerTimestamp
import com.psycach_ktl.parcels.FormResultParcel

@IgnoreExtraProperties
data class FormResult(
    @DocumentId
    var id: String = "",
    val methodologyType: MethodologyTypes? = null,
    @get:Exclude
    var items: List<FormResultItem> = emptyList(),
    var userId: String? = null,
    @ServerTimestamp
    var createdAt: Timestamp? = null
) {

    fun toParcel() : FormResultParcel = FormResultParcel(id, methodologyType, userId!!, createdAt!!)

    fun isNewRecord(): Boolean = id.isBlank()

    companion object {
        fun from(formParcel: FormParcel) : FormResult {
            val items = formParcel.items.map { FormResultItem(it.id, it.value) }

            return FormResult(methodologyType = formParcel.methodologyType, items = items)
        }

        fun from(formResultParcel: FormResultParcel) : FormResult {
            return FormResult(
                id = formResultParcel.id,
                methodologyType = formResultParcel.methodologyType,
                items = emptyList(),
                userId = formResultParcel.userId,
                createdAt = formResultParcel.createdAt
            )
        }

    }
}
