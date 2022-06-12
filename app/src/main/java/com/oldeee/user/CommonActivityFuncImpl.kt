package com.oldeee.user

interface CommonActivityFuncImpl {
    fun showProgress()
    fun hideProgress()
    fun showBottomNavi()
    fun hideBottomNavi()
    fun showToast(msg: String)
    fun showSnackBar(msg: String)
    fun showSnackBarWithButton(msg: String, btnText: String, onClick: () -> Unit)
}