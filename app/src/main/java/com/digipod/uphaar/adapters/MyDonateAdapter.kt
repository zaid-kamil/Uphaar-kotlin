package com.digipod.uphaar.adapters

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.digipod.uphaar.R
import com.digipod.uphaar.models.DonateModel
import com.google.android.material.card.MaterialCardView

class MyDonateAdapter(
    private val donatedItemList: ArrayList<DonateModel>,
    private val layout: Int
) : RecyclerView.Adapter<MyDonateAdapter.Holder>() {
    class Holder(iv: View) : RecyclerView.ViewHolder(iv) {
        private val textItem: TextView = iv.findViewById(R.id.textItem)
        private val textPlace: TextView = iv.findViewById(R.id.textPlace)
        private val img: ImageView = iv.findViewById(R.id.img)
        private val card: MaterialCardView = iv.findViewById(R.id.card)
        fun bind(item: DonateModel) {
            textPlace.text = item.donationPlace
            textItem.text = item.item
            Glide.with(img).load(item.imgUrl).placeholder(R.drawable.ic_baseline_image_24).into(img)
            when (item.deliveryStatus) {
                "pending" -> card.setCardBackgroundColor(Color.YELLOW)
                "complete" -> card.setCardBackgroundColor(Color.GREEN)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val view = LayoutInflater.from(parent.context).inflate(layout, parent, false)
        return Holder(view)
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        val item = donatedItemList[position]
        holder.bind(item)
    }

    override fun getItemCount() = donatedItemList.size
}