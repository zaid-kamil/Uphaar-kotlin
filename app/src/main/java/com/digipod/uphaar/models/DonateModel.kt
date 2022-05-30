package com.digipod.uphaar.models

data class DonateModel(
    val uid: String = "",
    val donationPlace: String = "",
    val item: String = "",
    val imgUrl: String = "",
    val qty: Int = 0,
    val donationPlaceId:String = "",
    val deliveryStatus:String="pending"
)
