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
import com.oldee.user.databinding.FragmentOrderReadyViewBinding
import com.oldee.user.ui.orderlog.adapter.OrderReadyViewAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OrderReadyViewFragment : BaseFragment<FragmentOrderReadyViewBinding, OrderReadyViewViewModel, NavArgs>(), SwipeRefreshLayout.OnRefreshListener {
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
        binding.rvContainer.addOnScrollListener(OnScrollEndListener{
            //add
            if(viewModel.resSize % 10 == 0){
                addData()
            }
        })
    }

    override fun initDataBinding() {
        viewModel.res.observe(viewLifecycleOwner){
            it?.let{
                if(it.isEmpty() && viewModel.page == 0){
                    binding.clEmpty.visibility = View.VISIBLE
                    binding.rvContainer.visibility = View.GONE
                }else if(it.isNotEmpty() && viewModel.page == 0){
                    binding.clEmpty.visibility = View.GONE
                    binding.rvContainer.visibility = View.VISIBLE
                    adapter.setItem(it)
                }else if(it.isNotEmpty() && viewModel.page != 1){
                    //add
                    binding.clEmpty.visibility = View.GONE
                    binding.rvContainer.visibility = View.VISIBLE
                    adapter.addItem(it)
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
    }

}