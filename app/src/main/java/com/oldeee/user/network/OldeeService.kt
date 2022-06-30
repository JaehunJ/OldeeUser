package com.oldeee.user.network

import com.oldeee.user.network.request.*
import com.oldeee.user.network.response.*
import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.*

interface OldeeService {
    @GET("/api/v1/user/version")
    suspend fun requestVersionInfo(
        @Header("Authorization") authorization: String = "clo",
        @Query("osType") osType: String = "android"
    ): Response<VersionInfoResponse>

    @POST("/api/v1/token/refresh")
    suspend fun requestNewToken(
        @Header("Authorization") authorization: String = "clo",
        @Body data:NewTokenRequest
    ):Response<NewTokenResponse>

    @Multipart
    @POST("/api/v1/user/image")
    suspend fun requestPostImage(
        @Header("Authorization") token: String,
        @Part files: List<MultipartBody.Part>,
        @Part("imageType") imageType: RequestBody
    ): Response<ImageResponse>

    @GET("/api/v1/user/image/view")
    suspend fun requestImage(
        @Header("Authorization") token: String,
        @Query("imageName") name: String
    ): Response<ResponseBody>

    //user data
    @POST("/api/v1/join/user")
    suspend fun requestSignUp(
        @Header("Authorization") authorization: String = "clo",
        @Body data: SignUpRequest
    ): Response<SignUpResponse>

    @POST("/api/v1/sns/naverlogin")
    suspend fun requestSignInFromNaver(
        @Header("Authorization") authorization: String = "clo",
        @Body data: NaverSignInRequest
    ): Response<SignInResponse>

    @POST("/api/v1/user/secession")
    suspend fun requestWithdraw(
        @Header("Authorization") token: String,
        @Body data: WithdrawRequest
    ): Response<BaseStringResponse>

    //expert
    @GET("/api/v1/user/expert/list")
    suspend fun requestExpertList(
        @Header("Authorization") token: String
    ): Response<ExpertListResponse>

    //reform design
    @GET("api/v1/user/reform/product/list?limit=3&page=0")
    suspend fun requestDesignList(
        @Header("Authorization") token: String,
        @Query("limit") limit: Int,
        @Query("page") page: Int
    ): Response<DesignListResponse>

    @GET("/api/v1/user/reform/product/detail")
    suspend fun requestDesignDetail(
        @Header("Authorization") token: String,
        @Query("reformId") reformId: Int
    ): Response<DesignDetailResponse>

    @POST("/api/v1/user/survey/reform")
    suspend fun requestAddCart(
        @Header("Authorization") token: String,
        @Body data:AddCartRequest
    ):Response<AddCartResponse>

//    @POST("/api/v1/user/reform/heart")
//    suspend fun requestHeartSave(
//        @Header("Authorization") token: String,
//        @Body data: Int
//    )

    @GET("/api/v1/user/basket/list")
    suspend fun requestBasketList(
        @Header("Authorization") token: String
    ): Response<BasketListResponse>

    //TODO work restart line
    @POST("/api/v1/user/basket/delete")
    suspend fun requestDeleteBasketItem(
        @Header("Authorization") token: String,
        @Body data: BasketItemDeleteRequest
    ): Response<BaseStringResponse>

    @POST("/api/v1/user/basket/detail/delete")
    suspend fun requestDeleteBasketDetailItem(
        @Header("Authorization") token: String,
        @Body data: BasketDetailDeleteRequest
    ): Response<BaseStringResponse>

    @POST("/api/v1/user/order")
    suspend fun requestPayment(
        @Header("Authorization") token: String,
        @Body data: PaymentRequest
    ): Response<BaseStringResponse>

    @GET("/api/v1/user/order/list")
    suspend fun requestPaymentList(
        @Header("Authorization") token: String,
        @Query("orderId") orderId: Int?,
        @Query("successYn") successYn: Int?,
        @Query("limit") limit: Int?,
        @Query("page") page: Int?
    ): Response<PaymentListResponse>

    @POST("/api/v1/user/order/address")
    suspend fun requestAddShippingAddress(
        @Header("Authorization") token: String,
        @Body data: AddShippingAddressRequest
    ): Response<ModifyShippingAddressResponse>

    @PUT("/api/v1/user/order/address")
    suspend fun requestModifyShippingAddress(
        @Header("Authorization") token: String,
        @Body data: ModifySHippingAddressRequest
    ): Response<ModifyShippingAddressResponse>

    @POST("/api/v1/user/order/address/delete")
    suspend fun requestDeleteShippingAddress(
        @Header("Authorization") token: String,
        @Body data: DeleteShippingAddressRequest
    ): Response<BaseStringResponse>

    @GET("/api/v1/user/order/address/list")
    suspend fun requestShippingAddressList(
        @Header("Authorization") token: String,
        @Query("shippingLastYn") shippingLastYn: Int? = null,
        @Query("addressId") addressId: Int? = null
    ): Response<ShippingAddressListResponse>

    //book mark
    @POST("/api/v1/user/reform/heart")
    suspend fun requestSaveBookMark(
        @Header("Authorization") token: String,
        @Body data: SaveBookMarkRequest
    ): Response<BaseStringResponse>

    @POST("/api/v1/user/reform/heart/delete")
    suspend fun requestDeleteBookMark(
        @Header("Authorization") token: String,
        @Body data: DeleteBookMarkRequest
    ): Response<BaseStringResponse>

    @GET("/api/v1/user/reform/heart/list")
    suspend fun requestBookMarkList(
        @Header("Authorization") token: String
    ): Response<BookMarkListResponse>

    @POST("api/v1/user/order/review")
    suspend fun requestSaveReviewLog(
        @Header("Authorization") token: String,
        @Body data: SaveReviewLogRequest
    ): Response<BaseStringResponse>

    @GET("/api/v1/user/order/review/list")
    suspend fun requestReviewList(
        @Header("Authorization") token: String,
        @Query("orderDetailId") orderDetailId: Int?,
        @Query("startNum") startNum: Int?,
        @Query("finishNum") finishNum: Int?
    ): Response<ReviewListResponse>


    //공지사항
    @GET("/api/v1/user/notice/main/list")
    suspend fun requestNoticeList(
        @Header("Authorization") token: String
    ): Response<NoticeResponse>

    @GET("/api/v1/user/notice/question/list")
    suspend fun requestFAQList(
        @Header("Authorization") token: String
    ): Response<ResponseBody>

    //설정

    //고객센터

    //전문가신청
}