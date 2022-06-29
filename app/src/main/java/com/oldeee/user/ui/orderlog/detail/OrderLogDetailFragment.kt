package com.oldeee.user.ui.orderlog.detail

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.oldeee.user.R
import com.oldeee.user.base.BaseFragment
import com.oldeee.user.databinding.FragmentOrderLogDetailBinding
import com.oldeee.user.ui.orderlog.adapter.OrderLogDetailAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OrderLogDetailFragment :
    BaseFragment<FragmentOrderLogDetailBinding, OrderLogDetailViewModel, OrderLogDetailFragmentArgs>() {
    override val layoutId: Int = R.layout.fragment_order_log_detail
    override val viewModel: OrderLogDetailViewModel by viewModels()
    override val navArgs: OrderLogDetailFragmentArgs by navArgs()

    lateinit var adapter: OrderLogDetailAdapter

    override fun initView(savedInstanceState: Bundle?) {
        adapter = OrderLogDetailAdapter { iv, path ->
            viewModel.setImage(iv, path)
        }
        binding.rvSub.adapter = adapter
        binding.tvTitle.text = "신청내역 상세"
    }

    override fun initDataBinding() {
        viewModel.res.observe(viewLifecycleOwner) {
            it?.let {
                binding.skeleton.sfItem.stopShimmer()
                binding.skeleton.sfAddress.stopShimmer()
                binding.skeleton.root.visibility = View.GONE
                binding.llExist.visibility = View.VISIBLE
                adapter.setData(it.surveySeqList)
                binding.data = it
            }
        }
    }

    override fun initViewCreated() {
        binding.skeleton.sfItem.startShimmer()
        binding.skeleton.sfAddress.startShimmer()
        viewModel.postDelay({
            viewModel.requestPaymentDetail(navArgs.orderId)
        }, 500)



    }
}