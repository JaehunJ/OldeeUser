package com.oldee.user.network.response

import com.google.gson.Gson

data class PostResponse(
    val postcode: String,
    val postcode1: String,
    val postcode2: String,
    val postcodeSeq: String,
    val zonecode: String,
    val address: String,
    val addressEnglish: String,
    val addressType: String,
    val bcode: String,
    val bname: String,
    val bnameEnglish: String,
    val bname1: String,
    val bname1English: String,
    val bname2: String,
    val bname2English: String,
    val sido: String,
    val sidoEnglish: String,
    val sigungu: String,
    val sigunguEnglish: String,
    val sigunguCode: String,
    val userLanguageType: String,
    val query: String,
    val buildingName: String,
    val buildingCode: String,
    val apartment: String,
    val jibunAddress: String,
    val jibunAddressEnglish: String,
    val roadAddress: String,
    val roadAddressEnglish: String,
    val autoRoadAddress: String,
    val autoRoadAddressEnglish: String,
    val autoJibunAddress: String,
    val autoJibunAddressEnglish: String,
    val userSelectedType: String,
    val noSelected: String,
    val hname: String,
    val roadnameCode: String,
    val roadname: String,
    val roadnameEnglish: String
){
    companion object {
        public fun fromJson(json: String) = Gson().fromJson(json, PostResponse::class.java)
    }
}
