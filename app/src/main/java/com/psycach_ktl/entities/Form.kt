package com.psycach_ktl.entities

import com.psycach_ktl.entities.forms.SanForm
import com.psycach_ktl.enums.MethodologyTypes

open class Form(
    val items: List<FormItem>,
    val methodologyType: MethodologyTypes
) {
    companion object {
        fun build(methodologyType: MethodologyTypes): Form {
            return when(methodologyType) {
                MethodologyTypes.SAN -> SanForm()
                else -> throw IllegalArgumentException("Unknown Form for $methodologyType methodology")
            }
        }
    }
}
