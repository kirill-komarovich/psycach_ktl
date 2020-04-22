package com.psycach_ktl.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.firestore.paging.FirestorePagingAdapter
import com.firebase.ui.firestore.paging.FirestorePagingOptions
import com.firebase.ui.firestore.paging.LoadingState
import com.psycach_ktl.databinding.HistoryListItemBinding
import com.psycach_ktl.entities.FormResult

class HistoryAdapter(
    options: FirestorePagingOptions<FormResult>,
    private val clickListener: Listener,
    private val loadingStateChangedListener: LoadingStateChangedListener
) : FirestorePagingAdapter<FormResult, HistoryAdapter.ViewHolder>(options) {

    override fun onBindViewHolder(holder: ViewHolder, position: Int, model: FormResult) {
        holder.bind(model, clickListener)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onLoadingStateChanged(state: LoadingState) {
        loadingStateChangedListener.onLoadingStateChanged(state)
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

    class Listener(val clickListener: (FormResult: FormResult) -> Unit) {
        fun onClick(FormResult: FormResult) = clickListener(FormResult)
    }

    class LoadingStateChangedListener(val loadingStateChangedListener: (state: LoadingState) -> Unit) {
        fun onLoadingStateChanged(state: LoadingState) = loadingStateChangedListener(state)
    }
}
