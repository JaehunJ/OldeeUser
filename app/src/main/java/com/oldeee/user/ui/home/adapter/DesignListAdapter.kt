package com.oldeee.user.ui.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.oldeee.user.databinding.LayoutHomeDesignItemBinding
import com.oldeee.user.network.response.DesignListItem

class DesignListAdapter(
    val navigateCallBack: (Int) -> Unit,
    val imageCallback: (ImageView, String) -> Unit
) : RecyclerView.Adapter<DesignListAdapter.DesignListItemViewHolder>() {

    var dataSet = listOf<DesignListItem>()

    fun setData(list: List<DesignListItem>) {
        dataSet = list
        notifyItemRangeChanged(0, dataSet.size)
    }

    override fun getItemCount(): Int {
        return dataSet.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        DesignListItemViewHolder.from(parent)

    override fun onBindViewHolder(holder: DesignListItemViewHolder, position: Int) {
        holder.bind(dataSet[position], navigateCallBack, imageCallback)
    }

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

            imageCallback(binding.ivBefore, data.beforeImageName)
            imageCallback(binding.ivAfter, data.afterImageName)
        }
    }
}