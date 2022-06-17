package com.oldeee.user

interface CommonActivityFuncImpl {
    fun showProgress()
    fun hideProgress()
    fun showToast(msg: String)
    fun showSnackBar(msg: String)
    fun openDrawerMenu()
    fun hideDrawerMenu()
    fun setDrawerName(name:String)
}