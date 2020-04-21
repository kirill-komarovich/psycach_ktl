package com.psycach_ktl.entities

import com.psycach_ktl.enums.MethodologyTypes
import com.psycach_ktl.parcels.FormParcel

data class FormResult(
    val methodologyType: MethodologyTypes,
    val items: List<FormResultItem>,
    var userId: String? = null
) {

    fun toMap(): Map<String, Any?> {
        return mapOf(
            "methodologyType" to methodologyType,
            "userId" to userId,
            "answers" to items.map { it.id.toString() to it.value }.toMap()
        )
    }

    companion object {
        fun from(formParcel: FormParcel) : FormResult {
            val items = formParcel.items.map { FormResultItem(it.id, it.value) }

            return FormResult(formParcel.methodologyType, items)
        }

    }
}
