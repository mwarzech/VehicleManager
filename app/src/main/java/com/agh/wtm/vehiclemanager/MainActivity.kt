package com.agh.wtm.vehiclemanager

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.agh.wtm.vehiclemanager.db.VehicleContract
import com.agh.wtm.vehiclemanager.db.VehicleDBHelper
import com.agh.wtm.vehiclemanager.fragments.AddVehicleFragment
import com.agh.wtm.vehiclemanager.fragments.MainPageFragment
import com.agh.wtm.vehiclemanager.fragments.RefuellingListFragment
import com.agh.wtm.vehiclemanager.model.Vehicle
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_main.*
import com.agh.wtm.vehiclemanager.db.VehicleContract.VehicleEntry as Vehicles

class MainActivity : AppCompatActivity() {

    var dbHelper: VehicleDBHelper? = null
    var logVehiclesBtn: Button? = null
    var drawerLayout: DrawerLayout? = null
    private var selectVehicleSpinner: Spinner? = null
    private var currentVehicle: Vehicle? = null

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
//        logVehiclesBtn = findViewById(R.id.log_vehicles)
        drawerLayout = findViewById(R.id.drawer_general_layout)
        selectVehicleSpinner = findViewById(R.id.vehicle_spinner_list)
        selectVehicleSpinner!!.adapter = ArrayAdapter(this, android.R.layout.simple_selectable_list_item, getVehiclesNames())
        selectVehicleSpinner!!.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {}

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                setVehicle(position + 1)
                refreshFragment()
            }
        }
        // set to first vehicle on init
        //setVehicle(1)
        bottom_nav.selectedItemId = R.id.main_page
        bottom_nav.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
        replaceFragment(MainPageFragment(applicationContext))
//        logVehiclesBtn!!.setOnClickListener {
//            run {
//                val vehicles = dbHelper!!.getAll(VehicleContract.VehicleEntry)
//                vehicles.forEach {
//                    Log.d("Vehicle", "Id: " + it.id + ", name: " + it.name + ", type: " + it.type + ", mileage: " + it.mileage)
//                }
//            }
//        }


    }

    private fun replaceFragment(fragment: Fragment){
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragmentContainer, fragment)
        fragmentTransaction.commit()
    }

    private fun getVehiclesNames(): List<String> {
        return dbHelper!!.getAll(Vehicles).map { v -> v.name }
    }

    private fun setVehicle(id: Int) {
        currentVehicle = dbHelper!!.getById(Vehicles, id)
    }

    private fun refreshFragment() {
        val currentFragment: Fragment = supportFragmentManager.findFragmentById(R.id.fragmentContainer)!!
        val fragTransaction: FragmentTransaction =   supportFragmentManager.beginTransaction()
        fragTransaction.detach(currentFragment);
        fragTransaction.attach(currentFragment);
        fragTransaction.commit()
    }

    fun addToSpinner(elem: String) {
        (selectVehicleSpinner!!.adapter as ArrayAdapter<String>).add(elem)
    }

    fun getCurrentVehicle(): Vehicle {
        return currentVehicle!!
    }
}
