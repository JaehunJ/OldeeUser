package com.oldee.user.ui.orderlog.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.oldee.user.databinding.LayoutOrderLogSubItemBinding
import com.oldee.user.network.response.SurveySeqListItem

class OrderReadyViewSubItemAdapter(val imageCallBack:(ImageView, String)->Unit) :RecyclerView.Adapter<OrderReadyViewSubItemAdapter.OrderReadyViewSubItemViewHolder>(){
    var list = listOf<SurveySeqListItem>()

    fun setData(new:List<SurveySeqListItem>){
        list = new
        notifyItemRangeChanged(0, list.size)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ) = OrderReadyViewSubItemViewHolder.from(parent)

    override fun onBindViewHolder(holder: OrderReadyViewSubItemViewHolder, position: Int) {
        holder.bind(list[position], imageCallBack)
    }

    override fun getItemCount() = list.size


    class OrderReadyViewSubItemViewHolder(val binding: LayoutOrderLogSubItemBinding) :RecyclerView.ViewHolder(binding.root){
        companion object {
            fun from(parent: ViewGroup) : OrderReadyViewSubItemViewHolder{
                val inflater = LayoutInflater.from(parent.context)
                val v = LayoutOrderLogSubItemBinding.inflate(inflater, parent, false)

                return OrderReadyViewSubItemViewHolder(v)
            }
        }

        fun bind(data:SurveySeqListItem, imageCallBack:(ImageView, String)->Unit){
            binding.data = data
            imageCallBack(binding.ivImage, data.imagePath)
        }
    }
}