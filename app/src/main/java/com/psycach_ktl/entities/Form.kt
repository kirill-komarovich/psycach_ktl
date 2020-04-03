package com.psycach_ktl.entities

import com.psycach_ktl.enums.FormTypes
import com.psycach_ktl.enums.MethodologyTypes

data class Form(val items: List<FormItem>, val methodologyType: MethodologyTypes) {
    companion object {
        fun build(methodologyType: MethodologyTypes): Form {
            val formType = FormTypes.forMethodology(methodologyType)
            val formItems = FormItem.build(formType, methodologyType.getQuestionsCount(), methodologyType.toLowerCase())

            return Form(formItems, methodologyType);
        }
    }
}