package com.digipod.uphaar.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.digipod.uphaar.R
import com.digipod.uphaar.listeners.OnDonationPlaceSelectionListener
import com.digipod.uphaar.models.DonationPlace

class PlaceAdapter(
    private val placeList: ArrayList<DonationPlace>,
    private val listener: OnDonationPlaceSelectionListener,
    private val layout: Int
) : RecyclerView.Adapter<PlaceAdapter.Holder>() {
    class Holder(iv: View) : RecyclerView.ViewHolder(iv) {
        val btn: Button =iv.findViewById(R.id.btnDonate)
        private val textTitle:TextView = iv.findViewById(R.id.textTitle)
        private val textCity:TextView = iv.findViewById(R.id.textCity)
        private val textAddress:TextView = iv.findViewById(R.id.textAddress)
        private val textDesc:TextView = iv.findViewById(R.id.textDesc)
        fun bind(item: DonationPlace) {
            textCity.text = item.city
            textAddress.text = item.address
            textTitle.text = item.name
            textDesc.text = item.description
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(parent.context).inflate(layout, parent, false)
        return Holder(view)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val item = placeList[position]
        holder.bind(item)
        holder.btn.tag =item
        holder.btn.setOnClickListener {
            val item = it.tag as DonationPlace
            listener.onDonationPlaceSelected(it,item,position)
        }

    }

    override fun getItemCount() = placeList.size
}