package com.psycach_ktl.util

import android.content.res.Resources
import android.util.Log
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.psycach_ktl.entities.FormItem.SliderItem

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