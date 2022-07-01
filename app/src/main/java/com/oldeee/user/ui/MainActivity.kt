package com.oldeee.user.ui

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.NavDestination
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

    override fun onCreate(savedInstanceState: Bundle?) {
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
        }
        drawerBinding.tvSetting.setOnClickListener {
            hideDrawerMenu()
            findNavController(R.id.nav_host_fragment).navigate(R.id.action_global_settingFragment)
        }
        drawerBinding.tvSignUpExpert.setOnClickListener {
            hideDrawerMenu()
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
}