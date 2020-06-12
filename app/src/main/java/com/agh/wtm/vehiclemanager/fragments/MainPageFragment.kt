package com.agh.wtm.vehiclemanager.fragments


import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.agh.wtm.vehiclemanager.MainActivity
import com.agh.wtm.vehiclemanager.R
import com.agh.wtm.vehiclemanager.model.Vehicle

/**
 * A simple [Fragment] subclass.
 */
class MainPageFragment constructor(private val mCtx: Context) : Fragment() {

    private var vehicleNameText: TextView? = null
    private var vehicleMileageText: TextView? = null
    private var currentVehicle: Vehicle? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.fragment_main_page, container, false)

        vehicleNameText = view.findViewById(R.id.vehicle_name)
        vehicleMileageText = view.findViewById(R.id.vehicle_mileage)
        val activity: MainActivity? = activity as MainActivity?
        currentVehicle = activity!!.getCurrentVehicle()
        displayVehicleData()
        return view
    }

    private fun displayVehicleData() {
        vehicleNameText!!.text = currentVehicle!!.name
        vehicleMileageText!!.text = currentVehicle!!.mileage.toString()
    }

}
