package com.oldeee.user.ui

import androidx.appcompat.app.AppCompatActivity
import com.oldeee.user.CommonActivityFuncImpl
import com.oldeee.user.databinding.ActivityMainBinding

class MainActivity:AppCompatActivity(), CommonActivityFuncImpl {
    lateinit var _binding:ActivityMainBinding


    override fun showProgress() {

    }

    override fun hideProgress() {

    }

    override fun showBottomNavi() {

    }

    override fun hideBottomNavi() {

    }

    override fun showToast(msg: String) {

    }

    override fun showSnackBar(msg: String) {
        TODO("Not yet implemented")
    }

    override fun showSnackBarWithButton(msg: String, btnText: String, onClick: () -> Unit) {
        TODO("Not yet implemented")
    }
}