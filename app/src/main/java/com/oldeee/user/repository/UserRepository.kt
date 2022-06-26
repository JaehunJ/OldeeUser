package com.oldeee.user.repository

import android.content.SharedPreferences
import com.oldeee.user.base.BaseRepository
import com.oldeee.user.network.OldeeService
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepository @Inject constructor(api: OldeeService, prefs: SharedPreferences):BaseRepository(api, prefs){
    data class UserData(
        val userName:String,
        val userPhone:String,
        val userEmail:String
    )

    private var userData : UserData? = null

    fun setUserData(name:String, email:String, phone:String){
        userData = UserData(name, email, phone)
    }

    fun getUserData() = userData
}