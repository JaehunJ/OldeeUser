package com.oldee.user.repository

import android.content.SharedPreferences
import com.oldee.user.base.BaseRepository
import com.oldee.user.network.OldeeService
import com.oldee.user.network.RemoteData
import com.oldee.user.network.request.*
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

    suspend fun requestCartDelete(data: BasketItemDeleteRequest) = call{
        api.requestDeleteBasketItem(getAccessToken(), data)
    }

    suspend fun requestAddCart(onError:(RemoteData.ApiError)->Unit, data: AddCartRequest) =
        call { api.requestAddCart(getAccessToken(), data) }

    suspend fun requestCartList() = call {
        api.requestBasketList(getAccessToken())
    }

    suspend fun requestGetPaymentPage(data: PaymentPageRequest):String?{
        val result = api.requestGetPaymentPage(data)

        if(result.isSuccessful){
            result.body()?.let{
                return it.toString()
            }
        }

        return null
    }

    suspend fun requestPaymentList(successYn:Int, limit:Int? = null, page:Int? = null) = call {
        api.requestPaymentList(
            token = getAccessToken(),
            orderId = null,
            successYn = successYn,
            limit = limit,
            page = page
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

    suspend fun requestAddressList(code:Int) = call{
        api.requestShippingAddressList(token = getAccessToken(), shippingLastYn = code)
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