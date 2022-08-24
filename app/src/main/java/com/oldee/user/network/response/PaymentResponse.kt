package com.oldee.user.network.response

/**
 * {
 *      "type":"fail",
 *      "code":"PAY_PROCESS_CANCELED",
 *      "msg":"사용자가 결제를 취소하였습니다",
 *      "locatedView":"https://dev.oldee.co.kr/api/v1/payment/fail"
 * }
 *
 * @property type
 * @property code
 * @property msg
 * @property locatedView
 */
data class PaymentFailResponse (
    val type: String,
    val code: String,
    val msg: String,
    val locatedView: String
)

