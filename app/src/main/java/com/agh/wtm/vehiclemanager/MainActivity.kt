package com.agh.wtm.vehiclemanager

import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.agh.wtm.vehiclemanager.db.VehicleContract
import com.agh.wtm.vehiclemanager.db.VehicleDBHelper
import com.agh.wtm.vehiclemanager.model.Vehicle

class MainActivity : AppCompatActivity() {

    var dbHelper: VehicleDBHelper? = null
    var insertVehicleBtn: Button? = null
    var selectVehiclesBtn: Button? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        dbHelper = VehicleDBHelper(this, VehicleContract.tables)
        insertVehicleBtn = findViewById(R.id.insert_vehicle_btn)
        selectVehiclesBtn = findViewById(R.id.log_vehicles)

        insertVehicleBtn!!.setOnClickListener {
            run {
                val vehicle = Vehicle(1, "warzecha", Vehicle.VehicleType.CAR, 199998)
                val insertedId = dbHelper!!.insert(VehicleContract.VehicleEntry, vehicle)
                Log.d("Inserted id: ", "" + insertedId)
            }
        }

        selectVehiclesBtn!!.setOnClickListener {
            run {
                val vehicles = dbHelper!!.getAll(VehicleContract.VehicleEntry)
                vehicles.forEach {
                    Log.d("Vehicle", "Id: " + it.id + ", name: " + it.name + ", type: " + it.type + ", mileage: " + it.mileage)
                }
            }
        }

    }

}
