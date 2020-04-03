package com.psycach_ktl.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.slider.Slider
import com.psycach_ktl.databinding.SliderFormItemBinding
import com.psycach_ktl.entities.FormItem
import com.psycach_ktl.entities.FormItem.SliderItem
import com.psycach_ktl.enums.FormItemTypes
import java.lang.ClassCastException

class FormItemsAdapter: ListAdapter<FormItem, RecyclerView.ViewHolder>(DiffCallback()) {
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = getItem(position)
        when(holder) {
            is SliderViewHolder -> {
                holder.bind(item!! as SliderItem)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when(val item = getItem(position)) {
            is SliderItem -> FormItemTypes.SLIDER.ordinal
            else -> throw ClassCastException("Unknown FormItem $item")
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when(viewType) {
            FormItemTypes.SLIDER.ordinal -> SliderViewHolder.from(parent)
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

    class DiffCallback : DiffUtil.ItemCallback<FormItem>() {
        override fun areItemsTheSame(oldItem: FormItem, newItem: FormItem): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: FormItem, newItem: FormItem): Boolean {
            return oldItem == newItem
        }
    }
}

