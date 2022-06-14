package com.oldeee.user.network.response

data class PaymentListResponse(
    override var count: Int?,
    override var status: Int?,
    override var message: String?,
    override var errorMessage: String?,
    override var errorCode: String?,
    val data:List<PaymentListItem>
):BaseResponse()

data class PaymentListItem(
    val surveySeqList:List<SurveySeqListItem>,
    val shippingName:String,
    val orderId:Int,
    val totalPrice:Int,
    val postalCode:String,
    val orderNum:String,
    val shippingPhone:String,
    val creationDate:String,
    val shippingFee:Int,
    val modifiedDate:String,
    val shippingAddress:String,
    val orderPrice:Int,
    val shippingAddressDetail:String
)

data class SurveySeqListItem(
    val reformItem:String,
    val classCode:String,
    val reviewYn:Boolean,
    val expertName:String,
    val imagePath:String,
    val reformPrice:String,
    val reformId:String,
    val orderDetailNum:String,
    val orderDetailId:Int,
    val mainCode:String,
    val orderStatusName:String,
    val surveySeq:Int,
    val storeAddress:String,
    val valueList:String,
    val mainName:String,
    val storeName:String,
    val expertPhone:String
)
/*

{
    "count": 2,
    "data": [
        {
            "surveySeqList": [
                {
                    "reformItem": "모자",
                    "classCode": "R",
                    "reviewYn": false,
                    "expertName": "선주전문가",
                    "imagePath": "15031197094778218-2487f10c-f09b-4f21-bd5a-d866648a9df8",
                    "reformPrice": "50000",
                    "orderDetailId": 169,
                    "mainCode": "R65",
                    "orderStatusName": "주문완료",
                    "surveySeq": 6,
                    "storeAddress": "인천 남동구 논현동",
                    "valueList": "0",
                    "mainName": "영찬님꺼",
                    "storeName": "선주 상점",
                    "expertPhone": "01028511320"
                }
            ],
            "shippingName": "따봉이",
            "shippingFee": 3000,
            "orderId": 131,
            "totalPrice": 53000,
            "postalCode": "10032",
            "modifiedDate": "22-04-26 15:57:41",
            "shippingAddress": "서울시 강서구",
            "shippingPhone": "014124242",
            "orderPrice": 50000,
            "creationDate": "22-04-26 15:57:41",
            "shippingAddressDetail": "디디디2"
        },
        {
            "surveySeqList": [
                {
                    "reformItem": "ㅎㅎ",
                    "classCode": "R",
                    "reviewYn": false,
                    "expertName": "전3",
                    "imagePath": "15032114873603263-b6005290-b7ae-4dcf-88c5-b80c933aaf39",
                    "reformPrice": "50000",
                    "orderDetailId": 167,
                    "mainCode": "R67",
                    "orderStatusName": "주문완료",
                    "surveySeq": 2,
                    "storeAddress": "인천",
                    "valueList": "Ccgg",
                    "mainName": "상품명",
                    "storeName": "테스트 상점3",
                    "expertPhone": "01011113333"
                },
                {
                    "reformItem": "가죽가방",
                    "classCode": "R",
                    "reviewYn": false,
                    "expertName": "전3",
                    "imagePath": "15029152290656657-7823bc97-0a2b-46a1-b16b-f0b5cc969b27",
                    "reformPrice": "54000",
                    "orderDetailId": 168,
                    "mainCode": "R64",
                    "orderStatusName": "주문완료",
                    "surveySeq": 3,
                    "storeAddress": "인천",
                    "valueList": "To yo lk",
                    "mainName": "가방 브랜드 변경",
                    "storeName": "테스트 상점3",
                    "expertPhone": "01011113333"
                }
            ],
            "shippingName": "따봉이",
            "shippingFee": 3000,
            "orderId": 130,
            "totalPrice": 107000,
            "postalCode": "10032",
            "modifiedDate": "22-04-26 05:14:20",
            "shippingAddress": "서울시 강서구",
            "shippingPhone": "014124242",
            "orderPrice": 104000,
            "creationDate": "22-04-26 05:14:20",
            "shippingAddressDetail": "디디디"
        }
    ],
    "status": 200,
    "message": "success"
}
 */