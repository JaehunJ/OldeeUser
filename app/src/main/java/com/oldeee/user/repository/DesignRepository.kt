package com.oldeee.user.repository

import android.content.SharedPreferences
import com.oldeee.user.base.BaseRepository
import com.oldeee.user.network.OldeeService
import com.oldeee.user.network.RemoteData
import com.oldeee.user.network.request.AddCartRequest
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DesignRepository @Inject constructor(api: OldeeService, preferences: SharedPreferences) :
    BaseRepository(api, preferences) {

    suspend fun requestDesignList(limit: Int, page: Int) =
        call { api.requestDesignList(getAccessToken(), limit, page) }

    suspend fun requestDesignDetail(id: Int) = call {
        api.requestDesignDetail(getAccessToken(), id)
    }

    suspend fun requestAddCart(onError:(RemoteData.ApiError)->Unit, data: AddCartRequest) =
        call { api.requestAddCart(getAccessToken(), data) }

    suspend fun requestCartList() = call {
        api.requestBasketList(getAccessToken())
    }

//    suspend fun requestReformRequest(data:)

//    suspend fun requestHeartState(id:Int, checked:Int) = {
//        call { api.request }
//    }
}