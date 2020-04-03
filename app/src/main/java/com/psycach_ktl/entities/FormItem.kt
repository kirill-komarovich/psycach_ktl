package com.psycach_ktl.entities

import com.psycach_ktl.enums.FormTypes

sealed class FormItem(open val id: Int) {
    fun getLabelKey(prefix: String, label: String = ""): String {
        return listOf(prefix, "input", label).joinToString("_")
    }

    companion object {
        fun build(formType: FormTypes, size: Int, labelKeyPrefix: String = ""): List<FormItem> {
            return when(formType) {
                FormTypes.SLIDER -> List(size) {
                    SliderItem(id = it, labelKeyPrefix = labelKeyPrefix, min = -3, max = 3, step = 1, value = 0)
                }
                else -> throw IllegalAccessException("Unknown Form type $formType")
            }
        }
    }

    data class SliderItem(
        override val id: Int,
        val labelKeyPrefix: String,
        val min: Int,
        val max: Int,
        val step: Int,
        var value: Int = min
    ) : FormItem(id) {
        val minLabelArrayKey: String
            get() = super.getLabelKey(labelKeyPrefix, "min")

        val maxLabelArrayKey: String
            get() = super.getLabelKey(labelKeyPrefix, "max")
    }
}

