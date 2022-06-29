package com.oldeee.user.repository

import android.content.SharedPreferences
import com.oldeee.user.base.BaseRepository
import com.oldeee.user.network.OldeeService
import com.oldeee.user.network.RemoteData
import com.oldeee.user.network.request.AddCartRequest
import com.oldeee.user.network.request.AddShippingAddressRequest
import com.oldeee.user.network.request.PaymentRequest
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

    suspend fun requestPaymentList(successYn:Int) = call {
        api.requestPaymentList(
            token = getAccessToken(),
            orderId = null,
            successYn = successYn,
            limit = null,
            page = null
        )
    }

    suspend fun requestPaymentDetail(orderId:Int) = call {
        api.requestPaymentList(
            token = getAccessToken(),
            orderId = orderId,
            successYn = null,
            limit = null,
            page = null
        )
    }

    suspend fun requestPayment(data:PaymentRequest) = call{
        api.requestPayment(getAccessToken(), data)
    }

    suspend fun requestAddressList() = call{
        api.requestShippingAddressList(token = getAccessToken(), shippingLastYn = 1)
    }

    suspend fun requestAddressById(id:Int) = call {
        api.requestShippingAddressList(token = getAccessToken(), shippingLastYn = null, id)
    }

    suspend fun requestAddAddress(data: AddShippingAddressRequest) = call {
        api.requestAddShippingAddress(getAccessToken(), data)
    }

//    suspend fun requestReformRequest(data:)

//    suspend fun requestHeartState(id:Int, checked:Int) = {
//        call { api.request }
//    }
}