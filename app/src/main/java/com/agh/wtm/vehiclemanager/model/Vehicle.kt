package com.agh.wtm.vehiclemanager.model

data class Vehicle(var id: Int, var name: String, var type: VehicleType, var mileage: Int) {

    enum class VehicleType {
        CAR, MOTORCYCLE
    }
}
