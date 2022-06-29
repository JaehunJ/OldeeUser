package com.oldeee.user.ui.design

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.NavArgs
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.oldeee.user.R
import com.oldeee.user.base.BaseFragment
import com.oldeee.user.custom.OnScrollEndListener
import com.oldeee.user.databinding.FragmentDesignListBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DesignListFragment : BaseFragment<FragmentDesignListBinding, DesignListViewModel, NavArgs>(), SwipeRefreshLayout.OnRefreshListener {
    override val layoutId: Int = R.layout.fragment_design_list
    override val viewModel: DesignListViewModel by viewModels()
    override val navArgs: NavArgs by navArgs()

    lateinit var adapter: DesignListAdapter

    var limit = 10
    var page = 0

    override fun initView(savedInstanceState: Bundle?) {
        adapter = DesignListAdapter({
            val action = DesignListFragmentDirections.actionDesignListFragmentToReformDetailFragment(it)
            findNavController().navigate(action)
        }) { iv, str ->
            viewModel.setImage(iv, str)
        }
        binding.rvDesignList.adapter = adapter

        binding.rvDesignList.addOnScrollListener(OnScrollEndListener{
            page += 1
            viewModel.requestDesignList(limit, page)
        })
        binding.rvDesignList.addOnScrollListener(OnScrollEndListener(){
            if(viewModel.resSize % 10 == 0){
                addItem()
            }
        })
    }

    override fun initDataBinding() {
        viewModel.listResponse.observe(viewLifecycleOwner){
            it?.let{
                if(it.isNotEmpty() && viewModel.page != 0){
                    adapter.addData(it)
                }else if(it.isNotEmpty() && viewModel.page == 0){
                    adapter.setData(it)
                }
            }
        }
    }

    fun addItem(){
        viewModel.requestDesignList(viewModel.limit, viewModel.page+1)
    }


    override fun initViewCreated() {
        viewModel.requestDesignList(10, 0)
        adapter.removeAll()
    }

    override fun onRefresh() {
        viewModel.requestDesignList(10, 0)
    }
}