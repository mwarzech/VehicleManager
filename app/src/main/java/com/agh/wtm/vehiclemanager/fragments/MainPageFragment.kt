package com.agh.wtm.vehiclemanager.fragments


import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.agh.wtm.vehiclemanager.MainActivity
import com.agh.wtm.vehiclemanager.R
import com.agh.wtm.vehiclemanager.logic.RefuellingStatistics
import com.agh.wtm.vehiclemanager.model.Vehicle

class MainPageFragment constructor(private val mCtx: Context) : Fragment() {

    private var vehicleNameText: TextView? = null
    private var vehicleMileageText: TextView? = null

    private var currentVehicle: Vehicle? = null

    private var vehicleSummaryDistanceM: TextView? = null
    private var vehicleSummaryConsumptionM: TextView? = null
    private var vehicleSummaryCostM: TextView? = null
    private var vehicleFuellingsNumberM: TextView? = null
    private var avgConsumptionM: TextView? = null
    private var avgPricePerKmM: TextView? = null
    private var worstConsumptionM: TextView? = null
    private var bestConsumptionM: TextView? = null

    private var vehicleSummaryDistanceA: TextView? = null
    private var vehicleSummaryConsumptionA: TextView? = null
    private var vehicleSummaryCostA: TextView? = null
    private var vehicleFuellingsNumberA: TextView? = null
    private var avgConsumptionA: TextView? = null
    private var avgPricePerKmA: TextView? = null
    private var worstConsumptionA: TextView? = null
    private var bestConsumptionA: TextView? = null



    private var refuellingStatistics: RefuellingStatistics? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view: View = inflater.inflate(R.layout.fragment_main_page, container, false)

        vehicleNameText = view.findViewById(R.id.vehicle_name)
        vehicleMileageText = view.findViewById(R.id.vehicle_mileage)

        vehicleSummaryDistanceM = view.findViewById(R.id.vehicle_distance)
        vehicleSummaryConsumptionM = view.findViewById(R.id.vehicle_consumption)
        vehicleSummaryCostM = view.findViewById(R.id.vehicle_cost)
        vehicleFuellingsNumberM = view.findViewById(R.id.vehicle_fuellings_number)
        avgConsumptionM = view.findViewById(R.id.avg_consumption)
        avgPricePerKmM = view.findViewById(R.id.avg_price_perKm)
        worstConsumptionM = view.findViewById(R.id.worst_consumption)
        bestConsumptionM = view.findViewById(R.id.best_consumption)

        vehicleSummaryDistanceA = view.findViewById(R.id.vehicle_distance_all)
        vehicleSummaryConsumptionA = view.findViewById(R.id.vehicle_consumption_all)
        vehicleSummaryCostA = view.findViewById(R.id.vehicle_cost_all)
        vehicleFuellingsNumberA = view.findViewById(R.id.vehicle_all_fuellings_number)
        avgConsumptionA = view.findViewById(R.id.avg_consumption_all)
        avgPricePerKmA = view.findViewById(R.id.avg_price_perKm_all)
        worstConsumptionA = view.findViewById(R.id.worst_consumption_all)
        bestConsumptionA = view.findViewById(R.id.best_consumption_all)

        val activity: MainActivity? = activity as MainActivity?
        currentVehicle = activity!!.getCurrentVehicle()
        refuellingStatistics = RefuellingStatistics(activity!!.dbHelper!!)

        displayVehicleData()

        return view
    }

    private fun displayVehicleData() {

        if (currentVehicle != null) {
            val wholePeriodStat = refuellingStatistics!!.getStatisticsForWholePeriod(currentVehicle!!.getId())
            val currentMonthStat = refuellingStatistics!!.getStatisticsForCurrentMonth(currentVehicle!!.getId())
            /*General info*/
            vehicleNameText!!.text = currentVehicle!!.name
            vehicleMileageText!!.text = String.format("%d km",currentVehicle!!.mileage)
            /*Stat for current month*/
            vehicleSummaryDistanceM!!.text = String.format("%.0f km", currentMonthStat.summaryDistance)
            vehicleSummaryConsumptionM!!.text = String.format("%.2f l", currentMonthStat.summaryConsumption)
            vehicleSummaryCostM!!.text = String.format("%.2f zł", currentMonthStat.summaryCost)
            vehicleFuellingsNumberM!!.text = currentMonthStat.refuellingsNumber.toString()
            avgConsumptionM!!.text = String.format("%.2f l/100km", currentMonthStat.avgConsumption)
            avgPricePerKmM!!.text = String.format("%.4f zł/km", currentMonthStat.avgPricePerKilometer)
            worstConsumptionM!!.text = String.format("%.2f l/100km", currentMonthStat.maxConsumption)
            bestConsumptionM!!.text = String.format("%.2f l/100km", currentMonthStat.minConsumption)
            /*Stat for whole period*/
            vehicleSummaryDistanceA!!.text = String.format("%.0f km", wholePeriodStat.summaryDistance)
            vehicleSummaryConsumptionA!!.text = String.format("%.2f l", wholePeriodStat.summaryConsumption)
            vehicleSummaryCostA!!.text = String.format("%.2f zł", wholePeriodStat.summaryCost)
            vehicleFuellingsNumberA!!.text = wholePeriodStat.refuellingsNumber.toString()
            avgConsumptionA!!.text = String.format("%.2f l/100km", wholePeriodStat.avgConsumption)
            avgPricePerKmA!!.text = String.format("%.4f zł/km", wholePeriodStat.avgPricePerKilometer)
            worstConsumptionA!!.text = String.format("%.2f l/100km", wholePeriodStat.maxConsumption)
            bestConsumptionA!!.text = String.format("%.2f l/100km", wholePeriodStat.minConsumption)

        }
    }

}
