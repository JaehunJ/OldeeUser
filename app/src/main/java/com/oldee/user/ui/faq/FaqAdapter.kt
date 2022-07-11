package com.oldee.user.ui.faq

import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.oldee.user.databinding.LayoutFaqItemBinding

class FaqAdapter:RecyclerView.Adapter<FaqAdapter.FaqViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FaqViewHolder {
        TODO("Not yet implemented")
    }

    override fun onBindViewHolder(holder: FaqViewHolder, position: Int) {
        TODO("Not yet implemented")
    }

    override fun getItemCount(): Int {
        TODO("Not yet implemented")
    }


    class FaqViewHolder(val binding:LayoutFaqItemBinding):RecyclerView.ViewHolder(binding.root){
        companion object{
            fun from(parent:ViewGroup):FaqViewHolder{
                val v = LayoutFaqItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)

                return FaqViewHolder(v)
            }
        }

        fun bind(data:NoticeData){
//            binding.title = data.title
//            binding.contents = data.contents
            binding.clArr.setOnClickListener {
                binding.cbArr.isChecked = !binding.cbArr.isChecked
            }
            binding.cbArr.setOnCheckedChangeListener { compoundButton, b ->
                if(b){
                    binding.tvContents.visibility = View.VISIBLE
                }else{
                    binding.tvContents.visibility = View.GONE
                }
            }
        }
    }

    data class NoticeData(val title:String, val contents:String)



//    class FaqViewHolderDecoration:RecyclerView.ItemDecoration(){
//        private val paint = Paint()
//    }
}