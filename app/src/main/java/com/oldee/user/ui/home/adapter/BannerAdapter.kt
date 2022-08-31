package com.oldee.user.ui.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.oldee.user.databinding.LayoutHomeBannerItemBinding

class BannerAdapter(val onClick:(Int)->Unit) : RecyclerView.Adapter<BannerAdapter.BannerViewHolder>() {
    private var dataSet = listOf<Int>()

    fun setData(new: List<Int>) {
        dataSet = new
        notifyItemRangeChanged(0, dataSet.size)
    }

    class BannerViewHolder(val binding: LayoutHomeBannerItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        companion object {
            fun from(parent: ViewGroup): BannerViewHolder {
                val inflater = LayoutInflater.from(parent.context)
                val bind = LayoutHomeBannerItemBinding.inflate(inflater, parent, false)

                return BannerViewHolder(bind)
            }
        }

        fun bind(imageId: Int, position:Int, onClick: (Int) -> Unit) {
            binding.ivBanner.setImageResource(imageId)
            binding.ivBanner.setOnClickListener {
                onClick(position)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        BannerViewHolder.from(parent)

    override fun onBindViewHolder(holder: BannerViewHolder, position: Int) {
        holder.bind(dataSet[position % dataSet.size], position, onClick)
    }

    override fun getItemCount() = Int.MAX_VALUE
}