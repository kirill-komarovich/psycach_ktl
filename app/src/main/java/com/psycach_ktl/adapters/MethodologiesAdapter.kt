package com.psycach_ktl.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.psycach_ktl.R
import com.psycach_ktl.entities.Methodology
import com.psycach_ktl.services.I18n

class MethodologiesAdapter : RecyclerView.Adapter<MethodologiesAdapter.ViewHolder>() {
    var data = listOf<Methodology>()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun getItemCount() = data.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = data[position]
        holder.methodologyTitle.text = I18n.translate(holder.itemView.context, item.type)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.methodology_list_item, parent, false)

        return ViewHolder(view)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val methodologyTitle: TextView = itemView.findViewById(R.id.methodology_title)
    }
}
