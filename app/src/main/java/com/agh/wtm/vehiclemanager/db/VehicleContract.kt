package com.agh.wtm.vehiclemanager.db

import android.content.ContentValues
import android.os.Build
import android.provider.BaseColumns
import androidx.annotation.RequiresApi
import com.agh.wtm.vehiclemanager.model.Fuelling
import com.agh.wtm.vehiclemanager.model.Vehicle

object VehicleContract {

    interface Table<T> : BaseColumns {
        val tableName: String
        val sqlCreateEntries: String
        val sqlDeleteEntries: String
        val entityReflection: Class<T>
        fun values(entity: T): ContentValues
    }

    object VehicleEntry : Table<Vehicle> {
        const val COLUMN_NAME_NAME = "name"
        const val COLUMN_NAME_TYPE = "type"
        const val COLUMN_NAME_MILEAGE = "mileage"
        override val tableName = "vehicles"
        override val sqlCreateEntries =
            "CREATE TABLE $tableName (" +
                    "${BaseColumns._ID} INTEGER PRIMARY KEY," +
                    "$COLUMN_NAME_NAME TEXT," +
                    "$COLUMN_NAME_TYPE TEXT," +
                    "$COLUMN_NAME_MILEAGE INTEGER)" 

        override val sqlDeleteEntries = "DROP TABLE IF EXISTS $tableName"
        override val entityReflection: Class<Vehicle> = Vehicle::class.java
        override fun values(vehicle: Vehicle): ContentValues {
            return ContentValues().apply {
                put(COLUMN_NAME_NAME, vehicle.name)
                put(COLUMN_NAME_TYPE, vehicle.type.toString())
                put(COLUMN_NAME_MILEAGE, vehicle.mileage)
            }
        }
    }

    object FuellingEntry : Table<Fuelling> {
        const val COLUMN_NAME_VEHICLE_ID = "vehicle_id"
        const val COLUMN_NAME_DATE = "date"
        const val COLUMN_NAME_FUEL_AMOUNT = "amount"
        const val COLUMN_NAME_PRICE_PER_LITRE = "price_per_litre"
        const val COLUMN_NAME_FUEL_TYPE = "fuel_type"
        const val COLUMN_NAME_MILEAGE = "mileage"
        override val tableName = "fuellings"

        override val sqlCreateEntries =
            "CREATE TABLE $tableName (" +
                    "${BaseColumns._ID} INTEGER PRIMARY KEY," +
                    "$COLUMN_NAME_VEHICLE_ID INTEGER FOREIGN KEY," +
                    "$COLUMN_NAME_DATE INTEGER," +
                    "$COLUMN_NAME_FUEL_AMOUNT DOUBLE," +
                    "$COLUMN_NAME_PRICE_PER_LITRE DOUBLE," +
                    "$COLUMN_NAME_FUEL_TYPE TEXT," +
                    "$COLUMN_NAME_MILEAGE INTEGER)"

        override val sqlDeleteEntries = "DROP TABLE IF EXISTS ${VehicleEntry.tableName}"
        override val entityReflection: Class<Fuelling> = Fuelling::class.java
        @RequiresApi(Build.VERSION_CODES.O)
        override fun values(fuelling: Fuelling): ContentValues {
            return ContentValues().apply {
                put(COLUMN_NAME_VEHICLE_ID, fuelling.vehicleId)
                put(COLUMN_NAME_DATE, fuelling.date?.time)//?.toInstant()?.epochSecond)
                put(COLUMN_NAME_FUEL_AMOUNT, fuelling.fuelAmount)
                put(COLUMN_NAME_PRICE_PER_LITRE, fuelling.pricePerLitre)
                put(COLUMN_NAME_FUEL_TYPE, fuelling.fuelType.toString())
                put(COLUMN_NAME_MILEAGE, fuelling.mileage)
            }
        }
    }
}