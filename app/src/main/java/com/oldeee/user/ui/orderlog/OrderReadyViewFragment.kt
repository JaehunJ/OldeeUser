package com.oldeee.user.ui.orderlog

import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavArgs
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.oldeee.user.R
import com.oldeee.user.base.BaseFragment
import com.oldeee.user.databinding.FragmentOrderReadyViewBinding
import com.oldeee.user.ui.orderlog.adapter.OrderReadyViewAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OrderReadyViewFragment : BaseFragment<FragmentOrderReadyViewBinding, OrderReadyViewViewModel, NavArgs>() {
    override val layoutId: Int = R.layout.fragment_order_ready_view
    override val viewModel: OrderReadyViewViewModel by viewModels()
    override val navArgs: NavArgs by navArgs()

    lateinit var adapter : OrderReadyViewAdapter

    override fun initView(savedInstanceState: Bundle?) {
        adapter = OrderReadyViewAdapter({ idx->
            val bundle = bundleOf("orderId" to idx)
            findNavController().navigate(R.id.action_global_orderLogDetailFragment, bundle)
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
                    adapter.setItem(it)
                }

            }
        }
    }

    override fun initViewCreated() {
        viewModel.requestPaymentList()
    }

}