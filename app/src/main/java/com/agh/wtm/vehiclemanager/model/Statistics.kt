package com.agh.wtm.vehiclemanager.model

data class Statistics(
    val avgConsumption: Double,
    val avgPricePerKilometer: Double,
    val summaryCost: Double,
    val minConsumption: Double,
    val maxConsumption: Double
)