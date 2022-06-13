package com.oldeee.user.ui.home

import android.os.Bundle
import androidx.fragment.app.viewModels
import androidx.navigation.NavArgs
import androidx.navigation.fragment.navArgs
import com.oldeee.user.R
import com.oldeee.user.base.BaseFragment
import com.oldeee.user.databinding.FragmentHomeBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding, HomeViewModel, NavArgs>() {
    override val layoutId: Int = R.layout.fragment_home
    override val viewModel: HomeViewModel by viewModels()
    override val navArgs: NavArgs by navArgs()

    override fun initView(savedInstanceState: Bundle?) {
        binding.ivDrawer.setOnClickListener {
            activityFuncFunction.openDrawerMenu()
        }
    }

    override fun initDataBinding() {

    }

}