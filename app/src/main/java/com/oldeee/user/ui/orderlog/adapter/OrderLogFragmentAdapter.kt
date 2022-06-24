package com.oldeee.user.ui.orderlog.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.oldeee.user.ui.orderlog.OrderLogViewFragment
import com.oldeee.user.ui.orderlog.OrderReadyViewFragment

class OrderLogFragmentAdapter(fragmentActivity: FragmentActivity): FragmentStateAdapter(fragmentActivity) {

    override fun getItemCount() = 2

    override fun createFragment(position: Int): Fragment {
        return when(position){
            0->OrderLogViewFragment()
            else->OrderReadyViewFragment()
        }
    }
}