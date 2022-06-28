package com.oldeee.user.ui.orderlog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import androidx.fragment.app.viewModels
import androidx.navigation.NavArgs
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import com.oldeee.user.R
import com.oldeee.user.base.BaseFragment
import com.oldeee.user.databinding.FragmentOrderLogBinding
import com.oldeee.user.databinding.LayoutOrderLogTabBinding
import com.oldeee.user.ui.orderlog.adapter.OrderLogFragmentAdapter
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class OrderLogFragment : BaseFragment<FragmentOrderLogBinding, OrderLogViewModel, NavArgs>() {
    companion object{
        val READY = 0
        val LOGGING = 1
    }

    override val layoutId: Int = R.layout.fragment_order_log
    override val viewModel: OrderLogViewModel by viewModels()
    override val navArgs: NavArgs by navArgs()

    val titleList = listOf<String>("주문내역","신청중")
    lateinit var tabList : List<LayoutOrderLogTabBinding>

//    @Inject var logViewFragment:OrderLogViewFragment
//
//    @Inject var readyViewFragment:OrderReadyViewFragment

    override fun initView(savedInstanceState: Bundle?) {
        initTab()

        binding.vpPage.adapter = OrderLogFragmentAdapter(requireActivity())

//        binding.tbTop.setupWithViewPager(binding.vpPage)

        tabList = listOf(createTabView(titleList[0]),createTabView(titleList[1]))

        TabLayoutMediator(binding.tbTop, binding.vpPage) { tab, pos ->
            tab.customView = tabList[pos].root
            setTabSelected(tab, tab.isSelected)
        }.attach()
    }

    override fun initDataBinding() {

    }

    override fun initViewCreated() {

    }

    fun initTab(){
        val tab1binding = createTabView("주문내역")
        val tab2binding = createTabView("신청중")
        val tab1 = binding.tbTop.newTab().setCustomView(tab1binding.root)
        val tab2 = binding.tbTop.newTab().setCustomView(tab2binding.root)
        binding.tbTop.addTab(tab1)
        binding.tbTop.addTab(tab2)
        binding.tbTop.addOnTabSelectedListener(object :TabLayout.OnTabSelectedListener{
            override fun onTabSelected(tab: TabLayout.Tab?) {
                setTabSelected(tab, true)
            }
            override fun onTabUnselected(tab: TabLayout.Tab?) {
                setTabSelected(tab, false)
            }
            override fun onTabReselected(tab: TabLayout.Tab?) {}
        })
    }

    fun createTabView(title:String): LayoutOrderLogTabBinding {
        val inflater = LayoutInflater.from(requireContext())
        val bind = LayoutOrderLogTabBinding.inflate(inflater)

        bind.tvItemName.text = title

        return bind
    }

    fun setTabSelected(tab:TabLayout.Tab?, isSelected: Boolean){
        val tv = tab?.view?.findViewById<TextView>(R.id.tv_item_name)
        tv?.setTextAppearance(if(isSelected) R.style.BoldText else R.style.NormalText)
    }
}