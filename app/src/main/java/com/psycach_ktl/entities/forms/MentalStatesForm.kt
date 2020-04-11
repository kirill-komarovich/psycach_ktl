package com.psycach_ktl.entities.forms

import com.psycach_ktl.entities.Form
import com.psycach_ktl.entities.FormItem
import com.psycach_ktl.enums.MethodologyTypes

class MentalStatesForm : Form(buildFormItems(), MethodologyTypes.MENTAL_STATES) {

    override fun isValid(): Boolean {
        return items.all { formItem ->
            when(formItem) {
                is FormItem.RadioButtonGroupItem -> formItem.value > -1
                else -> true
            }
        }
    }

    companion object {
        private const val QUESTIONS_COUNT = 40
        private val labelPrefixKey = MethodologyTypes.MENTAL_STATES.toLowerCase()

        fun buildFormItems(): List<FormItem> {
            val items = MutableList<FormItem>(QUESTIONS_COUNT) { index ->
                FormItem.RadioButtonGroupItem(index, labelPrefixKey)
            }
            items.add(FormItem.SubmitButtonItem())

            return items.toList()
        }
    }
}