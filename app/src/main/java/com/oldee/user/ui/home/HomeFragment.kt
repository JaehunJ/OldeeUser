package com.oldee.user.ui.home

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavArgs
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.viewpager2.widget.ViewPager2
import com.oldee.user.R
import com.oldee.user.base.BaseFragment
import com.oldee.user.custom.dpToPx
import com.oldee.user.databinding.FragmentHomeBinding
import com.oldee.user.ui.dialog.HomeMenuDialog
import com.oldee.user.ui.home.adapter.BannerAdapter
import com.oldee.user.ui.home.adapter.DesignListAdapter
import com.oldee.user.ui.home.adapter.ExpertListAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay

@AndroidEntryPoint
class HomeFragment : BaseFragment<FragmentHomeBinding, HomeViewModel, NavArgs>() {
    override val layoutId: Int = R.layout.fragment_home
    override val viewModel: HomeViewModel by viewModels()
    override val navArgs: NavArgs by navArgs()

    lateinit var designAdapter: DesignListAdapter
    lateinit var expertAdapter: ExpertListAdapter
    lateinit var bannerAdapter: BannerAdapter

    val expertMax = 5
    val designMax = 6
    lateinit var autoScrollJob : Job
    var bannerPosition = 0
    private var backTime = 0L

    lateinit var backCallback:OnBackPressedCallback

    override fun initView(savedInstanceState: Bundle?) {
        binding.ivDrawer.setOnClickListener {
//            activityFuncFunction.openDrawerMenu()
            val dialog = HomeMenuDialog(false){d,m->
                m.tvName.text = viewModel.getUserName()
                m.tvEmail.text = viewModel.getUserEmail()
                activityFuncFunction.openMenu(d, m)
            }
            dialog.isCancelable = true
            dialog.show(requireActivity().supportFragmentManager, "menu")
        }

        designAdapter = DesignListAdapter({
            val action = HomeFragmentDirections.actionHomeFragmentToReformDetailFragment(it)
            findNavController().navigate(action)
        }) { iv, path ->
            viewModel.setImage(iv, path, dpToPx(requireContext(), 10f).toInt())
        }
        expertAdapter = ExpertListAdapter { iv, path ->
            viewModel.setImageCircle(iv, path)
        }
        bannerAdapter = BannerAdapter{
            when(it){
                0->{
                    openWebBrowser("https://www.oldee.kr/62cd39e3-ac8b-4fd0-b643-063291d43274")
                }
                1->{
                    openWebBrowser("https://www.oldee.kr/0ea20357-234a-49f4-9548-fbea79ca9019")
                }
                else->{
                    openWebBrowser("https://www.oldee.kr/dbf9f91a-589d-4110-8563-3ca9297dd716")
                }
            }
        }
        bannerAdapter.setData(listOf(R.drawable.banner_02, R.drawable.banner_03, R.drawable.banner_04))
        binding.tvBannerTotal.text = "3"


        binding.rvDesignList.adapter = designAdapter
        binding.rvDesignerList.adapter = expertAdapter
        binding.rvDesignerList.isClickable = false
        binding.vpBanner.adapter = bannerAdapter
        binding.vpBanner.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback(){
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                binding.tvBannerCurrent.text = ((position%3)+1).toString()
                bannerPosition = position
            }

            override fun onPageScrollStateChanged(state: Int) {
                super.onPageScrollStateChanged(state)
                when(state){
                    ViewPager2.SCROLL_STATE_IDLE->{
                        if(!autoScrollJob.isActive) createScrollJob()
                    }
                    ViewPager2.SCROLL_STATE_DRAGGING->{
                        if(!autoScrollJob.isCancelled)
                            autoScrollJob.cancel()
                    }
                }
            }
        })

        binding.tvDesignTitle.setOnClickListener {
            nextFragment(HomeFragmentDirections.actionHomeFragmentToDesignListFragment())
        }
        binding.ivDesignArr.setOnClickListener {
            nextFragment(HomeFragmentDirections.actionHomeFragmentToDesignListFragment())
        }

        binding.btnExpertMore.setOnClickListener {
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("https://bit.ly/oldeener")))
        }

        binding.ivLogo.setOnClickListener {
            findNavController().popBackStack()
            findNavController().navigate(R.id.homeFragment)
        }
    }

    override fun initDataBinding() {
        viewModel.expertList.observe(viewLifecycleOwner) {
            it?.let { list ->
                if (list.size <= expertMax) {
                    expertAdapter.setData(list)
                } else {
                    expertAdapter.setData(list.subList(0, expertMax))
                }
            }
        }

        viewModel.designList.observe(viewLifecycleOwner) {
            it?.let {
                if (it.size <= designMax) {
                    designAdapter.setData(it)
                } else {
                    designAdapter.setData(it.subList(0, designMax))
                }
            }
        }
    }

    override fun initViewCreated() {
//        activityFuncFunction.setDrawerName(viewModel.getUserName())
        showSkeleton(true)

        viewModel.call { showSkeleton(false) }
        backCallback.isEnabled = true
    }

    override fun onResume() {
        super.onResume()
        createScrollJob()
        backCallback.isEnabled = true
    }

    override fun onStop() {
        super.onStop()
        backCallback.isEnabled = false
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
//
        backCallback = object :OnBackPressedCallback(true){
            override fun handleOnBackPressed() {
//                if(activityFuncFunction.isDrawerOpen()){
//                    activityFuncFunction.hideDrawerMenu()
//                    return
//                }

                if (System.currentTimeMillis() - backTime > 1500) {
                    //show toast
                    backTime = System.currentTimeMillis()
                    activityFuncFunction.showToast("한번 더 누르시면 앱을 종료합니다.")
                } else {
                    activityFuncFunction.goFinish()
                }
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(backCallback)
    }

    override fun onDetach() {
        super.onDetach()

        backCallback.remove()
    }

    override fun onPause() {
        super.onPause()
        autoScrollJob.cancel()
    }

    fun showSkeleton(show: Boolean) {
        if (show) {
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