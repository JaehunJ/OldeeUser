package com.oldee.user.ui.error

import android.os.Bundle
import androidx.fragment.app.viewModels
import androidx.navigation.NavArgs
import androidx.navigation.fragment.navArgs
import com.oldee.user.R
import com.oldee.user.base.BaseFragment
import com.oldee.user.databinding.FragmentNetworkErrorBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NetworkErrorFragment :
    BaseFragment<FragmentNetworkErrorBinding, NetworkErrorViewModel, NavArgs>() {
    override val layoutId: Int = R.layout.fragment_network_error
    override val viewModel: NetworkErrorViewModel by viewModels()
    override val navArgs: NavArgs by navArgs()

    override fun initView(savedInstanceState: Bundle?) {
        binding.btnHome.setOnClickListener {
            activityFuncFunction.logout()
        }
    }

    override fun initDataBinding() {

    }

    override fun initViewCreated() {

    }

}