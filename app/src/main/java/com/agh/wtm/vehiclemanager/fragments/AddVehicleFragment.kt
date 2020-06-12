package com.agh.wtm.vehiclemanager.fragments


import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import com.agh.wtm.vehiclemanager.MainActivity

import com.agh.wtm.vehiclemanager.R
import com.agh.wtm.vehiclemanager.db.VehicleContract
import com.agh.wtm.vehiclemanager.db.VehicleDBHelper
import com.agh.wtm.vehiclemanager.model.Vehicle

/**
 * A simple [Fragment] subclass.
 */
class AddVehicleFragment constructor(private val mCtx: Context): Fragment() {

    private var addVehicleBtn: Button? = null
    private var vehicleNameInput: EditText? = null
    private var vehicleTypeInput: Spinner? = null
    private var vehicleMileageInput: EditText? = null
    private var dbHelper: VehicleDBHelper? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.fragment_add_vehicle, container, false)
        addVehicleBtn = view.findViewById(R.id.add_vehicle_btn)
        vehicleNameInput = view.findViewById(R.id.vehicle_name_field)
        vehicleTypeInput = view.findViewById(R.id.vehicle_type_input)
        vehicleMileageInput = view.findViewById(R.id.mileage_input_field)

        vehicleTypeInput!!.adapter = ArrayAdapter(mCtx, android.R.layout.simple_selectable_list_item, Vehicle.VehicleType.values())
        dbHelper = VehicleDBHelper(mCtx, VehicleContract.tables)

        addVehicleBtn!!.setOnClickListener {
            run {
               addVehicle()
            }
        }

        return view
    }

    private fun addVehicle() {
        val vehicleName = vehicleNameInput!!.text.toString()
        val vehicleType = vehicleTypeInput!!.selectedItem.toString()
        val mileage = vehicleMileageInput!!.text.toString().toInt()

        val newVehicle = Vehicle(0, vehicleName, Vehicle.VehicleType.valueOf(vehicleType), mileage)
        dbHelper!!.insert(VehicleContract.VehicleEntry, newVehicle)
        vehicleNameInput!!.text.clear()
        vehicleMileageInput!!.text.clear()

        val mainActivity: MainActivity = activity as MainActivity
        mainActivity.addToSpinner(vehicleName)
    }

}
