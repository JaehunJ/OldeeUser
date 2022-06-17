package com.oldeee.user.ui.design

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.NavArgs
import androidx.navigation.fragment.navArgs
import com.oldeee.user.R
import com.oldeee.user.base.BaseFragment
import com.oldeee.user.custom.OnScrollEndListener
import com.oldeee.user.databinding.FragmentDesignListBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DesignListFragment : BaseFragment<FragmentDesignListBinding, DesignListViewModel, NavArgs>() {
    override val layoutId: Int = R.layout.fragment_design_list
    override val viewModel: DesignListViewModel by viewModels()
    override val navArgs: NavArgs by navArgs()

    lateinit var adapter: DesignListAdapter

    var limit = 10
    var page = 0

    override fun initView(savedInstanceState: Bundle?) {
        adapter = DesignListAdapter { iv, str ->
            viewModel.setImage(iv, str)
        }
        binding.rvDesignList.adapter = adapter

        binding.rvDesignList.addOnScrollListener(OnScrollEndListener{
            page += 1
            viewModel.requestDesignList(limit, page)
        })
    }

    override fun initDataBinding() {
        viewModel.listResponse.observe(viewLifecycleOwner){
            it?.let{
                adapter.addData(it)
            }
        }
    }


    override fun initViewCreated() {
        limit = 0
        page = 0
        viewModel.requestDesignList(limit, page)
    }
}