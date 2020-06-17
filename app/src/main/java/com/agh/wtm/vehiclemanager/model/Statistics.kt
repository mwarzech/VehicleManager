package com.agh.wtm.vehiclemanager.model

data class Statistics(
    val summaryDistance: Double,
    val summaryConsumption: Double,
    val avgConsumption: Double,
    val avgPricePerKilometer: Double,
    val summaryCost: Double,
    val minConsumption: Double,
    val maxConsumption: Double,
    val refuellingsNumber: Int
)