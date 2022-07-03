package com.oldeee.user.ui.signup

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.Editable
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.NavArgs
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.oldeee.user.BuildConfig
import com.oldeee.user.R
import com.oldeee.user.base.BaseFragment
import com.oldeee.user.databinding.FragmentSignUpBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignUpFragment : BaseFragment<FragmentSignUpBinding, SignUpViewModel, SignUpFragmentArgs>() {
    override val layoutId: Int = R.layout.fragment_sign_up
    override val viewModel: SignUpViewModel by viewModels()
    override val navArgs: SignUpFragmentArgs by navArgs()

    override fun initView(savedInstanceState: Bundle?) {
        binding.vm = viewModel
        binding.btnConfirm.setOnClickListener {
            if(viewModel.isValidate){
                viewModel.requestSignUp(navArgs.phone, navArgs.snsId)
            }else{
                activityFuncFunction.showToast("누락된 정보가 있습니다.")
            }

        }
        viewModel.phone = navArgs.phone.replace("-", "")
        viewModel.email = navArgs.email
        binding.etMail.isEnabled = false

        binding.tvShowTermService.setOnClickListener {
            val intent = Intent(
                Intent.ACTION_VIEW,
                Uri.parse(BuildConfig.TERM_SERVICE)
            )
            startActivity(intent)
        }

        binding.tvShowTermInfo.setOnClickListener {
            val intent = Intent(
                Intent.ACTION_VIEW,
                Uri.parse(BuildConfig.TERM_PRIVACY)
            )
            startActivity(intent)
        }

        binding.cbInfo.setOnTouchListener { view, motionEvent ->
            activityFuncFunction.hideSoftKeyboard()
            return@setOnTouchListener false
        }

        binding.cbService.setOnTouchListener { view, motionEvent ->
            activityFuncFunction.hideSoftKeyboard()
            return@setOnTouchListener false
        }
    }

    override fun initDataBinding() {
        viewModel.success.observe(viewLifecycleOwner){
            it?.let{
                if(it){
                    Log.e("#debug", "success signup")
                    findNavController().popBackStack()
                }else{
                    activityFuncFunction.showToast("회원가입중 오류가 발생했습니다.")
                }
            }
        }
    }

    override fun initViewCreated() {

    }
}