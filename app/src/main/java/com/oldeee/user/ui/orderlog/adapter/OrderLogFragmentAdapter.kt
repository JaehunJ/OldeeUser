package com.oldeee.user.ui.orderlog.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class OrderLogFragmentAdapter(fragmentActivity: FragmentActivity):FragmentStateAdapter(fragmentActivity) {
    var list:List<Fragment> = listOf<Fragment>()

    override fun getItemCount(): Int {
        return list.size
    }

    override fun createFragment(position: Int): Fragment {
        return list[position]
    }

    fun setFragmentList(new:List<Fragment>){
        list = new
        notifyItemRangeChanged(0, list.size)
    }
}