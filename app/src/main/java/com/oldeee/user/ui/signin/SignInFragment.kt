package com.oldeee.user.ui.signin

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.NavArgs
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.navercorp.nid.NaverIdLoginSDK
import com.nhn.android.naverlogin.OAuthLogin
import com.oldeee.user.R
import com.oldeee.user.base.BaseFragment
import com.oldeee.user.databinding.FragmentSignInBinding
import dagger.hilt.android.AndroidEntryPoint
import java.text.SimpleDateFormat
import java.time.Instant
import java.time.LocalDate
import java.time.LocalDateTime
import java.util.*

@AndroidEntryPoint
class SignInFragment : BaseFragment<FragmentSignInBinding, SignInViewModel, NavArgs>() {
    override val layoutId: Int = R.layout.fragment_sign_in
    override val viewModel: SignInViewModel by viewModels()
    override val navArgs: NavArgs by navArgs()

    private lateinit var oauthLogin : OAuthLogin

    override fun initView(savedInstanceState: Bundle?) {
        binding.llNaver.setOnClickListener {
            viewModel.startNaverLogin(requireContext())
            //findNavController().navigate()
        }
    }

    override fun initDataBinding() {
        viewModel.goToSignUp.observe(viewLifecycleOwner){
            it?.let{
                if(it){
                    findNavController().navigate(SignInFragmentDirections.actionSignInFragmentToHomeFragment())
                }
            }
        }
    }
}