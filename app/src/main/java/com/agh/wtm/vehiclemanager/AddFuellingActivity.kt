package com.agh.wtm.vehiclemanager

import android.content.Intent
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.agh.wtm.vehiclemanager.db.VehicleContract
import com.agh.wtm.vehiclemanager.db.VehicleDBHelper
import com.agh.wtm.vehiclemanager.model.Fuelling
import com.agh.wtm.vehiclemanager.model.Vehicle
import kotlinx.android.synthetic.main.activity_add_fuelling.*
import java.util.*
import com.agh.wtm.vehiclemanager.db.VehicleContract.FuellingEntry as Fuellings
import com.agh.wtm.vehiclemanager.db.VehicleContract.VehicleEntry as Vehicles

class AddFuellingActivity : AppCompatActivity() {

    private var fuellingAmountInput: TextView? = null
    private var fuellingPriceInput: TextView? = null
    private var fuelTypeInput: Spinner? = null
    private var fuellingMileageInput: TextView? = null
    private var addFuellingBtn: Button? = null
    private var returnBtn: Button? = null
    private var dbHelper: VehicleDBHelper? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_fuelling)

        fuellingAmountInput = findViewById(R.id.fuel_amount)
        fuellingPriceInput = findViewById(R.id.fuel_price)
        fuellingMileageInput = findViewById(R.id.fuelling_mileage)
        fuelTypeInput = findViewById(R.id.fuel_type)
        addFuellingBtn = findViewById(R.id.add_fuelling_btn)
        returnBtn = findViewById(R.id.btnBackFuelling)
        dbHelper = VehicleDBHelper(this, VehicleContract.tables)

        val vehicleId = intent.getIntExtra("carId", 0)
        val vehicle = dbHelper!!.getById(Vehicles, vehicleId)
        recent_mileage.text = String.format("Vehicle mileage: %d", vehicle!!.mileage)

        fuelTypeInput!!.adapter = ArrayAdapter(this, android.R.layout.simple_selectable_list_item, Fuelling.FuelType.values())

        addFuellingBtn!!.setOnClickListener {
            run {
                if (fuellingAmountInput!!.text.toString().isEmpty() ||
                        fuellingPriceInput!!.text.toString().isEmpty() ||
                        fuellingMileageInput!!.text.toString().isEmpty()){
                    Toast.makeText(this, "Please fill all fields", Toast.LENGTH_LONG).show()
                    return@run
                }

                if (fuellingMileageInput!!.text.toString().toInt() < vehicle.mileage){
                    Toast.makeText(this, "Fuelling mileage should be greater than vehicle mileage", Toast.LENGTH_LONG).show()
                    return@run
                }

                addRefuelling(vehicle)
                dbHelper!!.update(Vehicles, vehicle.copy(mileage = fuellingMileageInput!!.text.toString().toInt()))
                val intent = Intent("com.agh.wtm.vehiclemanager.UPDATE_SPINNER")
                sendBroadcast(intent)
                finish()
            }
        }

        returnBtn!!.setOnClickListener{
            finish()
        }
    }



    private fun addRefuelling(vehicle: Vehicle) {
        val fuellingAmount: Double = fuellingAmountInput!!.text.toString().toDouble()
        val fuellingPrice: Double = fuellingPriceInput!!.text.toString().toDouble()
        val fuellingMileage: Int = fuellingMileageInput!!.text.toString().toInt()
        val fuelType: String = fuelTypeInput!!.selectedItem.toString()

        val fuelling = Fuelling(
            vehicleId = vehicle.getId(),
            fuelAmount = fuellingAmount,
            pricePerLitre = fuellingPrice,
            fuelType = Fuelling.FuelType.valueOf(fuelType),
            lastFuellingMileage = vehicle.mileage,
            mileage = fuellingMileage
        )

        dbHelper!!.insert(Fuellings, fuelling)
    }
}
