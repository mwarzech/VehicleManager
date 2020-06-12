package com.agh.wtm.vehiclemanager

import android.content.Intent
import android.os.Bundle
import android.provider.AlarmClock.EXTRA_MESSAGE
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import com.agh.wtm.vehiclemanager.db.VehicleContract
import com.agh.wtm.vehiclemanager.db.VehicleDBHelper
import com.agh.wtm.vehiclemanager.fragments.AddVehicleFragment
import com.agh.wtm.vehiclemanager.fragments.MainPageFragment
import com.agh.wtm.vehiclemanager.fragments.RefuellingListFragment
import com.agh.wtm.vehiclemanager.model.Vehicle
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    var dbHelper: VehicleDBHelper? = null
    var insertVehicleBtn: Button? = null
    var selectVehiclesBtn: Button? = null
    var drawerLayout: DrawerLayout? = null

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId){
            R.id.main_page -> {
                println("main page pressed")
                replaceFragment(MainPageFragment(applicationContext))
                return@OnNavigationItemSelectedListener true
            }
            R.id.refuelling_list -> {
                println("refuelling pressed")
                replaceFragment(RefuellingListFragment(applicationContext))
                return@OnNavigationItemSelectedListener true
            }
            R.id.add_vehicle -> {
                println("add vehicle pressed")
                replaceFragment(AddVehicleFragment(applicationContext))
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        dbHelper = VehicleDBHelper(this, VehicleContract.tables)
        /*insertVehicleBtn = findViewById(R.id.add_vehicle_btn)
        selectVehiclesBtn = findViewById(R.id.log_vehicles)*/
        drawerLayout = findViewById(R.id.drawer_general_layout)

        bottom_nav.selectedItemId = R.id.main_page
        bottom_nav.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
        replaceFragment(MainPageFragment(applicationContext))


        /*insertVehicleBtn!!.setOnClickListener {
            run {
                val intent = Intent(this, AddVehicleActivity::class.java)
                startActivity(intent)
//                val vehicle = Vehicle(1, "warzecha", Vehicle.VehicleType.CAR, 199998)
//                val insertedId = dbHelper!!.insert(VehicleContract.VehicleEntry, vehicle)
//                Log.d("Inserted id: ", "" + insertedId)
            }
        }

        selectVehiclesBtn!!.setOnClickListener {
            run {
                val vehicles = dbHelper!!.getAll(VehicleContract.VehicleEntry)
                vehicles.forEach {
                    Log.d("Vehicle", "Id: " + it.id + ", name: " + it.name + ", type: " + it.type + ", mileage: " + it.mileage)
                }
            }
        }*/



    }

    private fun replaceFragment(fragment: Fragment){
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragmentContainer, fragment)
        fragmentTransaction.commit()
    }

}
