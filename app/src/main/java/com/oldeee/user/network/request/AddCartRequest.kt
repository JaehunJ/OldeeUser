package com.oldeee.user.network.request

data class AddCartRequest(
    val code: String,
    val reformItemId: String,
    val contents: String,
    val imageNameList: List<String>
)