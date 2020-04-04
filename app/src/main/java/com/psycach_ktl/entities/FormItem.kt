package com.psycach_ktl.entities

sealed class FormItem(open val id: Int) {
    fun getLabelKey(prefix: String, label: String = ""): String {
        return listOf(prefix, "input", label).joinToString("_")
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

    data class SubmitButtonItem(override val id: Int = -1) : FormItem(id)
}
