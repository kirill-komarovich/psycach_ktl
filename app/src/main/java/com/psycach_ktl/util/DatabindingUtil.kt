package com.psycach_ktl.util

import android.content.res.Resources
import android.widget.TextView
import androidx.databinding.BindingAdapter

@BindingAdapter("translatedValue")
fun TextView.setTranslatedValue(item: String?) {
    item?.let {
        val res: Resources = context.resources
        text = res.getString(res.getIdentifier(it, "string",  context.packageName))
    }
}