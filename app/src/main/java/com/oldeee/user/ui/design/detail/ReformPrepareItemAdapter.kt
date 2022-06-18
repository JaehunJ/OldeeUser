package com.oldeee.user.ui.design.detail

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.oldeee.user.databinding.LayoutDesignDetailPrepareItemBinding

class ReformPrepareItemAdapter(val imageCallback:(ImageView, String)->Unit) : RecyclerView.Adapter<ReformPrepareItemAdapter.ReformPrepareItemViewHolder>() {
    var dataSet = listOf<PrepareItem>()

    fun setData(new:List<PrepareItem>){
        dataSet = new
        notifyItemRangeChanged(0, dataSet.size)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = ReformPrepareItemViewHolder.from(parent)
    override fun onBindViewHolder(holder: ReformPrepareItemViewHolder, position: Int) {
        holder.bind(dataSet[position], imageCallback)
    }

    override fun getItemCount(): Int {
        return dataSet.size
    }

    class ReformPrepareItemViewHolder(val binding: LayoutDesignDetailPrepareItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        companion object {
            fun from(parent:ViewGroup) : ReformPrepareItemViewHolder{
                val layoutInflater = LayoutInflater.from(parent.context)
                val bind = LayoutDesignDetailPrepareItemBinding.inflate(layoutInflater)
                return ReformPrepareItemViewHolder(bind)
            }
        }

        fun bind(data:PrepareItem, imageCallback: (ImageView, String) -> Unit){
            binding.tvName.text = data.name
            imageCallback(binding.ivImage, data.image)
        }
    }

    data class PrepareItem(val image:String, val name:String)
}