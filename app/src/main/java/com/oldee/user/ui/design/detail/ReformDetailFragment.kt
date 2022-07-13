package com.oldee.user.ui.design.detail

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.CheckBox
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.viewpager2.widget.ViewPager2
import com.oldee.user.R
import com.oldee.user.base.BaseFragment
import com.oldee.user.databinding.FragmentReformDetailBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ReformDetailFragment :
    BaseFragment<FragmentReformDetailBinding, ReformDetailViewModel, ReformDetailFragmentArgs>() {
    override val layoutId: Int = R.layout.fragment_reform_detail
    override val viewModel: ReformDetailViewModel by viewModels()
    override val navArgs: ReformDetailFragmentArgs by navArgs()

    lateinit var prepareAdapter: ReformPrepareItemAdapter
    lateinit var imageAdapter: ReformImageAdapter

    override fun initView(savedInstanceState: Bundle?) {
        prepareAdapter = ReformPrepareItemAdapter()
        imageAdapter = ReformImageAdapter { iv, path ->
            viewModel.setImage(iv, path)
        }
        binding.rvPrepareItem.adapter = prepareAdapter
        binding.vpImage.adapter = imageAdapter
        binding.vpImage.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback(){
            override fun onPageSelected(position: Int) {
                viewModel.currentImageIdx.postValue(position+1)
            }
        })
        binding.vm = viewModel

        binding.cbLike.setOnClickListener {
            if(it is CheckBox){
                Log.e("#debug", if(it.isChecked) "true" else "false")
            }
        }

        binding.btnOrder.setOnClickListener {
            val res = viewModel.res.value
            res?.let{
                nextFragment(ReformDetailFragmentDirections.actionReformDetailFragmentToOrderFragment(it))
            }
        }
    }

    override fun initDataBinding() {
        viewModel.res.observe(viewLifecycleOwner) {
            it?.let {
                binding.res = it

                val prepareItemList = mutableListOf<PrepareItem>()
                val reformNameList = it.getReformItemNameList()
                val reformIdList = it.getItemCode()
                it.getIconImageIdList().forEachIndexed { index, s ->
                    prepareItemList.add(
                        PrepareItem(
                            s,
                            reformNameList[index],
                            reformIdList[index]
                        )
                    )
                }

                prepareAdapter.setData(prepareItemList.toList())

                viewModel.setImage(binding.ivBefore, it.beforeImageName)
                viewModel.setImage(binding.ivAfter, it.afterImageName)
                binding.cbLike.isChecked = it.heartCheck != 0

                //image
                val list = it.getImageNameList()
                imageAdapter.setData(list?: listOf())

                viewModel.currentImageIdx.postValue(1)
                viewModel.totalImageCnt.postValue(list?.size?:0)

                viewModel.postDelay({showSkeleton(false)}, 500)
            }
        }
    }

    override fun initViewCreated() {
        showSkeleton(true)
        viewModel.requestDesignDetail(navArgs.id)
    }

    fun showSkeleton(show:Boolean){
        if(show){
            binding.clDesignDetail.visibility = View.GONE
            binding.llSkeleton.visibility = View.VISIBLE
            binding.skeletonDetail.apply {
                sfBeforeAfter.startShimmer()
                sfMainImage.startShimmer()
                sfMainInfo.startShimmer()
                sfOrderInfo.startShimmer()
                sfPrepareItem.startShimmer()
                sfWarning.startShimmer()
            }
        }else{
            binding.clDesignDetail.visibility = View.VISIBLE
            binding.llSkeleton.visibility = View.GONE
            binding.skeletonDetail.apply {
                sfBeforeAfter.stopShimmer()
                sfMainImage.stopShimmer()
                sfMainInfo.stopShimmer()
                sfOrderInfo.stopShimmer()
                sfPrepareItem.stopShimmer()
                sfWarning.stopShimmer()
            }
        }
    }
}