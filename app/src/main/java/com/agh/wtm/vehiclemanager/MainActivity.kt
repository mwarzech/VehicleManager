package com.agh.wtm.vehiclemanager

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.graphics.BitmapFactory
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
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
import java.io.BufferedInputStream
import java.net.URL
import com.agh.wtm.vehiclemanager.db.VehicleContract.VehicleEntry as Vehicles

class MainActivity : AppCompatActivity() {

    var dbHelper: VehicleDBHelper? = null
    var drawerLayout: DrawerLayout? = null
    private var selectVehicleSpinner: Spinner? = null
    private var currentVehicle: Vehicle? = null

    private lateinit var broadcastUpdateSpinner: BroadcastReceiver

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        return@OnNavigationItemSelectedListener switchFragment(item.itemId)
    }

    fun switchFragment(fragmentId: Int): Boolean {
        when (fragmentId){
            R.id.main_page -> {
                println("main page pressed")
                replaceFragment(MainPageFragment())
                return true
            }
            R.id.refuelling_list -> {
                println("refuelling pressed")
                replaceFragment(RefuellingListFragment())
                return true
            }
            R.id.vehicle_manager -> {
                println("vehicle manager pressed")
                replaceFragment(VehicleManagerFragment())
                return true
            }
        }

        return false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        dbHelper = VehicleDBHelper(this, VehicleContract.tables)
        drawerLayout = findViewById(R.id.drawer_general_layout)
        selectVehicleSpinner = findViewById(R.id.vehicle_spinner_list)

        createNewVehicleAdapter()

        selectVehicleSpinner!!.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {}

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val selectedVehicle: Vehicle = parent!!.getItemAtPosition(position) as Vehicle
                setVehicle(selectedVehicle)
                refreshFragment()
            }
        }

        bottom_nav.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
        Log.d("lastWarzecha", " " + lastNonConfigurationInstance)
        if ( (lastCustomNonConfigurationInstance as? Int) != null ) {
            bottom_nav.selectedItemId = (lastCustomNonConfigurationInstance as Int)
        } else {
            bottom_nav.selectedItemId = R.id.main_page
        }
        switchFragment(bottom_nav.selectedItemId)

        val updateSpinnerFilter = IntentFilter("com.agh.wtm.vehiclemanager.UPDATE_SPINNER")
        broadcastUpdateSpinner = object : BroadcastReceiver(){
            override fun onReceive(context: Context, intent: Intent) {
                updateSpinner()
                var fragment: Fragment? = supportFragmentManager.fragments.last()
                if(fragment != null && fragment is VehicleManagerFragment) {
                    fragment.updateVehicleList()
                }
            }
        }
        registerReceiver(broadcastUpdateSpinner, updateSpinnerFilter)
    }

    override fun onResume() {
        super.onResume()
        refreshFragment()
    }

    private fun createNewVehicleAdapter() {
        val vehicleAdapter = VehicleAdapter(this, 0, getVehicles())

        if(vehicleAdapter.isEmpty){
            val intent = Intent(this, AddVehicleActivity::class.java)
            intent.putExtra("emptyVehicleList", true)
            startActivity(intent)
        }
        selectVehicleSpinner!!.adapter = vehicleAdapter
    }

    override fun onDestroy() {
        unregisterReceiver(broadcastUpdateSpinner)
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
        val fragTransaction: FragmentTransaction = supportFragmentManager.beginTransaction()
        fragTransaction.detach(currentFragment)
        fragTransaction.attach(currentFragment)
        fragTransaction.commit()
    }

    fun updateSpinner() {
        val selectedVehicle : Vehicle? = currentVehicle
        createNewVehicleAdapter()
        if(null != currentVehicle) {
            selectVehicleSpinner!!.setSelection(getSpinnerVehicleIndex(selectedVehicle!!))
        }
    }

    private fun getSpinnerVehicleIndex(vehicle: Vehicle): Int {
        for (i in 0 until selectVehicleSpinner!!.count) {
            if ((selectVehicleSpinner!!.getItemAtPosition(i) as Vehicle).getId() == vehicle.getId()) {
                return i
            }
        }
        return 0
    }

    fun getCurrentVehicle(): Vehicle? {
        return currentVehicle
    }

    override fun onRetainCustomNonConfigurationInstance(): Any {
        return bottom_nav.selectedItemId
    }
}
