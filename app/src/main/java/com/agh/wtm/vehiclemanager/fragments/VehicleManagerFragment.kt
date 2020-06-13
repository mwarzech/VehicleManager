package com.agh.wtm.vehiclemanager.fragments


import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.agh.wtm.vehiclemanager.AddVehicleActivity
import com.agh.wtm.vehiclemanager.R
import com.agh.wtm.vehiclemanager.db.VehicleContract
import com.agh.wtm.vehiclemanager.db.VehicleDBHelper
import com.google.android.material.floatingactionbutton.FloatingActionButton

/**
 * A simple [Fragment] subclass.
 */
class VehicleManagerFragment constructor(private val mCtx: Context): Fragment() {

    private var addVehicleFab: FloatingActionButton? = null
    private var dbHelper: VehicleDBHelper? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.fragment_vehicle_manager, container, false)
        addVehicleFab = view.findViewById(R.id.add_vehicle_fab)
        dbHelper = VehicleDBHelper(mCtx, VehicleContract.tables)

        addVehicleFab!!.setOnClickListener {
            val intent = Intent(activity, AddVehicleActivity::class.java)
            startActivity(intent)
        }

        return view
    }
}
