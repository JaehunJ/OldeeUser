package com.oldee.user.ui.home.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.oldee.user.R
import com.oldee.user.databinding.LayoutHomeDesignerItemBinding

class ExpertListAdapter(val imageCallBack:(ImageView, String)->Unit) : RecyclerView.Adapter<ExpertListAdapter.ExpertListItemViewHolder>() {
    var dataSet = listOf<com.oldee.user.network.response.ExpertListItem>()

    fun setData(new: List<com.oldee.user.network.response.ExpertListItem>) {
        dataSet = new
        val size = dataSet.size
        notifyItemRangeChanged(0, size)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
        ExpertListItemViewHolder.from(parent)

    override fun onBindViewHolder(holder: ExpertListItemViewHolder, position: Int) {
        holder.bind(dataSet[position], imageCallBack)
    }

    override fun getItemCount(): Int {
        return dataSet.size
    }

    class ExpertListItemViewHolder(val binding: LayoutHomeDesignerItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        companion object {
            fun from(parent: ViewGroup): ExpertListItemViewHolder {
                val inflater = LayoutInflater.from(parent.context)
                val bind = LayoutHomeDesignerItemBinding.inflate(inflater, parent, false)

                return ExpertListItemViewHolder(bind)
            }
        }

        fun bind(data: com.oldee.user.network.response.ExpertListItem, imageCallBack: (ImageView, String) -> Unit) {
            binding.res = data
            if(data.expertPrifileImg == null){
                Glide.with(binding.ivAvatar.context).load(R.drawable.ic_profile_default).apply(RequestOptions().circleCrop()).into(binding.ivAvatar)
            }else{
                imageCallBack(binding.ivAvatar, data.expertPrifileImg)
            }

//            binding.ivAvatar.
        }
    }
}