package com.psycach_ktl.entities

import com.psycach_ktl.entities.results.SanResult
import com.psycach_ktl.enums.MethodologyTypes
import com.psycach_ktl.parcels.FormParcel

open class FormResult(val methodologyType: MethodologyTypes, open val items: List<FormResultItem>) {

    companion object {
        fun from(formParcel: FormParcel) : FormResult {
            val items = formParcel.items.map { FormResultItem(it.id, it.value) }

            return when(formParcel.methodologyType) {
                MethodologyTypes.SAN -> SanResult(items)
                else -> throw IllegalArgumentException("Unknown FormResult for ${formParcel.methodologyType} methodology")
            }
        }

    }
}