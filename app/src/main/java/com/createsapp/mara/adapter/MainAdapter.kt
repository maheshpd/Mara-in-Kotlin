package com.createsapp.mara.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.createsapp.mara.R
import com.createsapp.mara.model.MainModel

class MainAdapter(internal val context: Context, internal var list: List<MainModel>) :
    RecyclerView.Adapter<MainAdapter.MainViewHolder>() {
    inner class MainViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var name: TextView? = null
        var totalCount: TextView? = null

        init {
            name = itemView.findViewById(R.id.nameTxt)
            totalCount = itemView.findViewById(R.id.totalTxt)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainViewHolder {
        return MainViewHolder(
            LayoutInflater.from(context).inflate(R.layout.main_item, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: MainViewHolder, position: Int) {
        holder.name!!.text = list.get(position).name
//        holder.totalCount!!.text = list.get(position).totlaCount
    }
}