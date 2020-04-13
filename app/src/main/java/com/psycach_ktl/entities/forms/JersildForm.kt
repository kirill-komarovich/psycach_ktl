package com.psycach_ktl.entities.forms

import com.psycach_ktl.entities.Form
import com.psycach_ktl.entities.FormItem
import com.psycach_ktl.enums.MethodologyTypes

class JersildForm : Form(buildFormItems(), MethodologyTypes.JERSILD) {

    override fun isValid(): Boolean {
        return items.all { formItem ->
            when(formItem) {
                is FormItem.RadioButtonGroupItem -> formItem.value > -1
                else -> true
            }
        }
    }

    companion object {
        private const val QUESTIONS_COUNT = 36
        private val labelPrefixKey = MethodologyTypes.JERSILD.toLowerCase()

        fun buildFormItems(): List<FormItem> {
            val items = MutableList<FormItem>(QUESTIONS_COUNT) { index ->
                FormItem.RadioButtonGroupItem(index, labelPrefixKey)
            }
            items.add(FormItem.SubmitButtonItem())

            return items.toList()
        }
    }
}