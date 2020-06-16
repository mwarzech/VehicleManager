package com.agh.wtm.vehiclemanager.model

import java.util.*

data class Fuelling(
    private val id: Int,
    val vehicleId: Int,
    val date: Date?,
    val fuelAmount: Double,
    val pricePerLitre: Double,
    val fuelType: FuelType,
    val mileage: Int
) : Entity {
    override fun getId(): Int {
        return id
    }

    enum class FuelType {
       Pb95, Pb98, ON
    }
}