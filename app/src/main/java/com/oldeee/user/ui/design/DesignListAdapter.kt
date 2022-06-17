package com.oldeee.user.ui.design

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.oldeee.user.databinding.LayoutDesignListItemBinding
import com.oldeee.user.network.response.DesignListItem

class DesignListAdapter(val imageCallBack: (ImageView, String) -> Unit) :
    RecyclerView.Adapter<DesignListAdapter.DesignListItemViewHolder>() {
    var dataSet = mutableListOf<DesignListItem>()

    private fun setData(list: MutableList<DesignListItem>) {
        dataSet = list.toMutableList()
        val size = dataSet.size
        notifyItemRangeChanged(0, size)
    }

    fun addData(newData:MutableList<DesignListItem>){
        if(dataSet.size == 0){
            setData(newData)
            return
        }

        val orgSize = dataSet.size
        newData.forEach {
            dataSet.add(it)
        }
        notifyItemRangeInserted(orgSize-1, newData.size)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        DesignListItemViewHolder.from(parent)

    override fun onBindViewHolder(holder: DesignListItemViewHolder, position: Int) {
        holder.bind(dataSet[position], imageCallBack)
    }

    override fun getItemCount() = dataSet.size

    class DesignListItemViewHolder(val binding: LayoutDesignListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        companion object {
            fun from(parent: ViewGroup): DesignListItemViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val bind = LayoutDesignListItemBinding.inflate(layoutInflater, parent, false)

                return DesignListItemViewHolder(bind)
            }
        }

        fun bind(data: DesignListItem, imageCallBack: (ImageView, String) -> Unit) {
            binding.res = data
            data.mainImageName?.let{
                imageCallBack(binding.ivImage, it)
            }
        }
    }
}