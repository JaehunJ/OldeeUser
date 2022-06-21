package com.oldeee.user.ui.design.detail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.oldeee.user.data.PrepareItemMappingStringList
import com.oldeee.user.databinding.LayoutDesignDetailPrepareItemBinding

class ReformPrepareItemAdapter() :
    RecyclerView.Adapter<ReformPrepareItemAdapter.ReformPrepareItemViewHolder>() {
    var dataSet = listOf<PrepareItem>()

    fun setData(new: List<PrepareItem>) {
        dataSet = new
        notifyItemRangeChanged(0, dataSet.size)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ReformPrepareItemViewHolder.from(parent)

    override fun onBindViewHolder(holder: ReformPrepareItemViewHolder, position: Int) {
        holder.bind(dataSet[position])
    }

    override fun getItemCount(): Int {
        return dataSet.size
    }

    class ReformPrepareItemViewHolder(val binding: LayoutDesignDetailPrepareItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        companion object {
            fun from(parent: ViewGroup): ReformPrepareItemViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val bind = LayoutDesignDetailPrepareItemBinding.inflate(layoutInflater)
                return ReformPrepareItemViewHolder(bind)
            }
        }

        fun bind(data: PrepareItem) {
            binding.tvName.text = data.name
            val imageRes = PrepareItemMappingStringList[data.code]
            Glide.with(binding.ivImage.context).load(imageRes).into(binding.ivImage)
//            imageCallback(binding.ivImage, data.image)
        }
    }


}

data class PrepareItem(val image: String, val name: String, val code: String)