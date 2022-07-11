package com.oldee.user.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.navercorp.nid.NaverIdLoginSDK
import com.oldee.user.CommonActivityFuncImpl
import com.oldee.user.R
import com.oldee.user.databinding.ActivityMainBinding
import com.oldee.user.databinding.LayoutHomeRightSlideMenuBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), CommonActivityFuncImpl {
    lateinit var binding: ActivityMainBinding
    lateinit var drawerBinding: LayoutHomeRightSlideMenuBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        installSplashScreen()
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
//        setContentView(R.layout.activity_main)
        Log.e("#debug", "call activity")

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment)
        val navController = navHostFragment!!.findNavController()
        navController.addOnDestinationChangedListener { controller, destination, arguments -> hideProgress() }

        drawerBinding = binding.menuDrawer

        drawerBinding.llCart.setOnClickListener {
            hideDrawerMenu()
            findNavController(R.id.nav_host_fragment).navigate(R.id.action_global_cartFragment)
        }
        drawerBinding.llOrderLog.setOnClickListener {
            hideDrawerMenu()
            findNavController(R.id.nav_host_fragment).navigate(R.id.action_global_orderLogFragment)
        }
        drawerBinding.tvNotice.setOnClickListener {
            hideDrawerMenu()
            findNavController(R.id.nav_host_fragment).navigate(R.id.action_global_noticeListFragment)
        }
        drawerBinding.tvQna.setOnClickListener {
            hideDrawerMenu()
            findNavController(R.id.nav_host_fragment).navigate(R.id.action_global_faqFragment)
//            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("http://pf.kakao.com/_QuRxmb")))
        }
        drawerBinding.tvSetting.setOnClickListener {
            hideDrawerMenu()
            findNavController(R.id.nav_host_fragment).navigate(R.id.action_global_settingFragment)
        }
        drawerBinding.tvSignUpExpert.setOnClickListener {
            //https://www.oldee.kr/oldeener
            hideDrawerMenu()
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("https://www.oldee.kr/oldeener")))
        }
    }

    override fun showProgress() {
        binding.pb.visibility = View.VISIBLE
    }

    override fun hideProgress() {
        binding.pb.visibility = View.GONE
    }

    override fun showToast(msg: String) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
    }

    override fun showSnackBar(msg: String) {

    }

    override fun openDrawerMenu() {
        val drawer = binding.mainDrawer
        val menu = binding.menuDrawer
        if (!drawer.isDrawerOpen(menu.root)) {
            drawer.openDrawer(menu.root)
        }
    }

    override fun hideDrawerMenu() {
        val drawer = binding.mainDrawer
        val menu = binding.menuDrawer
        drawer.closeDrawer(menu.root)
    }

    override fun setDrawerName(name: String) {
        binding.menuDrawer.tvName.text = name
    }

    override fun goFinish() {
        this.finish()
    }

    override fun isDrawerOpen(): Boolean {
        val drawer = binding.mainDrawer
        val menu = binding.menuDrawer

        return drawer.isDrawerOpen(menu.root)
    }

    override fun onDestroy() {
        super.onDestroy()
        NaverIdLoginSDK.logout()
    }
//
    override fun hideSoftKeyboard() {
        val inputManger = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        if(inputManger.isAcceptingText){
            if(currentFocus != null){
                inputManger.hideSoftInputFromWindow(currentFocus?.windowToken, 0)
            }
        }
    }

    override fun logout(){
        this.finishAffinity()
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }
}