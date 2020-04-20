package com.psycach_ktl.util

import android.content.res.Resources
import android.graphics.drawable.Drawable
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.psycach_ktl.entities.FormItem.*

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

@BindingAdapter(value = ["resultLevel", "resultValue", "resultKey", "resultKeyPrefix"], requireAll = true)
fun setResultLabel(view: TextView, level: String, value: Float, key: String, prefix: String) {
    val res: Resources = view.context.resources
    val labelName = res.getIdentifier("${prefix}_${level}_${key}", "string",  view.context.packageName)
    val label = res.getString(labelName)

    view.text = String.format(label, value)
}

@BindingAdapter(value = ["resultValue", "resultKey", "resultKeyPrefix"], requireAll = true)
fun setResultLabel(view: TextView, value: Float, key: String, prefix: String) {
    val res: Resources = view.context.resources
    val labelName = res.getIdentifier("${prefix}_${key}", "string",  view.context.packageName)
    val label = res.getString(labelName)

    view.text = String.format(label, value)
}

@BindingAdapter(value = ["imageUrl", "imagePlaceholder"], requireAll = true)
fun setImageByUrl(view: ImageView, imageUrl: String?, placeholder: Drawable) {
    Glide.with(view.context)
        .load(imageUrl)
        .placeholder(placeholder)
        .error(placeholder)
        .apply(RequestOptions.circleCropTransform())
        .into(view)
}