package com.oldeee.user.ui.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.oldeee.user.databinding.LayoutHomeDesignItemBinding
import com.oldeee.user.network.response.DesignListItem

class DesignListAdapter : RecyclerView.Adapter<DesignListAdapter.DesignListItemViewHolder>() {

    var dataSet = listOf<DesignListItem>()

    fun setData(list:List<DesignListItem>){
        dataSet = list
        notifyItemRangeChanged(0, dataSet.size)
    }

    override fun getItemCount(): Int {
        return dataSet.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        DesignListItemViewHolder.from(parent)

    override fun onBindViewHolder(holder: DesignListItemViewHolder, position: Int) {
        holder.bind(dataSet[position])
    }

    class DesignListItemViewHolder(val binding: LayoutHomeDesignItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        companion object {
            fun from(parent: ViewGroup): DesignListItemViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val bind = LayoutHomeDesignItemBinding.inflate(layoutInflater, parent, false)
                return DesignListItemViewHolder(bind)
            }
        }

        fun bind(data:DesignListItem){
            binding.res = data
        }
    }
}