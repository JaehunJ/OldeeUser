package com.oldeee.user.usercase

import com.oldeee.user.network.RemoteData
import com.oldeee.user.network.request.*
import com.oldeee.user.network.response.SignInResponseData
import com.oldeee.user.repository.*
import okhttp3.MultipartBody
import java.io.PipedReader
import javax.inject.Inject

class SetTokenUseCase @Inject constructor(private val repo: CommonRepository) {
    operator fun invoke(data: SignInResponseData) {
        repo.setToken(data)
    }
}

class GetAutoLoginValue @Inject constructor(private val repo: SignRepository) {
    operator fun invoke() = repo.getAutoLoginValue()
}

class GetUserData @Inject constructor(private val repo:UserRepository){
    operator fun invoke() = repo.getUserData()
}

class SetUserData @Inject constructor(private val repo:UserRepository){
    operator fun invoke(name:String, email:String, phone:String) = repo.setUserData(name, email, phone)
}

class SetAutoLoginValue @Inject constructor(private val repo: SignRepository) {
    operator fun invoke(boolean: Boolean) = repo.setAutoLoginValue(boolean)
}

class SetNaverSignInUseCase @Inject constructor(private val repo: SignRepository) {
    suspend operator fun invoke(data: NaverSignInRequest, onError: (RemoteData.ApiError) -> Unit) =
        repo.requestNaverSignIn(data, onError)
}

class SetSignUpUseCase @Inject constructor(private val repo: SignRepository) {
    suspend operator fun invoke(data: SignUpRequest) = repo.requestSignUp(data)
}

class GetDesignListUseCase @Inject constructor(private val repository: DesignRepository) {
    suspend operator fun invoke(limit: Int, page: Int) = repository.requestDesignList(limit, page)
}

class GetDesignDetailUseCase @Inject constructor(private val repo: DesignRepository) {
    suspend operator fun invoke(id: Int) = repo.requestDesignDetail(id)
}

class PostAddCartUseCase @Inject constructor(private val repo:DesignRepository){
    suspend operator fun invoke(onError: (RemoteData.ApiError) -> Unit, data:AddCartRequest) = repo.requestAddCart(onError, data)
}

class PostPaymentUseCase @Inject constructor(private val repo:DesignRepository){
    suspend operator fun invoke(data:PaymentRequest) = repo.requestPayment(data)
}

//log
class GetPaymentListUseCase @Inject constructor(private val repo:DesignRepository){
    suspend operator fun invoke(successYn:Int) = repo.requestPaymentList(successYn)
}

class GetAddressListUseCase @Inject constructor(private val repo:DesignRepository){
    suspend operator fun invoke() = repo.requestAddressList()
}

class GetAddressByIdUserCase @Inject constructor(private val repo:DesignRepository){
    suspend operator fun invoke(id:Int) = repo.requestAddressById(id)
}

class PostAddressUseCase @Inject constructor(private val repo:DesignRepository){
    suspend operator fun invoke(data: AddShippingAddressRequest) = repo.requestAddAddress(data)
}

class GetExpertListUseCase @Inject constructor(private val repo: ExpertRepository) {
    suspend operator fun invoke() = repo.requestExpertList()
}

class GetImageUseCase @Inject constructor(private val repo: CommonRepository) {
    suspend operator fun invoke(path: String) = repo.getImageFromServer(path)
}

class PostImageUseCase @Inject constructor(private val repo:CommonRepository){
    suspend operator fun invoke(list:List<MultipartBody.Part>) = repo.postImageToServer(list)
}

class GetNoticeListUseCase @Inject constructor(private val repo:CommonRepository){
    suspend operator fun invoke() = repo.requestNoticeList()
}

//cart
class GetCartListUserCase @Inject constructor(private val repo:DesignRepository){
    suspend operator fun invoke() = repo.requestCartList()
}



class SetHeartCheckUseCase @Inject constructor(private val repo: DesignRepository) {

}