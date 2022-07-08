package com.oldee.user.ui.setting.withdraw

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.viewModels
import androidx.navigation.NavArgs
import androidx.navigation.fragment.navArgs
import com.oldee.user.R
import com.oldee.user.base.BaseFragment
import com.oldee.user.databinding.FragmentWithdrawDoneBinding
import com.oldee.user.ui.MainActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WithdrawDoneFragment :
    BaseFragment<FragmentWithdrawDoneBinding, WithdrawDoneViewModel, NavArgs>() {
    override val layoutId: Int = R.layout.fragment_withdraw_done
    override val viewModel: WithdrawDoneViewModel by viewModels()
    override val navArgs: NavArgs by navArgs()

    override fun initView(savedInstanceState: Bundle?) {
        binding.btnConfirm.setOnClickListener {
            viewModel.logout()
        }
    }

    override fun initDataBinding() {
        viewModel.success.observe(viewLifecycleOwner){
            it?.let{
                if(it){
                    activity?.let {ac->
                        ac.finishAffinity()
                        val intent = Intent(requireContext(), MainActivity::class.java)
                        startActivity(intent)
                    }
                }
            }
        }
    }

    override fun initViewCreated() {

    }
}