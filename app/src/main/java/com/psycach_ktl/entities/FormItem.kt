package com.psycach_ktl.entities

import com.psycach_ktl.parcels.FormItemParcel

sealed class FormItem {
    open val value: Int? = null
    abstract val id: Int

    fun getLabelKey(prefix: String, label: String = ""): String {
        return listOf(prefix, "input", label).joinToString("_")
    }

    open fun toParcel() : FormItemParcel? {
        return null
    }

    data class SliderItem(
        override val id: Int,
        val labelKeyPrefix: String = "",
        val min: Int,
        val max: Int,
        val step: Int,
        override var value: Int = min
    ) : FormItem() {
        val minLabelArrayKey: String
            get() = super.getLabelKey(labelKeyPrefix, "min")

        val maxLabelArrayKey: String
            get() = super.getLabelKey(labelKeyPrefix, "max")

        override fun toParcel(): FormItemParcel? {
            return FormItemParcel(id, value)
        }
    }

    data class RadioButtonGroupItem(
        override val id: Int,
        val labelKeyPrefix: String = "",
        override var value: Int = -1
    ) : FormItem() {
        val questionLabelKey: String
            get() = super.getLabelKey(labelKeyPrefix, "questions")

        val answerLabelKey: String
            get() = super.getLabelKey(labelKeyPrefix, "answers")

        override fun toParcel(): FormItemParcel? {
            return FormItemParcel(id, value)
        }
    }

    data class SubmitButtonItem(override val id: Int = -1) : FormItem()
}
