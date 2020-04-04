package com.psycach_ktl.entities.forms

import com.psycach_ktl.entities.Form
import com.psycach_ktl.entities.FormItem
import com.psycach_ktl.enums.MethodologyTypes

class SanForm : Form(buildFormItems(), MethodologyTypes.SAN) {
    companion object {
        private const val QUESTIONS_COUNT = 30
        private const val SLIDER_MIN = -3
        private const val SLIDER_MAX = 3
        private const val SLIDER_STEP = 1
        private const val DEFAULT_SLIDER_VALUE = 0
        private val labelPrefixKey = MethodologyTypes.SAN.toLowerCase()

        fun buildFormItems(): List<FormItem> {
            val items = MutableList<FormItem>(QUESTIONS_COUNT) { index ->
                FormItem.SliderItem(index, labelPrefixKey, SLIDER_MIN, SLIDER_MAX, SLIDER_STEP, DEFAULT_SLIDER_VALUE)
            }
            items.add(FormItem.SubmitButtonItem())

            return items.toList()
        }
    }
}
