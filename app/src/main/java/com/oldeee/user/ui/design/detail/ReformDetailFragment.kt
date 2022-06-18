package com.oldeee.user.ui.design.detail

import android.os.Bundle
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.viewpager2.widget.ViewPager2
import com.oldeee.user.R
import com.oldeee.user.base.BaseFragment
import com.oldeee.user.databinding.FragmentReformDetailBinding
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
        prepareAdapter = ReformPrepareItemAdapter { iv, path ->
            viewModel.setImage(iv, path)
        }
        imageAdapter = ReformImageAdapter { iv, path ->
            viewModel.setImage(iv, path)
        }
        binding.rvPrepareItem.adapter = prepareAdapter
        binding.vpImage.adapter = imageAdapter
        binding.vpImage.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback(){
            override fun onPageSelected(position: Int) {
                viewModel.currentImageIdx.postValue(position)
            }
        })
        binding.vm = viewModel
    }

    override fun initDataBinding() {
        viewModel.res.observe(viewLifecycleOwner) {
            it?.let {
                binding.res = it

                val prepareItemList = mutableListOf<ReformPrepareItemAdapter.PrepareItem>()
                val reformNameList = it.getReformItemNameList()
                it.getIconImageIdList().forEachIndexed { index, s ->
                    prepareItemList.add(
                        ReformPrepareItemAdapter.PrepareItem(
                            s,
                            reformNameList[index]
                        )
                    )
                }

                prepareAdapter.setData(prepareItemList.toList())

                viewModel.setImage(binding.ivBefore, it.beforeImageName)
                viewModel.setImage(binding.ivAfter, it.afterImageName)
                binding.cbLike.isChecked = it.heartCheck != 0

                //image
                val list = it.getImageNameList()
                imageAdapter.setData(list)

                viewModel.currentImageIdx.postValue(1)
                viewModel.totalImageCnt.postValue(list.size)
            }
        }
    }

    override fun initViewCreated() {
        viewModel.requestDesignDetail(navArgs.id)
    }
}