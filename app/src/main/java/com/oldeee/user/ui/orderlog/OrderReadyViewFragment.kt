package com.oldeee.user.ui.orderlog

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavArgs
import androidx.navigation.fragment.navArgs
import com.oldeee.user.R
import com.oldeee.user.base.BaseFragment
import com.oldeee.user.databinding.FragmentOrderReadyViewBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OrderReadyViewFragment : BaseFragment<FragmentOrderReadyViewBinding, OrderReadyViewViewModel, NavArgs>() {
    override val layoutId: Int = R.layout.fragment_order_ready_view
    override val viewModel: OrderReadyViewViewModel by viewModels()
    override val navArgs: NavArgs by navArgs()

    override fun initView(savedInstanceState: Bundle?) {

    }

    override fun initDataBinding() {

    }

    override fun initViewCreated() {

    }

}