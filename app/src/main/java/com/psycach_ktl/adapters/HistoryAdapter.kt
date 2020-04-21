package com.psycach_ktl.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.ListAdapter
import com.psycach_ktl.databinding.HistoryListItemBinding
import com.psycach_ktl.entities.FormResult

class HistoryAdapter(
    private val clickListener: Listener,
    private val onBottomReachedListener: OnBottomReachedListener
) : ListAdapter<FormResult, HistoryAdapter.ViewHolder>(DiffCallback()) {

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (position == itemCount - 1) onBottomReachedListener.onBottomReached(position)

        val item = getItem(position)
        holder.bind(item!!, clickListener)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    class ViewHolder private constructor(val binding: HistoryListItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: FormResult, clickListener: Listener) {
            binding.result = item
            binding.clickListener = clickListener
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val inflater = LayoutInflater.from(parent.context)
                val binding = HistoryListItemBinding.inflate(inflater, parent, false)

                return ViewHolder(binding)
            }
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<FormResult>() {
        override fun areItemsTheSame(oldItem: FormResult, newItem: FormResult): Boolean {
            return oldItem.id == newItem.id
        }

        @SuppressLint("DiffUtilEquals")
        override fun areContentsTheSame(oldItem: FormResult, newItem: FormResult): Boolean {
            return oldItem == newItem
        }
    }

    class Listener(val clickListener: (FormResult: FormResult) -> Unit) {
        fun onClick(FormResult: FormResult) = clickListener(FormResult)
    }

    class OnBottomReachedListener(val onBottomReachedListener: (position: Int) -> Unit) {
        fun onBottomReached(position: Int) = onBottomReachedListener(position)
    }
}
