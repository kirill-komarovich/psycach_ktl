package com.psycach_ktl.util

import android.content.res.Resources
import android.widget.TextView
import androidx.databinding.BindingAdapter
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

@ExperimentalStdlibApi
@BindingAdapter(value = ["resultLevel", "resultValue"], requireAll = true)
fun TextView.setResultLabel(level: String, value: Float) {
    val res: Resources = context.resources
    val levelLabel = res.getString(res.getIdentifier(level, "string",  context.packageName))

    text = String.format(text.toString(), levelLabel.capitalize(Locale.ROOT), value )
}