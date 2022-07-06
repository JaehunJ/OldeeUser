package com.oldee.user.ui.orderlog

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.navigation.NavArgs
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.oldee.user.R
import com.oldee.user.base.BaseFragment
import com.oldee.user.custom.OnScrollEndListener
import com.oldee.user.databinding.FragmentOrderLogViewBinding
import com.oldee.user.ui.orderlog.adapter.OrderLogViewAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OrderLogViewFragment :
    BaseFragment<FragmentOrderLogViewBinding, OrderLogViewViewModel, NavArgs>(),
    SwipeRefreshLayout.OnRefreshListener {
    override val layoutId: Int = R.layout.fragment_order_log_view
    override val viewModel: OrderLogViewViewModel by viewModels()
    override val navArgs: NavArgs by navArgs()

    lateinit var adapter: OrderLogViewAdapter

    override fun initView(savedInstanceState: Bundle?) {
        adapter = OrderLogViewAdapter({ idx ->
            val bundle = bundleOf("orderId" to idx)
            findNavController().navigate(R.id.action_global_orderLogDetailFragment, bundle)
//            findNavController().navigate(R.id.action_global_orderLogDetailFragment)
        }) { iv, path ->
            viewModel.setImage(iv, path)
        }
        binding.rvContainer.adapter = adapter
        binding.rvContainer.addOnScrollListener(OnScrollEndListener {
            //add
            if(viewModel.resSize % 10 == 0){
                addData()
            }
        })
        binding.swList.setOnRefreshListener(this)
    }

    override fun initDataBinding() {
        viewModel.res.observe(viewLifecycleOwner) {
            it?.let {
                if (it.isEmpty() && viewModel.page == 0) {
                    binding.clEmpty.visibility = View.VISIBLE
                    binding.rvContainer.visibility = View.GONE
                } else if (it.isNotEmpty() && viewModel.page == 0) {
                    binding.clEmpty.visibility = View.GONE
                    binding.rvContainer.visibility = View.VISIBLE
                    adapter.setData(it)
                } else if(it.isNotEmpty() && viewModel.page != 1){
                    binding.clEmpty.visibility = View.GONE
                    binding.rvContainer.visibility = View.VISIBLE
                    adapter.addData(it)
                }
            }
        }
    }

    fun addData(){
        viewModel.requestPaymentList(10, viewModel.page+1)
    }

    override fun initViewCreated() {
        viewModel.requestPaymentList(10, 0)
    }

    override fun onRefresh() {
        viewModel.requestPaymentList(10, 0)
        binding.swList.isRefreshing = false
    }
}