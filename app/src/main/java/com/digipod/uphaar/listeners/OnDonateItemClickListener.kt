package com.digipod.uphaar.listeners

import android.view.View
import com.digipod.uphaar.models.DonateModel

interface OnDonateItemClickListener {
    fun onItemClick(v:View,d:DonateModel,pos:Int)
}
