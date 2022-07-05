package com.oldee.user.ui.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.oldee.user.databinding.LayoutDesignListItemBinding
import com.oldee.user.network.response.DesignListItem

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

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)=DesignListItemViewHolder.from(parent)

    override fun onBindViewHolder(holder: DesignListItemViewHolder, position: Int) {
        holder.bind(dataSet[position], navigateCallBack, imageCallback)
    }

    class DesignListItemViewHolder(val binding: LayoutDesignListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        companion object {
            fun from(parent: ViewGroup): DesignListItemViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val bind = LayoutDesignListItemBinding.inflate(layoutInflater, parent, false)

                return DesignListItemViewHolder(bind)
            }
        }

        fun bind(data: DesignListItem, navigateCallback:(Int)->Unit, imageCallBack: (ImageView, String) -> Unit) {
            binding.res = data
            data.mainImageName?.let{
                imageCallBack(binding.ivImage, it)
            }
            binding.clRoot.setOnClickListener {
                navigateCallback(data.reformId)
            }
        }
    }



//    class DesignListItemViewHolder(val binding: LayoutHomeDesignItemBinding) :
//        RecyclerView.ViewHolder(binding.root) {
//        companion object {
//            fun from(parent: ViewGroup): DesignListItemViewHolder {
//                val layoutInflater = LayoutInflater.from(parent.context)
//                val bind = LayoutHomeDesignItemBinding.inflate(layoutInflater, parent, false)
//                return DesignListItemViewHolder(bind)
//            }
//        }
//
//        fun bind(
//            data: DesignListItem,
//            navigateCallBack: (Int) -> Unit,
//            imageCallback: (ImageView, String) -> Unit
//        ) {
//            binding.res = data
//            binding.clRoot.setOnClickListener {
//                navigateCallBack(data.reformId)
//            }
//
//            imageCallback(binding.ivBefore, data.beforeImageName)
//            imageCallback(binding.ivAfter, data.afterImageName)
//        }
//    }
}