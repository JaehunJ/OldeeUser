package com.oldee.user.ui.cart

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.oldee.user.databinding.LayoutCartOrderItemBinding
import com.oldee.user.network.response.BasketListItem

class CartOrderAdapter(
    val checkedChange: (position: Int, checked: Boolean) -> Unit,
    val checkItem: (data: BasketListItem) -> Unit
) :
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
        holder.bind(position, dataSet[position], checkedChange, checkItem)
    }

    override fun getItemCount() = dataSet.size

//    fun getItem() = getAda

    class CartOrderViewHolder(val binding: LayoutCartOrderItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        companion object {
            fun from(parent: ViewGroup): CartOrderViewHolder {
                val inflater = LayoutInflater.from(parent.context)
                val bindingView = LayoutCartOrderItemBinding.inflate(inflater, parent, false)

                return CartOrderViewHolder(bindingView)
            }
        }

        fun bind(
            position: Int,
            data: BasketListItem,
            checkedChange: (Int, Boolean) -> kotlin.Unit,
            checkItem: (BasketListItem) -> Unit
        ) {
            binding.data = data
            binding.llSub.setOnClickListener {
                binding.cbSub.isChecked
            }
            binding.cbSub.setOnCheckedChangeListener { compoundButton, b ->
                checkedChange(position, b)
            }
        }
    }
}