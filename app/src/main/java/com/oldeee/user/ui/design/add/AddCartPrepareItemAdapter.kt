package com.oldeee.user.ui.design.add

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.oldeee.user.data.PrepareItemMappingStringList
import com.oldeee.user.databinding.LayoutOrderCheckPrepareItemBinding
import com.oldeee.user.ui.design.detail.PrepareItem

class AddCartPrepareItemAdapter(val onClick: (String, Boolean) -> Unit) :
    RecyclerView.Adapter<AddCartPrepareItemAdapter.PrepareItemViewHolder>() {
    var dataSet = listOf<PrepareItem>()

    fun setData(new: List<PrepareItem>) {
        dataSet = new
        notifyItemRangeChanged(0, dataSet.size)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        PrepareItemViewHolder.from(parent)

    override fun onBindViewHolder(holder: PrepareItemViewHolder, position: Int) {
        holder.bind(dataSet[position], onClick)
    }

    override fun getItemCount() = dataSet.size

    class PrepareItemViewHolder(val binding: LayoutOrderCheckPrepareItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        companion object {
            val enableAlpha = 1f
            val disableAlpha = 0.4f

            fun from(parent: ViewGroup): PrepareItemViewHolder {
                val inflater = LayoutInflater.from(parent.context)
                val bind = LayoutOrderCheckPrepareItemBinding.inflate(inflater, parent, false)
                return PrepareItemViewHolder(bind)
            }
        }

        fun bind(data: PrepareItem, onClick:(String, Boolean)->Unit) {
            binding.clRoot.setOnClickListener {
                binding.cbPrepare.isChecked = !binding.cbPrepare.isChecked
                Log.e("#debug","$binding.cbPrepare.isChecked")
            }
            binding.cbPrepare.setOnCheckedChangeListener { buttonView, isChecked ->
                binding.clRoot.alpha = if(isChecked) enableAlpha else disableAlpha
                onClick.invoke(data.code, isChecked)
            }
            val resId = PrepareItemMappingStringList[data.code]
            resId?.let{ id->
                binding.ivImage.setImageResource(id)
            }
            binding.tvName.text = data.name
        }
    }
}