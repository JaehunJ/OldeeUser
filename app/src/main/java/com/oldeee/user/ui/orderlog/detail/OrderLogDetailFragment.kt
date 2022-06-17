package com.oldeee.user.ui.orderlog.detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.NavArgs
import androidx.navigation.fragment.navArgs
import com.oldeee.user.R
import com.oldeee.user.base.BaseFragment
import com.oldeee.user.databinding.FragmentOrderLogDetailBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class OrderLogDetailFragment : BaseFragment<FragmentOrderLogDetailBinding, OrderLogDetailViewModel, NavArgs>() {
    override val layoutId: Int = R.layout.fragment_order_log_detail
    override val viewModel: OrderLogDetailViewModel by viewModels()
    override val navArgs: NavArgs by navArgs()

    override fun initView(savedInstanceState: Bundle?) {

    }

    override fun initDataBinding() {

    }

    override fun initViewCreated() {

    }

}