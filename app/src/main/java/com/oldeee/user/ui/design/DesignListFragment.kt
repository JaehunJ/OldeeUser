package com.oldeee.user.ui.design

import android.os.Bundle
import androidx.fragment.app.viewModels
import androidx.navigation.NavArgs
import androidx.navigation.fragment.navArgs
import com.oldeee.user.R
import com.oldeee.user.base.BaseFragment
import com.oldeee.user.databinding.FragmentDesignListBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DesignListFragment : BaseFragment<FragmentDesignListBinding, DesignListViewModel, NavArgs>() {
    override val layoutId: Int
        get() = R.layout.fragment_design_list
    override val viewModel: DesignListViewModel by viewModels()
    override val navArgs: NavArgs by navArgs()

    override fun initView(savedInstanceState: Bundle?) {

    }

    override fun initDataBinding() {

    }

}