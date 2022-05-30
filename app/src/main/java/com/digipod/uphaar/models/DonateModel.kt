package com.digipod.uphaar.models

data class DonateModel(
    val uid: String = "",
    val donationPlace: String = "",
    val item: String = "",
    val imgUrl: String = "",
    val pickupPlace: String = "",
    val qty: Int = 0,
    val deliveryStatus: String = "pending"
)
