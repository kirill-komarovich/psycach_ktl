package com.psycach_ktl.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.firebase.ui.firestore.FirestoreRecyclerAdapter
import com.firebase.ui.firestore.FirestoreRecyclerOptions
import com.psycach_ktl.databinding.PsychologistListItemBinding
import com.psycach_ktl.entities.UserProfile

class PsychologistsAdapter(
    options: FirestoreRecyclerOptions<UserProfile>,
    private val clickListener: Listener
) : FirestoreRecyclerAdapter<UserProfile, PsychologistsAdapter.ViewHolder>(options) {

    override fun onBindViewHolder(holder: ViewHolder, position: Int, model: UserProfile) {
        holder.bind(model, clickListener)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    class ViewHolder private constructor(val binding: PsychologistListItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(item: UserProfile, clickListener: Listener) {
            binding.profile = item
            binding.clickListener = clickListener
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): ViewHolder {
                val inflater = LayoutInflater.from(parent.context)
                val binding = PsychologistListItemBinding.inflate(inflater, parent, false)

                return ViewHolder(binding)
            }
        }
    }

    class Listener(val clickListener: (userProfile: UserProfile) -> Unit) {
        fun onClick(userProfile: UserProfile) = clickListener(userProfile)
    }
}