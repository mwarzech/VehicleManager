package com.agh.wtm.vehiclemanager.fragments


import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.agh.wtm.vehiclemanager.MainActivity

import com.agh.wtm.vehiclemanager.R
import com.agh.wtm.vehiclemanager.adapters.RefuellingAdapter
import com.agh.wtm.vehiclemanager.db.VehicleContract
import com.agh.wtm.vehiclemanager.db.VehicleDBHelper
import com.agh.wtm.vehiclemanager.model.Vehicle
import kotlinx.android.synthetic.main.fragment_refuelling_list.*

/**
 * A simple [Fragment] subclass.
 */
class RefuellingListFragment constructor(private val mCtx: Context): Fragment() {

    private var dbHelper: VehicleDBHelper? = null
    private var currentVehicle: Vehicle? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        dbHelper = VehicleDBHelper(mCtx, VehicleContract.tables)
        val activity: MainActivity? = activity as MainActivity?
        currentVehicle = activity!!.getCurrentVehicle()

        return inflater.inflate(R.layout.fragment_refuelling_list, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        refuellings_list.adapter = RefuellingAdapter(getFuellings())
        refuellings_list.layoutManager = LinearLayoutManager(mCtx)
        refuellings_list.setHasFixedSize(true)
        super.onActivityCreated(savedInstanceState)
    }

    private fun getFuellings() = dbHelper!!.getFuellingsForVehicle(currentVehicle!!.id)

}
