package com.oldeee.user.ui.design.detail

import android.os.Bundle
import androidx.fragment.app.viewModels
import androidx.navigation.NavArgs
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.oldeee.user.R
import com.oldeee.user.base.BaseFragment
import com.oldeee.user.databinding.FragmentReformDetailBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ReformDetailFragment :
    BaseFragment<FragmentReformDetailBinding, ReformDetailViewModel, NavArgs>() {
    override val layoutId: Int = R.layout.fragment_reform_detail
    override val viewModel: ReformDetailViewModel by viewModels()
    override val navArgs: NavArgs by navArgs()

    override fun initView(savedInstanceState: Bundle?) {
        binding.ivBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }

    override fun initDataBinding() {

    }
}