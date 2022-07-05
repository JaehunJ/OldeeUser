package com.oldee.user.ui.notice

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.oldee.user.databinding.LayoutNoticeListItemBinding
import com.oldee.user.network.response.NoticeResponseData

class NoticeListAdapter:RecyclerView.Adapter<NoticeListAdapter.NoticeListViewHolder>() {
    var dataSet = listOf<NoticeResponseData>()

    fun setData(new:List<NoticeResponseData>){
        dataSet = new
        notifyItemRangeChanged(0, dataSet.size)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int)= NoticeListViewHolder.from(parent)

    override fun onBindViewHolder(holder: NoticeListViewHolder, position: Int) {
        holder.bind(dataSet[position])
    }

    override fun getItemCount() = dataSet.size

    class NoticeListViewHolder(val binding:LayoutNoticeListItemBinding):RecyclerView.ViewHolder(binding.root){
        companion object{
            fun from(parent: ViewGroup):NoticeListViewHolder{
                val layoutInflater = LayoutInflater.from(parent.context)
                val view = LayoutNoticeListItemBinding.inflate(layoutInflater, parent, false)
                return NoticeListViewHolder(view)
            }
        }

        fun bind(data:NoticeResponseData){
            binding.tvTitle.text = data.title
            binding.tvContents.text = data.contents
            binding.tvDate.text = data.creationDate
        }
    }
}