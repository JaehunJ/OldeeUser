package com.oldee.user.ui.signin

import android.os.Bundle
import androidx.fragment.app.viewModels
import androidx.navigation.NavArgs
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.navercorp.nid.NaverIdLoginSDK
import com.oldee.user.R
import com.oldee.user.base.BaseFragment
import com.oldee.user.databinding.FragmentSignInBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignInFragment : BaseFragment<FragmentSignInBinding, SignInViewModel, NavArgs>() {
    override val layoutId: Int = R.layout.fragment_sign_in
    override val viewModel: SignInViewModel by viewModels()
    override val navArgs: NavArgs by navArgs()

    override fun initView(savedInstanceState: Bundle?) {
        binding.llNaver.setOnClickListener {
            viewModel.startNaverLogin(requireContext())
        }

        val cId = getString(R.string.naver_client_id)
        val sce = getString(R.string.naver_client_secret)
        val name = getString(R.string.naver_app_name)
        NaverIdLoginSDK.initialize(requireContext(), cId, sce, name)
        NaverIdLoginSDK.showDevelopersLog(true)
    }

    override fun initDataBinding() {
        viewModel.nProfile.observe(viewLifecycleOwner) {
            it?.let {
                viewModel.requestNaverSignIn(it) {
                    activityFuncFunction.hideProgress()
                    findNavController().navigate(
                        SignInFragmentDirections.actionSignInFragmentToSignUpFragment(
                            it.email ?: "",
                            it.id ?: "",
                            it.mobile ?: ""
                        )
                    )
                }
            }
        }

        viewModel.res.observe(viewLifecycleOwner, getObserver(viewLifecycleOwner){
            it?.let{
                onNext(it.userName)
            }
        })

    }

    fun onNext(str:String) {
        findNavController().navigate(SignInFragmentDirections.actionSignInFragmentToHomeFragment())
    }

    override fun initViewCreated() {
        if(viewModel.getAutoLogin()){
            viewModel.startNaverLogin(requireContext())
        }
    }
}