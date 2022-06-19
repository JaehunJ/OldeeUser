package com.oldeee.user.ui.design.order

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.oldeee.user.databinding.LayoutOrderAddImageItemBinding

class OrderAddPhotoAdapter(val imageCallBack:(ImageView, String)->Unit, val deleteCallBack:(Int)) :RecyclerView.Adapter<OrderAddPhotoAdapter.AddPhotoViewHolder>() {
    val dataSet = mutableListOf<String>()

    override fun getItemCount() = dataSet.size
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = AddPhotoViewHolder.from(parent)
    override fun onBindViewHolder(holder: AddPhotoViewHolder, position: Int) {
        holder.bind(dataSet[position], dataSet.size - 1 == position)
    }

    class AddPhotoViewHolder(val binding:LayoutOrderAddImageItemBinding):RecyclerView.ViewHolder(binding.root){
        companion object{
            fun from(parent:ViewGroup):AddPhotoViewHolder{
                val inflater = LayoutInflater.from(parent.context)
                val bind = LayoutOrderAddImageItemBinding.inflate(inflater, parent, false)
                return  AddPhotoViewHolder(bind)
            }
        }

        fun bind(path:String, isEnd:Boolean){

        }
    }
}