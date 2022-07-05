package com.oldee.user.ui.orderlog.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.oldee.user.databinding.LayoutOrderLogItemDetailSubBinding
import com.oldee.user.network.response.SurveySeqListItem

class OrderLogDetailAdapter(val imageCallback: (ImageView, String) -> Unit) :
    RecyclerView.Adapter<OrderLogDetailAdapter.OrderLogDetailViewHolder>() {
    private var list = mutableListOf<SurveySeqListItem>()

    fun setData(new: List<SurveySeqListItem>) {
        list.clear()
        list = new.toMutableList()
        notifyItemRangeChanged(0, list.size)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        OrderLogDetailViewHolder.from(parent)

    override fun onBindViewHolder(holder: OrderLogDetailViewHolder, position: Int) {
        holder.bind(list[position], imageCallback)
    }

    override fun getItemCount() = list.size

    class OrderLogDetailViewHolder(val binding: LayoutOrderLogItemDetailSubBinding) :
        RecyclerView.ViewHolder(binding.root) {
        companion object {
            fun from(parent: ViewGroup): OrderLogDetailViewHolder {
                val inflater = LayoutInflater.from(parent.context)
                val v = LayoutOrderLogItemDetailSubBinding.inflate(inflater, parent, false)

                return OrderLogDetailViewHolder(v)
            }
        }

        fun bind(data: SurveySeqListItem, imageCallback: (ImageView, String) -> Unit) {
            binding.data = data
            binding.llItem.data = data
            imageCallback.invoke(binding.llItem.ivImage, data.imagePath)
        }
    }
}