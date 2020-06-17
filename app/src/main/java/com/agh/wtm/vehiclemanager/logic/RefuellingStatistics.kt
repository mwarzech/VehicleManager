package com.agh.wtm.vehiclemanager.logic

import com.agh.wtm.vehiclemanager.db.VehicleDBHelper
import com.agh.wtm.vehiclemanager.model.Fuelling
import com.agh.wtm.vehiclemanager.model.Statistics
import org.joda.time.DateTime

data class RefuellingStatistics(val dbHelper: VehicleDBHelper){

    private fun getCurrentMonthRefuellings(vehicleId: Int): List<Fuelling> {
        val allRefuellings: List<Fuelling> = getAllRefuellings(vehicleId)
        val monthBefore = DateTime().minusMonths(1).toDate()
        return allRefuellings.filter { fuelling -> fuelling.date.after(monthBefore) }
    }

    private fun getAllRefuellings(vehicleId: Int): List<Fuelling> = dbHelper.getFuellingsForVehicle(vehicleId)

    private fun getStatistics(refuellings: List<Fuelling>): Statistics {
        var summaryConsumption = 0.0
        var summaryDistance = 0.0
        var summaryCost = 0.0
        var minConsumption = 0.0
        var maxConsumption = 0.0
        val refuellingsNumber = refuellings.size

        refuellings.forEach {
            summaryConsumption += it.fuelAmount
            summaryDistance += it.mileage - it.lastFuellingMileage
            summaryCost += it.pricePerLitre * it.fuelAmount

            val consumption = it.fuelAmount / (it.mileage - it.lastFuellingMileage) * 100
            if ( consumption > maxConsumption) maxConsumption = consumption
            if ( minConsumption == 0.0 || minConsumption > consumption) minConsumption = consumption
        }

        return Statistics(
            summaryDistance = summaryDistance,
            summaryConsumption = summaryConsumption,
            avgConsumption = summaryConsumption / summaryDistance  * 100,
            avgPricePerKilometer = summaryCost / summaryDistance,
            summaryCost = summaryCost,
            minConsumption = minConsumption,
            maxConsumption = maxConsumption,
            refuellingsNumber = refuellingsNumber
        )
    }

    fun getStatisticsForCurrentMonth(vehicleId: Int) = getStatistics(getCurrentMonthRefuellings(vehicleId))
    fun getStatisticsForWholePeriod(vehicleId: Int) = getStatistics(getAllRefuellings(vehicleId))
}