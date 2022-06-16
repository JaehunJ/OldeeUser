package com.oldeee.user.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.navigation.findNavController
import com.navercorp.nid.NaverIdLoginSDK
import com.oldeee.user.CommonActivityFuncImpl
import com.oldeee.user.R
import com.oldeee.user.databinding.ActivityMainBinding
import com.oldeee.user.databinding.LayoutHomeRightSlideMenuBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity(),CommonActivityFuncImpl {
    lateinit var binding:ActivityMainBinding
    lateinit var drawerBinding: LayoutHomeRightSlideMenuBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
//        setContentView(R.layout.activity_main)
        Log.e("#debug", "call activity")

        drawerBinding = binding.menuDrawer

        drawerBinding.llCart.setOnClickListener {
            hideDrawerMenu()
            findNavController(R.id.nav_host_fragment).navigate(R.id.action_global_cartFragment)
        }
        drawerBinding.llOrderLog.setOnClickListener {
            hideDrawerMenu()
            findNavController(R.id.nav_host_fragment).navigate(R.id.action_global_orderLogFragment)
        }

        val cId = getString(R.string.naver_client_id)
        val sce = getString(R.string.naver_client_secret)
        val name = getString(R.string.naver_app_name)
        NaverIdLoginSDK.initialize(this, cId, sce, name)
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
        if(drawer.isDrawerOpen(menu.root)){
            drawer.closeDrawer(menu.root)
        }else{
            drawer.openDrawer(menu.root)
        }
    }

    override fun hideDrawerMenu() {
        val drawer = binding.mainDrawer
        val menu = binding.menuDrawer
//        if(drawer.isDrawerOpen(menu.root)){
            drawer.closeDrawer(menu.root)
//        }else{
//            drawer.openDrawer(menu.root)
//        }
    }

    override fun onBackPressed() {
        val drawer = binding.mainDrawer
        val menu = binding.menuDrawer
        if(drawer.isDrawerOpen(menu.root)){
            drawer.closeDrawer(menu.root)
        }else{
            super.onBackPressed()
        }
    }
}