package com.oldeee.user.ui

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.navercorp.nid.NaverIdLoginSDK
import com.oldeee.user.CommonActivityFuncImpl
import com.oldeee.user.R
import com.oldeee.user.databinding.ActivityMainBinding
import com.oldeee.user.databinding.LayoutHomeRightSlideMenuBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity(), CommonActivityFuncImpl {
    lateinit var binding: ActivityMainBinding
    lateinit var drawerBinding: LayoutHomeRightSlideMenuBinding

    var currentDesId: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
//        setContentView(R.layout.activity_main)
        Log.e("#debug", "call activity")

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment)
        val navController = navHostFragment!!.findNavController()

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

        }
        drawerBinding.tvSetting.setOnClickListener {

        }
        drawerBinding.tvSignUpExpert.setOnClickListener {

        }

        navController.addOnDestinationChangedListener { controller, destination, arguments ->
            currentDesId = destination.id
        }

    }

    override fun showProgress() {
        binding.pb.visibility = View.VISIBLE
    }

    override fun hideProgress() {
        binding.pb.visibility = View.GONE
    }

    override fun showToast(msg: String) {

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

//    @SuppressLint("RestrictedApi")
    override fun onBackPressed() {
        Log.e("#debug", "call onBackPressed")
        if(currentDesId != R.id.homeFragment){
            super.onBackPressed()
            return
        }

        Log.e("#debug", "call onBackPressed drawer")
        val drawer = binding.mainDrawer
        val menu = binding.menuDrawer
        if (drawer.isDrawerOpen(menu.root)) {
            drawer.closeDrawer(menu.root)
        } else {
            super.onBackPressed()
        }
    }

    override fun setDrawerName(name: String) {
        binding.menuDrawer.tvName.text = name
    }
}