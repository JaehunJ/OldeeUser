package com.oldeee.user.ui.home

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.viewpager2.widget.ViewPager2
import com.oldeee.user.R
import com.oldeee.user.base.BaseFragment
import com.oldeee.user.databinding.FragmentHomeBinding
import com.oldeee.user.ui.home.adapter.BannerAdapter
import com.oldeee.user.ui.home.adapter.DesignListAdapter
import com.oldeee.user.ui.home.adapter.ExpertListAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding, HomeViewModel, HomeFragmentArgs>() {
    override val layoutId: Int = R.layout.fragment_home
    override val viewModel: HomeViewModel by viewModels()
    override val navArgs: HomeFragmentArgs by navArgs()

    lateinit var designAdapter: DesignListAdapter
    lateinit var expertAdapter: ExpertListAdapter
    lateinit var bannerAdapter: BannerAdapter

    val max = 6
    lateinit var autoScrollJob : Job
    var bannerPosition = 0

    override fun initView(savedInstanceState: Bundle?) {
        binding.ivDrawer.setOnClickListener {
            activityFuncFunction.openDrawerMenu()
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
        bannerAdapter = BannerAdapter()
        bannerAdapter.setData(listOf(R.drawable.banner, R.drawable.banner_02))
        binding.tvBannerTotal.text = "2"


        binding.rvDesignList.adapter = designAdapter
        binding.rvDesignerList.adapter = expertAdapter
        binding.vpBanner.adapter = bannerAdapter
        binding.vpBanner.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback(){
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                binding.tvBannerCurrent.text = (position%2).toString()
                bannerPosition = position
            }

            override fun onPageScrollStateChanged(state: Int) {
                super.onPageScrollStateChanged(state)
                when(state){
                    ViewPager2.SCROLL_STATE_IDLE->{
                        if(!autoScrollJob.isActive) createScrollJob()
                    }
                    ViewPager2.SCROLL_STATE_DRAGGING->{
                        autoScrollJob.cancel()
                    }
                }
            }
        })

        activityFuncFunction.setDrawerName(navArgs.name)

        binding.tvDesignTitle.setOnClickListener {
            nextFragment(HomeFragmentDirections.actionHomeFragmentToDesignListFragment())
        }
        binding.ivDesignArr.setOnClickListener {
            nextFragment(HomeFragmentDirections.actionHomeFragmentToDesignListFragment())
        }
    }

    override fun initDataBinding() {
        viewModel.expertList.observe(viewLifecycleOwner) {
            it?.let { list ->
//                val max = 6
                if (list.size <= max) {
                    expertAdapter.setData(list)
                } else {
                    expertAdapter.setData(list.subList(0, max - 1))
                }

            }
        }

        viewModel.designList.observe(viewLifecycleOwner) {
            it?.let {
                if (it.size <= max) {
                    designAdapter.setData(it)
                } else {
                    designAdapter.setData(it.subList(0, it.size - 1))
                }
            }
        }
    }

    override fun initViewCreated() {
        showSkeleton(true)

        viewModel.call { showSkeleton(false) }
        createScrollJob()

//        viewModel.requestExpertList()
//        viewModel.requestDesignList({ showSkeleton(false) })
    }

    override fun onPause() {
        super.onPause()
        autoScrollJob.cancel()
    }

    fun showSkeleton(show: Boolean) {
        if (show) {
            Log.e("#debug", "start")
            binding.llDesignListSkeleton.visibility = View.VISIBLE
            binding.llSkeletonExpertList.visibility = View.VISIBLE
            binding.llDesignList.visibility = View.GONE
            binding.llExpertList.visibility = View.GONE
            binding.sfDesignListSkeleton.startShimmer()
            binding.sfExpertList.startShimmer()
            //            binding.sfDesignListSkeleton.bringToFront()
        } else {
            binding.sfDesignListSkeleton.stopShimmer()
            binding.sfExpertList.stopShimmer()
            binding.llDesignList.visibility = View.VISIBLE
            binding.llExpertList.visibility = View.VISIBLE
            binding.llDesignListSkeleton.visibility = View.GONE
            binding.llSkeletonExpertList.visibility = View.GONE


        }
    }

    fun createScrollJob(){
        autoScrollJob = lifecycleScope.launchWhenResumed {
            delay(3000)
            binding.vpBanner.setCurrentItem(++bannerPosition, true)
        }
    }
}