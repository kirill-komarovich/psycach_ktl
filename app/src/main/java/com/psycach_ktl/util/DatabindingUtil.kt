package com.psycach_ktl.util

import android.content.res.Resources
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.psycach_ktl.entities.FormItem.RadioButtonGroupItem
import com.psycach_ktl.entities.FormItem.SliderItem
import java.util.*

@BindingAdapter("translatedValue")
fun TextView.setTranslatedValue(item: String?) {
    item?.let {
        val res: Resources = context.resources
        text = res.getString(res.getIdentifier(it, "string",  context.packageName))
    }
}

@BindingAdapter("minLabelValue")
fun TextView.setMinLabelValue(item: SliderItem?) {
    item?.let {
        val res: Resources = context.resources
        val minLabelStringArray = res.getStringArray(res.getIdentifier(it.minLabelArrayKey, "array",  context.packageName))

        text = minLabelStringArray[it.id]
    }
}

@BindingAdapter("maxLabelValue")
fun TextView.setMaxLabelValue(item: SliderItem?) {
    item?.let {
        val res: Resources = context.resources
        val maxLabelStringArray = res.getStringArray(res.getIdentifier(it.maxLabelArrayKey, "array",  context.packageName))

        text = maxLabelStringArray[it.id]
    }
}

@BindingAdapter("questionLabel")
fun TextView.setQuestionLabel(item: RadioButtonGroupItem?) {
    item?.let {
        val res: Resources = context.resources
        val questionsStringArray = res.getStringArray(res.getIdentifier(it.questionLabelKey, "array",  context.packageName))

        text = questionsStringArray[it.id]
    }
}

@ExperimentalStdlibApi
@BindingAdapter(value = ["resultLevel", "resultValue"], requireAll = true)
fun TextView.setResultLabel(level: String, value: Float) {
    val res: Resources = context.resources
    val levelLabel = res.getString(res.getIdentifier(level, "string",  context.packageName))

    text = String.format(text.toString(), levelLabel.capitalize(Locale.ROOT), value )
}
