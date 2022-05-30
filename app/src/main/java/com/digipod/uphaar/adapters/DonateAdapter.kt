package com.digipod.uphaar.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.digipod.uphaar.listeners.OnDonateItemClickListener
import com.digipod.uphaar.models.DonateModel

class DonateAdapter(
    private val donatedItemList: ArrayList<DonateModel>,
    private val listener: OnDonateItemClickListener,
    private val layout: Int
) : RecyclerView.Adapter<DonateAdapter.Holder>() {
    class Holder(iv: View) : RecyclerView.ViewHolder(iv) {

        fun bind(item: DonateModel) {

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