package com.oldeee.user.ui.orderlog.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.oldeee.user.databinding.LayoutOrderLogItemBinding
import com.oldeee.user.network.response.PaymentListItem

class OrderLogViewAdapter(
    val onClick: (Int) -> Unit,
    val imageCallBack: (ImageView, String) -> Unit
) : RecyclerView.Adapter<OrderLogViewAdapter.OrderLogViewItemViewHolder>() {
    var dataSet = mutableListOf<PaymentListItem>()

    fun setData(new: List<PaymentListItem>) {
        dataSet.clear()
        dataSet = new.toMutableList()
        notifyItemRangeChanged(0, dataSet.size)
    }

    fun addData(new:List<PaymentListItem>){
        val orgSize = dataSet.size
        new.forEach {
            dataSet.add(it)
        }
        notifyItemRangeInserted(orgSize, new.size)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        OrderLogViewItemViewHolder.from(parent)

    override fun onBindViewHolder(holder: OrderLogViewItemViewHolder, position: Int) {
        holder.bind(dataSet[position], onClick, imageCallBack)
    }

    override fun getItemCount() = dataSet.size

    class OrderLogViewItemViewHolder(val binding: LayoutOrderLogItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        companion object {
            fun from(parent: ViewGroup): OrderLogViewItemViewHolder {
                val inflater = LayoutInflater.from(parent.context)
                val v = LayoutOrderLogItemBinding.inflate(inflater, parent, false)

                return OrderLogViewItemViewHolder(v)
            }
        }

        fun bind(
            data: PaymentListItem,
            onClick: (Int) -> Unit,
            imageCallBack: (ImageView, String) -> Unit
        ) {
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
