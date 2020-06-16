package com.agh.wtm.vehiclemanager.fragments


import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.agh.wtm.vehiclemanager.AddFuellingActivity
import com.agh.wtm.vehiclemanager.MainActivity
import com.agh.wtm.vehiclemanager.R
import com.agh.wtm.vehiclemanager.adapters.RefuellingAdapter
import com.agh.wtm.vehiclemanager.db.VehicleContract
import com.agh.wtm.vehiclemanager.db.VehicleDBHelper
import com.agh.wtm.vehiclemanager.model.Vehicle
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.android.synthetic.main.fragment_refuelling_list.*

/**
 * A simple [Fragment] subclass.
 */
class RefuellingListFragment constructor(private val mCtx: Context): Fragment() {

    private var dbHelper: VehicleDBHelper? = null
    private var currentVehicle: Vehicle? = null
    private var addFuellingFab: FloatingActionButton? = null
    private var refuellingListAdapter: RefuellingAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_refuelling_list, container, false)
        dbHelper = VehicleDBHelper(mCtx, VehicleContract.tables)
        addFuellingFab = view.findViewById(R.id.add_fuelling_fab)
        val mainActivity: MainActivity? = activity as MainActivity?
        currentVehicle = mainActivity!!.getCurrentVehicle()

        addFuellingFab!!.setOnClickListener {
            run {
                val intent = Intent(activity, AddFuellingActivity::class.java)
                intent.putExtra("carId", currentVehicle!!.getId())
                startActivity(intent)
            }

        }
        return view
    }



    override fun onActivityCreated(savedInstanceState: Bundle?) {
        refuellings_list.adapter = RefuellingAdapter(getFuellings())
        refuellings_list.layoutManager = LinearLayoutManager(mCtx)
        refuellings_list.setEmptyView(no_refuellings)
        refuellings_list.setHasFixedSize(true)
        super.onActivityCreated(savedInstanceState)
    }

    private fun getFuellings() =
        if (currentVehicle != null)
            dbHelper!!.getFuellingsForVehicle(currentVehicle!!.getId())
        else listOf()

}
