package com.oldeee.user.ui.orderlog

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.navigation.NavArgs
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.oldeee.user.R
import com.oldeee.user.base.BaseFragment
import com.oldeee.user.databinding.FragmentOrderLogViewBinding
import com.oldeee.user.ui.orderlog.adapter.OrderLogViewAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OrderLogViewFragment :
    BaseFragment<FragmentOrderLogViewBinding, OrderLogViewViewModel, NavArgs>() {
    override val layoutId: Int = R.layout.fragment_order_log_view
    override val viewModel: OrderLogViewViewModel by viewModels()
    override val navArgs: NavArgs by navArgs()

    lateinit var adapter : OrderLogViewAdapter

    override fun initView(savedInstanceState: Bundle?) {
        adapter = OrderLogViewAdapter({idx->
            val bundle = bundleOf("orderId" to idx)
            findNavController().navigate(R.id.action_global_orderLogDetailFragment, bundle)
//            findNavController().navigate(R.id.action_global_orderLogDetailFragment)
        }){iv, path->
            viewModel.setImage(iv, path)
        }
        binding.rvContainer.adapter = adapter
    }

    override fun initDataBinding() {
        viewModel.res.observe(viewLifecycleOwner){
            it?.let{
                if(it.isEmpty()){
                    binding.clEmpty.visibility = View.VISIBLE
                    binding.rvContainer.visibility = View.GONE
                }else{
                    binding.clEmpty.visibility = View.GONE
                    binding.rvContainer.visibility = View.VISIBLE
                    adapter.setData(it)
                }
            }
        }
    }

    override fun initViewCreated() {
        viewModel.requestPaymentList()
    }
}