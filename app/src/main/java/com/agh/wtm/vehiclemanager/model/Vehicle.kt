package com.agh.wtm.vehiclemanager.model

class Vehicle {

    var id: Int = 0
    var name: String? = null
    var type: VehicleType? = null
    var mileage: Int = 0

    constructor()

    constructor(id: Int, name: String?, type: VehicleType?, mileage: Int) {
        this.id = id
        this.name = name
        this.type = type
        this.mileage = mileage
    }

    enum class VehicleType {
        CAR, MOTORCYCLE
    }
}
