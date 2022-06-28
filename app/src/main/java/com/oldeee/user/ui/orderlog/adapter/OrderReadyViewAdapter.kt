package com.oldeee.user.ui.orderlog.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.oldeee.user.databinding.LayoutOrderLogReadyItemBinding
import com.oldeee.user.network.response.PaymentListItem

class OrderReadyViewAdapter(val onClick:(Int)->Unit, val imageCallBack: (ImageView, String) -> Unit) :RecyclerView.Adapter<OrderReadyViewAdapter.OrderReadyViewItemViewHolder>(){

    var dataSet = mutableListOf<PaymentListItem>()

    fun setItem(new:List<PaymentListItem>){
        dataSet.clear()
        dataSet = new.toMutableList()
        notifyItemRangeChanged(0, itemCount)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ) = OrderReadyViewItemViewHolder.from(parent)

    override fun onBindViewHolder(holder: OrderReadyViewItemViewHolder, position: Int) {
        holder.bind(dataSet[position], onClick, imageCallBack)
    }

    override fun getItemCount() = dataSet.size


    class OrderReadyViewItemViewHolder(val binding:LayoutOrderLogReadyItemBinding):RecyclerView.ViewHolder(binding.root){
        companion object{
            fun from(parent:ViewGroup):OrderReadyViewItemViewHolder{
                val inflater = LayoutInflater.from(parent.context)
                val v = LayoutOrderLogReadyItemBinding.inflate(inflater, parent, false)

                return OrderReadyViewItemViewHolder(v)
            }
        }

        fun bind(data:PaymentListItem, onClick: (Int) -> Unit, imageCallBack:(ImageView, String)->Unit){
            binding.data = data
            binding.btnDetail.setOnClickListener {
                onClick(data.orderId)
            }
            val adapter = OrderReadyViewSubItemAdapter(imageCallBack)
            binding.rvSub.adapter = adapter
            adapter.setData(data.surveySeqList)
        }
    }
}