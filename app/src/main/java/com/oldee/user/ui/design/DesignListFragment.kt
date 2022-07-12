package com.oldee.user.ui.design

import android.os.Bundle
import androidx.fragment.app.viewModels
import androidx.navigation.NavArgs
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.oldee.user.R
import com.oldee.user.base.BaseFragment
import com.oldee.user.custom.OnScrollEndListener
import com.oldee.user.databinding.FragmentDesignListBinding
import com.oldee.user.network.response.DesignListItem
import com.oldee.user.network.response.DesignListResponse
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DesignListFragment : BaseFragment<FragmentDesignListBinding, DesignListViewModel, NavArgs>(), SwipeRefreshLayout.OnRefreshListener {
    override val layoutId: Int = R.layout.fragment_design_list
    override val viewModel: DesignListViewModel by viewModels()
    override val navArgs: NavArgs by navArgs()

    lateinit var adapter: DesignListAdapter

    var limit = 10
    var page = 0

    init {

    }

    override fun initView(savedInstanceState: Bundle?) {
        adapter = DesignListAdapter({
            val action = DesignListFragmentDirections.actionDesignListFragmentToReformDetailFragment(it)
            findNavController().navigate(action)
        }) { iv, str ->
            viewModel.setImage(iv, str)
        }
        binding.rvDesignList.adapter = adapter
        binding.rvDesignList.setItemViewCacheSize(30)

        binding.rvDesignList.addOnScrollListener(OnScrollEndListener(){
            if(viewModel.resSize % 10 == 0){
                addItem()
            }
        })
    }

    override fun initDataBinding() {
        viewModel.listResponse.observe(viewLifecycleOwner){
            it?.let{
                adapter.submitList(it)
            }
        }
    }

    fun addItem(){
        viewModel.requestDesignList(viewModel.limit, viewModel.page+1, true)
    }


    override fun initViewCreated() {
        viewModel.requestDesignList(10, 0, false)
//        adapter.removeAll()
    }

    override fun onRefresh() {
        viewModel.requestDesignList(10, 0, false)
//        binding.swList.isRefreshing = false
    }
}