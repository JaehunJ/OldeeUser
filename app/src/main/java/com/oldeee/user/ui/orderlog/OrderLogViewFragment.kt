package com.oldeee.user.ui.orderlog

import android.os.Bundle
import androidx.fragment.app.viewModels
import androidx.navigation.NavArgs
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.oldeee.user.R
import com.oldeee.user.base.BaseFragment
import com.oldeee.user.databinding.FragmentOrderLogViewBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OrderLogViewFragment :
    BaseFragment<FragmentOrderLogViewBinding, OrderLogViewViewModel, NavArgs>() {
    override val layoutId: Int = R.layout.fragment_order_log_view
    override val viewModel: OrderLogViewViewModel by viewModels()
    override val navArgs: NavArgs by navArgs()

    override fun initView(savedInstanceState: Bundle?) {

    }

    override fun initDataBinding() {

    }

}