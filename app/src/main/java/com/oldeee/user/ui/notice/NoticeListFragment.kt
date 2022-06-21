package com.oldeee.user.ui.notice

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.NavArgs
import androidx.navigation.fragment.navArgs
import com.oldeee.user.R
import com.oldeee.user.base.BaseFragment
import com.oldeee.user.databinding.FragmentNoticeListBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class NoticeListFragment : BaseFragment<FragmentNoticeListBinding, NoticeListViewModel, NavArgs>() {
    override val layoutId: Int = R.layout.fragment_notice_list
    override val viewModel: NoticeListViewModel by viewModels()
    override val navArgs: NavArgs by navArgs()

    lateinit var adapter:NoticeListAdapter

    override fun initView(savedInstanceState: Bundle?) {
        adapter = NoticeListAdapter()
        binding.rvList.adapter = adapter
    }

    override fun initDataBinding() {
        viewModel.noticeList.observe(viewLifecycleOwner){
            it?.let{
                adapter.setData(it.toList())
            }
        }
    }

    override fun initViewCreated() {
        viewModel.requestNoticeList()
    }
}