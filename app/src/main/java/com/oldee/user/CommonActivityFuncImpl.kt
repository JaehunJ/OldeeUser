package com.oldee.user

interface CommonActivityFuncImpl {
    fun showProgress()
    fun hideProgress()
    fun showToast(msg: String)
    fun showSnackBar(msg: String)
    fun openDrawerMenu()
    fun hideDrawerMenu()
    fun setDrawerName(name:String)
    fun goFinish()
    fun isDrawerOpen():Boolean
    fun hideSoftKeyboard()
}