package com.oldee.user.ui.splash

import android.Manifest
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.viewModels
import androidx.navigation.NavArgs
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.oldee.user.BuildConfig
import com.oldee.user.R
import com.oldee.user.base.BaseFragment
import com.oldee.user.custom.checkPermission
import com.oldee.user.data.AppStatus
import com.oldee.user.databinding.FragmentSplashBinding
import com.oldee.user.ui.dialog.OneButtonDialog
import com.oldee.user.ui.dialog.PermissionDialog
import com.oldee.user.ui.dialog.TwoButtonDialog
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
                requestVersionInfo()
            } else {
                requireActivity().finish()
            }
        }

    override fun initView(savedInstanceState: Bundle?) {

    }

    override fun initDataBinding() {
        viewModel.data.observe(viewLifecycleOwner){
            it?.let{
                if(it.version_id > BuildConfig.VERSION_CODE){
                    showForceUpdateDialog()
                }
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        showPermissionInfoDialog()
    }

    fun showUpdateDialog(){
        val dialog = TwoButtonDialog(
            title = "업데이트 안내",
            contents = "중요한 업데이트가 있습니다.\\n서비스 이용을 위해 지금 바로 업데이트하세요.",
            okText = "업데이트",
            cancelText = "다음에",
            {
                val intent = Intent(Intent.ACTION_VIEW)
                intent.setData(Uri.parse(BuildConfig.GOOGLE_PLAY))
                startActivity(intent)
            }
        ){
            loadNext()
        }

        dialog.isCancelable = false

        activity?.let{
            dialog.show(it.supportFragmentManager, "update")
        }
    }

    fun showForceUpdateDialog(){
        val dialog = OneButtonDialog(
            title = "업데이트 안내",
            contents = "중요한 업데이트가 있습니다.\\n서비스 이용을 위해 지금 바로 업데이트하세요.",
            okText = "업데이트"
        ){
            val intent = Intent(Intent.ACTION_VIEW)
            intent.setData(Uri.parse(BuildConfig.GOOGLE_PLAY))
            startActivity(intent)
        }
        dialog.isCancelable = false
        activity?.let{
            dialog.show(it.supportFragmentManager, "force update")
        }
    }

    fun showServiceCheckDialog(){
        val dialog = OneButtonDialog(
            title = "서비스 점검 안내",
            contents = "서비스 안정화를 위한 점검이 진행중입니다.\\n점검일시: YYYY년 MM월 DD일 HH:mm ~ HH:mm\\n",
            okText = "확인"
        ){
            requireActivity().finish()
        }
        dialog.isCancelable = false

        activity?.let{
            dialog.show(it.supportFragmentManager, "service")
        }
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
            requestVersionInfo()
        }
    }

    fun loadNext() {
        viewModel.postDelay({
            val action = SplashFragmentDirections.actionSplashFragmentToSignInFragment()
            findNavController()?.navigate(action)
        }, 500)
    }

    fun requestVersionInfo(){
//        loadNext()
        //TODO 나중에 해야함. 버전인포
//        callApi {
            viewModel.requestVersionInfo()
//        }
    }

    override fun initViewCreated() {

    }
}