package com.oldee.user.ui.payment.done

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.oldee.user.databinding.LayoutPaymentDoneItemBinding
import com.oldee.user.network.response.SurveySeqListItem

class PaymentItemAdapter(val imageCallBack: (ImageView, String) -> Unit) :
    RecyclerView.Adapter<PaymentItemAdapter.PaymentDoneItemViewHolder>() {
    var datas = listOf<SurveySeqListItem>()

    fun setData(new: List<SurveySeqListItem>) {
        datas = new.toMutableList()
        notifyItemRangeChanged(0, datas.size)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        PaymentDoneItemViewHolder.from(parent)

    override fun onBindViewHolder(holder: PaymentDoneItemViewHolder, position: Int) {
        holder.bind(datas[position], imageCallBack)
    }

    override fun getItemCount() = datas.size

    class PaymentDoneItemViewHolder(val binding: LayoutPaymentDoneItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        companion object {
            fun from(parent: ViewGroup): PaymentDoneItemViewHolder {
                val _v = LayoutPaymentDoneItemBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )

                return PaymentDoneItemViewHolder(_v)
            }
        }

        fun bind(data: SurveySeqListItem, imageCallBack: (ImageView, String) -> Unit) {
            imageCallBack(binding.ivImage, data.imagePath)
            binding.data = data
        }
    }
}