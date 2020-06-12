package com.agh.wtm.vehiclemanager

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import android.widget.TextView
import com.agh.wtm.vehiclemanager.db.VehicleContract
import com.agh.wtm.vehiclemanager.db.VehicleDBHelper
import com.agh.wtm.vehiclemanager.model.Fuelling
import java.util.*
import com.agh.wtm.vehiclemanager.db.VehicleContract.FuellingEntry as Fuellings

class AddFuellingActivity : AppCompatActivity() {

    private var fuellingAmountInput: TextView? = null
    private var fuellingPriceInput: TextView? = null
    private var fuelTypeInput: Spinner? = null
    private var fuellingMileageInput: TextView? = null
    private var addFuellingBtn: Button? = null
    private var dbHelper: VehicleDBHelper? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_fuelling)

        fuellingAmountInput = findViewById(R.id.fuel_amount)
        fuellingPriceInput = findViewById(R.id.fuel_price)
        fuellingMileageInput = findViewById(R.id.fuelling_mileage)
        fuelTypeInput = findViewById(R.id.fuel_type)
        addFuellingBtn = findViewById(R.id.add_fuelling_btn)
        dbHelper = VehicleDBHelper(this, VehicleContract.tables)

        fuelTypeInput!!.adapter = ArrayAdapter(this, android.R.layout.simple_selectable_list_item, Fuelling.FuelType.values())

        addFuellingBtn!!.setOnClickListener {
            run {
                addRefuelling(intent.getIntExtra("carId", 0))
            }
        }
    }

    private fun addRefuelling(carId: Int) {
        val fuellingAmount: Double = fuellingAmountInput!!.text.toString().toDouble()
        val fuellingPrice: Double = fuellingPriceInput!!.text.toString().toDouble()
        val fuellingMileage: Int = fuellingMileageInput!!.text.toString().toInt()
        val fuelType: String = fuelTypeInput!!.selectedItem.toString()

        val fuelling = Fuelling(
            0,
            carId,
            Date(),
            fuellingAmount,
            fuellingPrice,
            Fuelling.FuelType.valueOf(fuelType),
            fuellingMileage
        )

        dbHelper!!.insert(Fuellings, fuelling)
        finish()
    }
}
