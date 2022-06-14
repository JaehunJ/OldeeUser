package com.oldeee.user.network.response

abstract class BaseResponse {
    abstract var count:Int?
    abstract var status:Int?
    abstract var message:String?
    abstract var errorMessage: String?
    abstract var errorCode: String?
}