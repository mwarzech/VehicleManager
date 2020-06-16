package com.agh.wtm.vehiclemanager.fragments


import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.agh.wtm.vehiclemanager.AddVehicleActivity
import com.agh.wtm.vehiclemanager.MainActivity
import com.agh.wtm.vehiclemanager.R
import com.agh.wtm.vehiclemanager.adapters.VehicleListAdapter
import com.agh.wtm.vehiclemanager.db.VehicleContract
import com.agh.wtm.vehiclemanager.db.VehicleDBHelper
import com.agh.wtm.vehiclemanager.model.Vehicle
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.android.synthetic.main.fragment_vehicle_manager.*
import com.agh.wtm.vehiclemanager.db.VehicleContract.VehicleEntry as Vehicles


/**
 * A simple [Fragment] subclass.
 */
class VehicleManagerFragment constructor(private val mCtx: Context): Fragment() {

    private var addVehicleFab: FloatingActionButton? = null
    private var dbHelper: VehicleDBHelper? = null
    private var vehicleListAdapter: VehicleListAdapter? = null

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

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        vehicleListAdapter = VehicleListAdapter(getVehicles())
        vehicleListAdapter!!.setOnItemClickListener(object : VehicleListAdapter.OnItemClickListener {
            override fun onDeleteClick(position: Int) {
                val vehicleToRemove: Vehicle = vehicleListAdapter!!.getElementOnPosition(position)
                dbHelper!!.deleteById(Vehicles, vehicleToRemove.id)
                vehicleListAdapter!!.vehicleList = getVehicles()
                (activity as MainActivity).removeFromSpinner(vehicleToRemove)
                vehicleListAdapter!!.notifyItemRemoved(position)
            }
        })
        vehicle_list.adapter = vehicleListAdapter
        vehicle_list.layoutManager = LinearLayoutManager(mCtx)
        vehicle_list.setEmptyView(no_vehicles)
        vehicle_list.hasFixedSize()

        super.onActivityCreated(savedInstanceState)
    }

    fun updateVehicleList() {
        vehicleListAdapter!!.vehicleList = getVehicles()
        vehicleListAdapter!!.notifyDataSetChanged()
    }

    private fun getVehicles(): List<Vehicle> {
        return dbHelper!!.getAll(Vehicles)
    }
}
