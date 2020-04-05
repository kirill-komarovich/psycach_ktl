package com.psycach_ktl.entities

import com.psycach_ktl.entities.forms.SanForm
import com.psycach_ktl.enums.MethodologyTypes
import com.psycach_ktl.parcels.FormParcel

open class Form(
    val items: List<FormItem>,
    val methodologyType: MethodologyTypes
) {
    fun toParcel(): FormParcel {
        val itemParcels = items.mapNotNull { it.toParcel() }
        return FormParcel(methodologyType, itemParcels)
    }

    companion object {
        fun build(methodologyType: MethodologyTypes): Form {
            return when(methodologyType) {
                MethodologyTypes.SAN -> SanForm()
                else -> throw IllegalArgumentException("Unknown Form for $methodologyType methodology")
            }
        }
    }
}
