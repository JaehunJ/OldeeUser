package com.oldee.user.ui.design

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.oldee.user.databinding.LayoutHomeDesignItemBinding
import com.oldee.user.network.response.DesignListItem

class DesignListAdapter(
    val navigateCallback: (Int) -> Unit,
    val imageCallBack: (ImageView, String) -> Unit
) :
    ListAdapter<DesignListItem, DesignListAdapter.DesignListItemViewHolder>(DesignItemDiffCallback()) {
//    var dataSet = mutableListOf<DesignListItem>()
//
//    fun setData(list: MutableList<DesignListItem>) {
//        dataSet = list.toMutableList()
//        val size = dataSet.size
//        notifyItemRangeChanged(0, size)
//    }
//
//    fun removeAll() {
//        val size = dataSet.size
//        dataSet.clear()
//        notifyItemRangeRemoved(0, size)
//    }
//
//    fun addData(newData: MutableList<DesignListItem>) {
//        if (dataSet.size == 0) {
//            setData(newData)
//            return
//        }
//
//        val orgSize = dataSet.size
//        newData.forEach {
//            dataSet.add(it)
//        }
//        notifyItemRangeInserted(orgSize, newData.size)
//    }

    init {
        setHasStableIds(true)
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        DesignListItemViewHolder.from(parent)

    override fun onBindViewHolder(holder: DesignListItemViewHolder, position: Int) {

        holder.bind(getItem(position), navigateCallback, imageCallBack)
    }

//    override fun getItemCount() = dataSet.size

    class DesignListItemViewHolder(val binding: LayoutHomeDesignItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        companion object {
            fun from(parent: ViewGroup): DesignListItemViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val bind = LayoutHomeDesignItemBinding.inflate(layoutInflater, parent, false)
                return DesignListItemViewHolder(bind)
            }
        }

        fun bind(
            data: DesignListItem,
            navigateCallBack: (Int) -> Unit,
            imageCallback: (ImageView, String) -> Unit
        ) {
            binding.res = data
            binding.clRoot.setOnClickListener {
                navigateCallBack(data.reformId)
            }
            binding.ivBefore.setImageDrawable(null)
            binding.ivAfter.setImageDrawable(null)

            imageCallback(binding.ivBefore, data.beforeImageName)
            imageCallback(binding.ivAfter, data.afterImageName)
        }
    }

    class DesignItemDiffCallback : DiffUtil.ItemCallback<DesignListItem>() {
        override fun areItemsTheSame(oldItem: DesignListItem, newItem: DesignListItem): Boolean {
            return oldItem.reformId == newItem.reformId
        }

        override fun areContentsTheSame(oldItem: DesignListItem, newItem: DesignListItem): Boolean {
            return oldItem == newItem
        }
    }
}