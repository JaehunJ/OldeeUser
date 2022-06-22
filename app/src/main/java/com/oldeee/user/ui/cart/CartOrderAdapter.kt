package com.oldeee.user.ui.cart

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.oldeee.user.databinding.LayoutCartOrderItemBinding
import com.oldeee.user.network.response.BasketListItem

class CartOrderAdapter(val checkItem: (data: BasketListItem) -> Unit) :
    RecyclerView.Adapter<CartOrderAdapter.CartOrderViewHolder>() {
    var dataSet = mutableListOf<BasketListItem>()

    fun setData(new: List<BasketListItem>) {
        dataSet.clear()
        dataSet = new.toMutableList()
        notifyItemRangeChanged(0, dataSet.size)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        CartOrderViewHolder.from(parent)

    override fun onBindViewHolder(holder: CartOrderViewHolder, position: Int) {

    }

    override fun getItemCount() = dataSet.size

    class CartOrderViewHolder(val binding: LayoutCartOrderItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        companion object {
            fun from(parent: ViewGroup): CartOrderViewHolder {
                val inflater = LayoutInflater.from(parent.context)
                val bindingView = LayoutCartOrderItemBinding.inflate(inflater, parent, false)

                return CartOrderViewHolder(bindingView)
            }
        }

        fun bind(data: BasketListItem, checkItem: (BasketListItem) -> Unit) {
            binding.data = data
            binding.llSub.setOnClickListener {
                binding.cbSub.isChecked
            }
        }
    }
}