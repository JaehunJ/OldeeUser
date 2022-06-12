package com.oldeee.user.network

sealed class RemoteData<out T : Any> {
    data class Success<out T : Any>(val output: T) : RemoteData<T>()
    data class Error(val exception: Exception) : RemoteData<Nothing>()
    data class ApiError(val errorCode: String?, val errorMessage: String?) : RemoteData<Nothing>()
}