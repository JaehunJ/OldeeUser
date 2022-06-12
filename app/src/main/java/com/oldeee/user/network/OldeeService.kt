package com.oldeee.user.network

import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.*

interface OldeeService {
    @GET("/api/v1/expert/image/view")
    suspend fun requestImage(
        @Header("Authorization") authorization: String,
        @Query("imageName") name: String
    ): Response<ResponseBody>

//    @Multipart
//    @POST("/api/v1/expert/image")
//    suspend fun requestPostImage(
//        @Header("Authorization") authorization: String,
//        @Part files: List<MultipartBody.Part>,
//        @Part("imageType") imageType: RequestBody
//    ): Response<ImageResponse>
}