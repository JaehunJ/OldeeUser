package com.oldee.user

import android.app.Dialog
import com.oldee.user.databinding.LayoutHomeMenuBinding

interface CommonActivityFuncImpl {
    fun showProgress()
    fun hideProgress()
    fun showToast(msg: String)
    fun showSnackBar(msg: String)
//    fun openDrawerMenu()
//    fun hideDrawerMenu()
//    fun setDrawerName(name:String)
    fun goFinish()
//    fun isDrawerOpen():Boolean
    fun hideSoftKeyboard()
    fun logout()
    fun openMenu(dialog: Dialog?, binding:LayoutHomeMenuBinding)
}