package com.oldeee.user.ui.orderlog

import android.os.Bundle
import androidx.fragment.app.viewModels
import androidx.navigation.NavArgs
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.oldeee.user.R
import com.oldeee.user.base.BaseFragment
import com.oldeee.user.databinding.FragmentOrderLogBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OrderLogFragment : BaseFragment<FragmentOrderLogBinding, OrderLogViewModel, NavArgs>() {
    override val layoutId: Int = R.layout.fragment_order_log
    override val viewModel: OrderLogViewModel by viewModels()
    override val navArgs: NavArgs by navArgs()

    override fun initView(savedInstanceState: Bundle?) {
        binding.ivBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    override fun initDataBinding() {

    }

}