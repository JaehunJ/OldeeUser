package com.oldee.user.repository

import android.content.SharedPreferences
import com.oldee.user.base.BaseRepository
import com.oldee.user.network.OldeeService
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepository @Inject constructor(api: OldeeService, prefs: SharedPreferences):BaseRepository(api, prefs){
    data class UserData(
        val userName:String,
        val userEmail:String,
        val userPhone:String,
        val snsId:String,
    )

    private var userData : UserData? = null

    fun setUserData(name:String, email:String, phone:String, snsId: String){
        userData = UserData(name, email, phone, snsId)
    }

    fun getUserData() = userData

}