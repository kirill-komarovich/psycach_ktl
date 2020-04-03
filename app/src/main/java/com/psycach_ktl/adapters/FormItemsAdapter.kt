package com.psycach_ktl.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.slider.Slider
import com.psycach_ktl.databinding.SliderFormItemBinding
import com.psycach_ktl.databinding.SubmitButtonFormItemBinding
import com.psycach_ktl.entities.FormItem
import com.psycach_ktl.entities.FormItem.*
import com.psycach_ktl.enums.FormItemTypes
import java.lang.ClassCastException

class FormItemsAdapter(private val submitListener: SubmitListener): ListAdapter<FormItem, RecyclerView.ViewHolder>(DiffCallback()) {
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = getItem(position)
        when(holder) {
            is SliderViewHolder -> holder.bind(item!! as SliderItem)
            is SubmitButtonViewHolder -> holder.bind(submitListener)
            else -> throw ClassCastException("Unknown ViewHolder $holder")
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when(val item = getItem(position)) {
            is SliderItem -> FormItemTypes.SLIDER.ordinal
            is SubmitButtonItem -> FormItemTypes.SUBMIT_BUTTON.ordinal
            else -> throw ClassCastException("Unknown FormItem $item")
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when(viewType) {
            FormItemTypes.SLIDER.ordinal -> SliderViewHolder.from(parent)
            FormItemTypes.SUBMIT_BUTTON.ordinal -> SubmitButtonViewHolder.from(parent)
            else -> throw ClassCastException("Unknown ViewType $viewType")
        }
    }

    class SliderViewHolder private constructor(val binding: SliderFormItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(sliderItem: SliderItem) {
            binding.apply {
                item = sliderItem
                slider.addOnChangeListener { _: Slider, newValue: Float, fromUser: Boolean ->
                    if (fromUser) {
                        item?.let { it.value = newValue.toInt() }
                    }
                }
                executePendingBindings()
            }

        }

        companion object {
            fun from(parent: ViewGroup): SliderViewHolder {
                val inflater = LayoutInflater.from(parent.context)
                val binding = SliderFormItemBinding.inflate(inflater, parent, false)

                return SliderViewHolder(binding)
            }
        }
    }

    class SubmitButtonViewHolder private constructor(val binding: SubmitButtonFormItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(submitListener: SubmitListener) {
            binding.clickListener = submitListener
            binding.executePendingBindings()
        }
        companion object {
            fun from(parent: ViewGroup): SubmitButtonViewHolder {
                val inflater = LayoutInflater.from(parent.context)
                val binding = SubmitButtonFormItemBinding.inflate(inflater, parent, false)

                return SubmitButtonViewHolder(binding)
            }
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<FormItem>() {
        override fun areItemsTheSame(oldItem: FormItem, newItem: FormItem): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: FormItem, newItem: FormItem): Boolean {
            return oldItem == newItem
        }
    }

    class SubmitListener(val clickListener: () -> Unit) {
        fun onClick() = clickListener()
    }
}
