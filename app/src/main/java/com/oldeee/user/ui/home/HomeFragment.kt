package com.oldeee.user.ui.home

import android.os.Bundle
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.oldeee.user.R
import com.oldeee.user.base.BaseFragment
import com.oldeee.user.databinding.FragmentHomeBinding
import com.oldeee.user.ui.home.adapter.DesignListAdapter
import com.oldeee.user.ui.home.adapter.ExpertListAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding, HomeViewModel, HomeFragmentArgs>() {
    override val layoutId: Int = R.layout.fragment_home
    override val viewModel: HomeViewModel by viewModels()
    override val navArgs: HomeFragmentArgs by navArgs()

    lateinit var designAdapter: DesignListAdapter
    lateinit var expertAdapter: ExpertListAdapter

    override fun initView(savedInstanceState: Bundle?) {
        binding.ivDrawer.setOnClickListener {
            activityFuncFunction.openDrawerMenu()
        }

        binding.clDesignTitle.setOnClickListener {
            nextFragment(HomeFragmentDirections.actionHomeFragmentToDesignListFragment())
        }

        designAdapter = DesignListAdapter({
            val action = HomeFragmentDirections.actionHomeFragmentToReformDetailFragment(it)
            findNavController().navigate(action)
        }) { iv, path ->
            viewModel.setImage(iv, path)
        }
        expertAdapter = ExpertListAdapter { iv, path ->
            viewModel.setImageCircle(iv, path)
        }
        binding.rvDesignList.adapter = designAdapter
        binding.rvDesignerList.adapter = expertAdapter
        activityFuncFunction.setDrawerName(navArgs.name)
    }

    override fun initDataBinding() {
        viewModel.expertList.observe(viewLifecycleOwner) {
            it?.let { list ->
                val max = 6
                if (list.size <= 6) {
                    expertAdapter.setData(list)
                } else {
                    expertAdapter.setData(list.subList(0, max - 1))
                }

            }
        }

        viewModel.designList.observe(viewLifecycleOwner) {
            it?.let {
                if (it.size <= 3) {
                    designAdapter.setData(it)
                } else {
                    designAdapter.setData(it.subList(0, it.size - 1))
                }
            }
        }
    }

    override fun initViewCreated() {
        viewModel.requestExpertList()
        viewModel.requestDesignList()
    }
}