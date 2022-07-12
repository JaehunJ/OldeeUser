package com.oldee.user.ui.design.add

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.oldee.user.databinding.LayoutOrderAddImageItemBinding

class AddCartAddPhotoAdapter(
    val addCallback: () -> Unit,
    val imageCallBack: (ImageView, Uri) -> Unit,
    val deleteCallBack: (Int) -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    var dataSet = mutableListOf<Uri>()

    val ADD = 0
    val DATA = 1

    fun setData(new: List<Uri>) {
        dataSet.clear()
        dataSet.addAll(new)
        notifyItemRangeChanged(0, dataSet.size)
    }

    fun initData(){
        dataSet.clear()
        notifyDataSetChanged()
    }

    fun addData(uri: Uri) {
        dataSet.add(uri)
        notifyItemInserted(dataSet.size - 1)
    }

    override fun getItemCount() = dataSet.size + 1
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        when (viewType) {
            ADD -> {
                return AddPhotoViewHolder.from(parent)
            }
            else -> {
                return PhotoViewHolder.from(parent)
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is AddPhotoViewHolder) {
            holder.bind {
                addCallback()
            }
        } else if (holder is PhotoViewHolder) {
            holder.bind(imageCallBack, dataSet[position]){
                deleteCallBack.invoke(position)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == dataSet.size) ADD else DATA
    }

    class AddPhotoViewHolder(val binding: LayoutOrderAddImageItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        companion object {
            fun from(parent: ViewGroup): AddPhotoViewHolder {
                val inflater = LayoutInflater.from(parent.context)
                val bind = LayoutOrderAddImageItemBinding.inflate(inflater, parent, false)
                return AddPhotoViewHolder(bind)
            }
        }

        fun bind(click: () -> Unit) {
            binding.clContainer.setOnClickListener {
                click()
            }

            binding.clAdd.visibility = View.VISIBLE
            binding.clPhoto.visibility = View.INVISIBLE
        }
    }

    class PhotoViewHolder(val binding: LayoutOrderAddImageItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        companion object {
            fun from(parent: ViewGroup): PhotoViewHolder {
                val inflater = LayoutInflater.from(parent.context)
                val bind = LayoutOrderAddImageItemBinding.inflate(inflater, parent, false)
                return PhotoViewHolder(bind)
            }
        }

        fun bind(imageCallBack: (ImageView, Uri) -> Unit, uri: Uri, deleteCallBack: () -> Unit) {
            binding.clContainer.setOnClickListener {

            }
            binding.ivDelete.setOnClickListener {
                deleteCallBack.invoke()
            }
            binding.clAdd.visibility = View.INVISIBLE
            binding.clPhoto.visibility = View.VISIBLE
            imageCallBack(binding.ivImage, uri)
        }
    }
}