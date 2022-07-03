package com.oldeee.user.ui.cart

import androidx.lifecycle.MutableLiveData
import com.oldeee.user.base.BaseViewModel
import com.oldeee.user.network.request.BasketItemDeleteData
import com.oldeee.user.network.request.BasketItemDeleteRequest
import com.oldeee.user.network.response.BasketListItem
import com.oldeee.user.usercase.DeleteCartListItemUseCase
import com.oldeee.user.usercase.GetCartListUserCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(
    private val getCartListUserCase: GetCartListUserCase,
    private val deleteCartListItemUseCase: DeleteCartListItemUseCase
) : BaseViewModel() {
    val res = MutableLiveData<List<BasketListItem>>()

    val totalPrice = MutableLiveData<Int>()
    val totalCheck = MutableLiveData<Boolean>()

    init {

        totalPrice.value = 0
        totalCheck.value = false
    }


    fun requestCartList() {
        remote {
            val result = getCartListUserCase.invoke()

            result?.let {
                res.postValue(it.data)
            }
        }
    }

    fun requestCartListItemDelete(list : List<BasketListItem>){
        remote {
            val requestChildList = mutableListOf<BasketItemDeleteData>()
            list.forEach {
                requestChildList.add(BasketItemDeleteData(it.basketId))
            }

            val request = BasketItemDeleteRequest(requestChildList)

            val result = deleteCartListItemUseCase.invoke(request)

            result?.let{
                requestCartList()
            }
        }
    }

    data class ObserveListItem(val item: BasketListItem, val checked: Boolean)
}