package com.digipod.uphaar.listeners

import android.view.View
import com.digipod.uphaar.models.DonationPlace

interface OnDonationPlaceSelectionListener {
    fun onDonationPlaceSelected(v: View, place: DonationPlace, pos: Int)
}