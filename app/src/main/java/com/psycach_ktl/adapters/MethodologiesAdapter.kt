package com.psycach_ktl.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.ListAdapter
import com.psycach_ktl.databinding.MethodologyListItemBinding
import com.psycach_ktl.entities.Methodology

class MethodologiesAdapter(private val clickListener: Listener) : ListAdapter<Methodology, MethodologiesAdapter.ViewHolder>(DiffCallback()) {
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item!!, clickListener)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    class ViewHolder private constructor(val binding: MethodologyListItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Methodology, clickListener: Listener) {
            binding.methodology = item
            binding.clickListener = clickListener
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val inflater = LayoutInflater.from(parent.context)
                val binding = MethodologyListItemBinding.inflate(inflater, parent, false)

                return ViewHolder(binding)
            }
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<Methodology>() {
        override fun areItemsTheSame(oldItem: Methodology, newItem: Methodology): Boolean {
            return oldItem.type == newItem.type
        }

        override fun areContentsTheSame(oldItem: Methodology, newItem: Methodology): Boolean {
            return oldItem == newItem
        }

    }

    class Listener(val clickListener: (methodologyType: String) -> Unit) {
        fun onClick(methodology: Methodology) = clickListener(methodology.type)
    }
}
