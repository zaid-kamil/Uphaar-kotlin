package com.digipod.uphaar.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.digipod.uphaar.listeners.OnDonationPlaceSelectionListener
import com.digipod.uphaar.models.DonationPlace

class PlaceAdapter(
    private val placeList: ArrayList<DonationPlace>,
    private val listener: OnDonationPlaceSelectionListener,
    private val layout: Int
) : RecyclerView.Adapter<PlaceAdapter.Holder>() {
    class Holder(iv: View) : RecyclerView.ViewHolder(iv) {

        fun bind(item: DonationPlace, listener: OnDonationPlaceSelectionListener) {

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(parent.context).inflate(layout, parent, false)
        return Holder(view)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val item = placeList[position]
        holder.bind(item, listener)
    }

    override fun getItemCount() = placeList.size
}