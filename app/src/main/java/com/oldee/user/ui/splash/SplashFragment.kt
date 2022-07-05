package com.oldee.user.ui.splash

import android.Manifest
import android.os.Bundle
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.viewModels
import androidx.navigation.NavArgs
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.oldee.user.R
import com.oldee.user.base.BaseFragment
import com.oldee.user.custom.checkPermission
import com.oldee.user.databinding.FragmentSplashBinding
import com.oldee.user.ui.dialog.PermissionDialog
import dagger.hilt.android.AndroidEntryPoint

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

    fun showPermissionInfoDialog() {
        val check = checkPermission(requireContext(), *permissions)

        if (!check) {
            val dialog = PermissionDialog {
                requestPermissionResult.launch(permissions)
            }
            dialog.isCancelable = false

            activity?.let {
                dialog.show(it.supportFragmentManager, "permission")
            }
        } else {
            loadNext()
        }
    }

    fun loadNext() {
        viewModel.postDelay({
            val action = SplashFragmentDirections.actionSplashFragmentToSignInFragment()
            findNavController()?.navigate(action)
        }, 500)
    }

    override fun initViewCreated() {

    }
}