package com.oldeee.user.data

import com.oldeee.user.R

enum class AppStatus(val value: Int) {
    None(0),
    Update(1),
    ForceUpdate(2),
    Check(9)
}

enum class UserStatus(val value: Int) {
    Normal(0),
    Start(1),
    Stop(2),
    Withdraw(8)
}

enum class OrderStatus(val value: Int) {
    Ready(0),
    OrderComplete(1),
    Working(2),
    WorkComplete(3),
    ShipmentComplete(4),
    Cancel(-4)
}

enum class Period(val value: Int) {
    MonthTotal(0),
    Month3(3),
    Month6(6),
    Month12(12)
}

enum class OrderType(val type: String) {
    A("A"), R("R")
}

enum class ClothCategory(val value: String) {
    Sweatshirt("티셔츠/맨투맨"),
    Shirts("셔츠/블라우스"),
    One("원피스"),
    Pants("바지"),
    Jean("청바지"),
    Skirts("치마"),
    Etc("기타")
}

enum class ClothCategoryCode(val value: String) {
    Sweatshirt("00"),
    Shirts("01"),
    One("02"),
    Pants("03"),
    Jean("04"),
    Skirts("05"),
    Etc("06")
}

enum class ReformStatus(val value: Int) {
    None(0),
    Start(2),
    Stop(3)
}

enum class PrepareItem(val value: String) {
    Standard("00"),
    Winter("01"),
    Outer("02"),
    Skirts("03"),
    Pants("04"),
    One("05"),
    Hats("06"),
    WinterItem("07"),
    Bag("08"),
    Shoes("09"),
    Acce("10"),
    Etc("11")
}

val PrepareItemMappingList = listOf(
    PrepareItem.Standard, PrepareItem.Winter,
    PrepareItem.Outer,
    PrepareItem.Skirts,
    PrepareItem.Pants,
    PrepareItem.One,
    PrepareItem.Hats,
    PrepareItem.WinterItem,
    PrepareItem.Bag,
    PrepareItem.Shoes,
    PrepareItem.Acce,
    PrepareItem.Etc
)

val PrepareItemMappingStringList = mapOf(
    Pair("00", R.drawable.ic_asset_01),
    Pair("01", R.drawable.ic_asset_02),
    Pair("02", R.drawable.ic_asset_03),
    Pair("03", R.drawable.ic_asset_04),
    Pair("04", R.drawable.ic_asset_05),
    Pair("05", R.drawable.ic_asset_06),
    Pair("06", R.drawable.ic_asset_07),
    Pair("07", R.drawable.ic_asset_08),
    Pair("08", R.drawable.ic_asset_09),
    Pair("09", R.drawable.ic_asset_10),
    Pair("10", R.drawable.ic_asset_11),
    Pair("11", R.drawable.ic_asset_12),
)