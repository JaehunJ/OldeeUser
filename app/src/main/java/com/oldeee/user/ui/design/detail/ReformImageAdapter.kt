package com.oldeee.user.ui.design.detail

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.oldeee.user.databinding.LayoutDesignDetailImageBinding

class ReformImageAdapter(val imageCallback: (ImageView, String) -> Unit) :
    RecyclerView.Adapter<ReformImageAdapter.ReformImageViewHolder>() {
    private var dataSet = listOf<String>()

    fun setData(newSet: List<String>) {
        dataSet = newSet
        notifyItemRangeChanged(0, dataSet.size)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ReformImageViewHolder.from(parent)

    override fun onBindViewHolder(holder: ReformImageViewHolder, position: Int) {
        holder.bind(dataSet[position], imageCallback)
    }

    override fun getItemCount() = dataSet.size

    class ReformImageViewHolder(val binding: LayoutDesignDetailImageBinding) :
        RecyclerView.ViewHolder(binding.root) {
        companion object {
            fun from(parent: ViewGroup): ReformImageViewHolder {
                val inflater = LayoutInflater.from(parent.context)
                val bind = LayoutDesignDetailImageBinding.inflate(inflater, parent, false)

                return ReformImageViewHolder(bind)
            }
        }

        fun bind(path: String, imageCallback: (ImageView, String) -> Unit) {
            imageCallback(binding.ivImage, path)
        }
    }
}