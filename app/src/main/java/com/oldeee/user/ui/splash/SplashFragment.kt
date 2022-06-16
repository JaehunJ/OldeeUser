package com.oldeee.user.ui.splash

import android.Manifest
import android.os.Bundle
import android.os.Handler
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.NavArgs
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.oldeee.user.R
import com.oldeee.user.base.BaseFragment
import com.oldeee.user.custom.checkPermission
import com.oldeee.user.databinding.FragmentSplashBinding
import com.oldeee.user.ui.dialog.PermissionDialog
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SplashFragment : BaseFragment<FragmentSplashBinding, SplashViewModel, NavArgs>() {
    override val layoutId: Int = R.layout.fragment_splash
    override val viewModel: SplashViewModel by viewModels()
    override val navArgs: NavArgs by navArgs()

    private val permissions =
        arrayOf(
            Manifest.permission.CAMERA,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        )

    private val requestPermissionResult =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) {
            if (it.all { per -> per.value == true }) {
                loadNext()
            } else {
                requireActivity().finish()
            }
        }

    override fun initView(savedInstanceState: Bundle?) {

    }

    override fun initDataBinding() {

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        showPermissionInfoDialog()
    }

    fun showPermissionInfoDialog(){
        val check = checkPermission(requireContext(), *permissions)

        if(!check){
            val dialog = PermissionDialog{
                requestPermissionResult.launch(permissions)
            }
            dialog.isCancelable = false

            activity?.let{
                dialog.show(it.supportFragmentManager, "permission")
            }
        }else{
            loadNext()
        }
    }

    fun loadNext(){
        viewModel.postDelay({
            val action = SplashFragmentDirections.actionSplashFragmentToSignInFragment()
            findNavController()?.navigate(action)
        }, 500)
    }
}