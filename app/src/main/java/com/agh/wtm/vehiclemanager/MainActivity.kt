package com.agh.wtm.vehiclemanager

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.Button
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import com.agh.wtm.vehiclemanager.adapters.VehicleAdapter
import com.agh.wtm.vehiclemanager.db.VehicleContract
import com.agh.wtm.vehiclemanager.db.VehicleDBHelper
import com.agh.wtm.vehiclemanager.fragments.MainPageFragment
import com.agh.wtm.vehiclemanager.fragments.RefuellingListFragment
import com.agh.wtm.vehiclemanager.fragments.VehicleManagerFragment
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

    private var vehicleAdapter: VehicleAdapter? = null

    lateinit var broadcastReceiver: BroadcastReceiver

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
            R.id.vehicle_manager -> {
                println("vehicle manager pressed")
                replaceFragment(VehicleManagerFragment(applicationContext))
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
        vehicleAdapter = VehicleAdapter(this, 0, getVehicles())

        if(vehicleAdapter!!.isEmpty){
            val intent = Intent(this, AddVehicleActivity::class.java)
            intent.putExtra("emptyVehicleList", true)
            startActivity(intent)
        }
        selectVehicleSpinner = findViewById(R.id.vehicle_spinner_list)
        selectVehicleSpinner!!.adapter = vehicleAdapter!!
        selectVehicleSpinner!!.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {}

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val selectedVehicle: Vehicle = parent!!.getItemAtPosition(position) as Vehicle
                setVehicle(selectedVehicle)
                refreshFragment()
            }
        }
        // set to first vehicle on init
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

        val filter = IntentFilter("com.agh.wtm.vehiclemanager.VEHICLE_DATA")

        broadcastReceiver = object : BroadcastReceiver(){
            override fun onReceive(context: Context, intent: Intent) {
                val vehicle = intent.getParcelableExtra<Vehicle>("com.agh.wtm.vehiclemanager.VEHICLE")
                addToSpinner(vehicle)
                var fragment: Fragment? = supportFragmentManager.fragments.last()
                if(fragment != null && fragment is VehicleManagerFragment) {
                    fragment.updateVehicleList()
                }
            }
        }
        registerReceiver(broadcastReceiver, filter)



    }

    override fun onDestroy() {
        unregisterReceiver(broadcastReceiver)
        super.onDestroy()
    }

    private fun replaceFragment(fragment: Fragment){
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.fragmentContainer, fragment)
        fragmentTransaction.commit()
    }

    private fun getVehicles(): List<Vehicle> {
        return dbHelper!!.getAll(Vehicles)
    }

    private fun setVehicle(vehicle: Vehicle) {
        currentVehicle = vehicle
    }

    private fun refreshFragment() {
        val currentFragment: Fragment = supportFragmentManager.findFragmentById(R.id.fragmentContainer)!!
        val fragTransaction: FragmentTransaction =   supportFragmentManager.beginTransaction()
        fragTransaction.detach(currentFragment)
        fragTransaction.attach(currentFragment)
        fragTransaction.commit()
    }

    fun addToSpinner(elem: Vehicle) {
        (selectVehicleSpinner!!.adapter as VehicleAdapter).add(elem)
    }

    fun removeFromSpinner(elem: Vehicle) {
        (selectVehicleSpinner!!.adapter as VehicleAdapter).remove(elem)
    }

    fun getCurrentVehicle(): Vehicle? {
        return currentVehicle
    }
}
