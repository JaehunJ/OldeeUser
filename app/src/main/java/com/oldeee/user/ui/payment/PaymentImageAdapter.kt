package com.oldeee.user.ui.payment

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.oldeee.user.databinding.LayoutPaymentItemImageBinding

class PaymentImageAdapter(val imageCallback: (ImageView, String) -> Unit) :
    RecyclerView.Adapter<PaymentImageAdapter.PaymentImageViewHolder>() {

    var dataSet = listOf<String>()

    fun setData(new: List<String>) {
        dataSet = new
        notifyItemRangeChanged(0, dataSet.size)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        PaymentImageViewHolder.from(parent)

    override fun onBindViewHolder(holder: PaymentImageViewHolder, position: Int) {
        holder.bind(imageCallback, dataSet[position])
    }

    override fun getItemCount() = dataSet.size

    class PaymentImageViewHolder(val binding: LayoutPaymentItemImageBinding) :
        RecyclerView.ViewHolder(binding.root) {
        companion object {
            fun from(parent: ViewGroup): PaymentImageViewHolder {
                val inflater = LayoutInflater.from(parent.context)
                val viewBinding = LayoutPaymentItemImageBinding.inflate(inflater, parent, false)
                return PaymentImageViewHolder(viewBinding)
            }
        }

        fun bind(imageCallback: (ImageView, String) -> Unit, path: String) {
            imageCallback(binding.ivImage, path)
        }
    }
}