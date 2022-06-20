package com.oldeee.user.ui.design.order

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.oldeee.user.databinding.LayoutDesignDetailPrepareItemBinding

class OrderPrepareItemAdapter:RecyclerView.Adapter<OrderPrepareItemAdapter.PrepareItemViewHolder>() {
    var dataSet= listOf<String>()

    fun setData(new:List<String>){
        dataSet = new
        notifyItemRangeChanged(0, dataSet.size)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = PrepareItemViewHolder.from(parent)

    override fun onBindViewHolder(holder: PrepareItemViewHolder, position: Int) {

    }

    override fun getItemCount() = dataSet.size

    class PrepareItemViewHolder(val binding:LayoutDesignDetailPrepareItemBinding):RecyclerView.ViewHolder(binding.root){
        companion object{
            fun from(parent: ViewGroup):PrepareItemViewHolder{
                val inflater = LayoutInflater.from(parent.context)
                val bind = LayoutDesignDetailPrepareItemBinding.inflate(inflater, parent, false)
                return  PrepareItemViewHolder(bind)
            }
        }

        fun bind(code:String){
//            binding.
        }
    }
}