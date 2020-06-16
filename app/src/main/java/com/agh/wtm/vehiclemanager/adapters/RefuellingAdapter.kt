package com.agh.wtm.vehiclemanager.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.agh.wtm.vehiclemanager.R
import com.agh.wtm.vehiclemanager.model.Fuelling
import kotlinx.android.synthetic.main.fuelling_item.view.*
import java.text.SimpleDateFormat

class RefuellingAdapter(var refuellingList: List<Fuelling>): RecyclerView.Adapter<RefuellingAdapter.RefuellingViewHolder>() {

    class RefuellingViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val dateField: TextView = itemView.refuelling_date_field
        val priceField: TextView = itemView.refuelling_price_field
        val amountField: TextView = itemView.refuelling_amount_field
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RefuellingViewHolder {
        val itemView = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.fuelling_item, parent, false)
        return RefuellingViewHolder(itemView)
    }

    override fun getItemCount() = refuellingList.size

    override fun onBindViewHolder(holder: RefuellingViewHolder, position: Int) {
        val currentItem = refuellingList[position]

        val formatter = SimpleDateFormat("YYYY-mm-dd")
        holder.dateField.text = formatter.format(currentItem.date)
        holder.priceField.text = String.format("%.2f zł", currentItem.pricePerLitre)
        holder.amountField.text = currentItem.fuelAmount.toString()
    }

}