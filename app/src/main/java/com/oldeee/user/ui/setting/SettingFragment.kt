package com.oldeee.user.ui.setting

import android.content.Intent
import android.os.Bundle
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavArgs
import androidx.navigation.fragment.navArgs
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.oldeee.user.R
import com.oldeee.user.base.BaseFragment
import com.oldeee.user.databinding.FragmentSettingBinding
import com.oldeee.user.ui.MainActivity
import com.oldeee.user.ui.dialog.TermBottomSheetDialog
import com.oldeee.user.ui.dialog.TwoButtonDialog
import com.oldeee.user.ui.dialog.WithdrawBottomSheetDialog
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class SettingFragment : BaseFragment<FragmentSettingBinding, SettingVIewModel, NavArgs>() {
    override val layoutId: Int = R.layout.fragment_setting
    override val viewModel: SettingVIewModel by viewModels()
    override val navArgs: NavArgs by navArgs()

    lateinit var withdrawBehavior:BottomSheetBehavior<LinearLayout>

    override fun initView(savedInstanceState: Bundle?) {
        binding.llLogout.setOnClickListener {
            val dialog = TwoButtonDialog(
                title = "",
                contents = "로그아웃 하시겠어요?",
                okText = "로그아웃",
                cancelText = "취소",
                {
                    viewModel.logout()
                },{

                }
            )
            dialog.show(requireActivity().supportFragmentManager, "")
        }

        binding.llTerm.setOnClickListener {
            val dialog = TermBottomSheetDialog()
            dialog.show(requireActivity().supportFragmentManager, "")
        }

        binding.llWithdraw.setOnClickListener {
            val dialog = WithdrawBottomSheetDialog({

            })
            dialog.show(requireActivity().supportFragmentManager, "")
//            withdrawBehavior.state = BottomSheetBehavior.STATE_EXPANDED
//            viewModel.requestWithdraw()
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