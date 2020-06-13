package com.agh.wtm.vehiclemanager.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import androidx.annotation.LayoutRes
import com.agh.wtm.vehiclemanager.R
import com.agh.wtm.vehiclemanager.model.Vehicle
import kotlinx.android.synthetic.main.vehicle_spinner_row.view.*

class VehicleAdapter(context: Context, @LayoutRes private val layoutResource: Int, vehicleList: List<Vehicle>):
    ArrayAdapter<Vehicle>(context, layoutResource, vehicleList) {

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup?): View {
        return initView(position, convertView, parent)
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        return initView(position, convertView, parent)
    }

    private fun initView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val cView: View = convertView ?: LayoutInflater.from(context).inflate(R.layout.vehicle_spinner_row, parent, false)

        val idLabel: TextView = cView.spinner_vehicle_id
        val nameLabel: TextView = cView.spinner_vehicle_name
        val mileageLabel: TextView = cView.spinner_vehicle_mileage

        val currentVehicle: Vehicle? = getItem(position)

        if (currentVehicle != null) {
            idLabel.text = currentVehicle!!.id.toString()
            nameLabel.text = currentVehicle!!.name
            mileageLabel.text = currentVehicle!!.mileage.toString()
        }

        return cView
    }

}