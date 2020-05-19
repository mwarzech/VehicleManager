package com.agh.wtm.vehiclemanager.model

import java.util.*

class Fuelling {
    var id: Int = 0
    var vehicleId: Int = 0
    var date: Date? = null
    var fuelAmount: Double = 0.0
    var pricePerLitre: Double = 0.0
    var fuelType: FuelType? = null
    var mileage: Int = 0

    constructor(){}

    constructor(
        id: Int,
        vehicleId: Int,
        date: Date?,
        fuelAmount: Double,
        pricePerLitre: Double,
        fuelType: FuelType?,
        mileage: Int
    ) {
        this.id = id
        this.vehicleId = vehicleId
        this.date = date
        this.fuelAmount = fuelAmount
        this.pricePerLitre = pricePerLitre
        this.fuelType = fuelType
        this.mileage = mileage
    }


    enum class FuelType {
        ON, GASOLINE
    }
}