package com.agh.wtm.vehiclemanager.db

import android.content.ContentValues
import android.database.Cursor
import android.provider.BaseColumns
import com.agh.wtm.vehiclemanager.model.Fuelling
import com.agh.wtm.vehiclemanager.model.Vehicle
import java.util.*

object VehicleContract {

    interface Table<T> : BaseColumns {
        val tableName: String
        val sqlCreateEntries: String
        val sqlDeleteEntries: String
        fun values(entity: T): ContentValues
        fun fromCursor(cursor: Cursor): T
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
        override fun values(vehicle: Vehicle): ContentValues {
            return ContentValues().apply {
                put(COLUMN_NAME_NAME, vehicle.name)
                put(COLUMN_NAME_TYPE, vehicle.type.toString())
                put(COLUMN_NAME_MILEAGE, vehicle.mileage)
            }
        }
        override fun fromCursor(cursor: Cursor): Vehicle {
            return Vehicle(
                cursor.getInt(0),
                cursor.getString(1),
                Vehicle.VehicleType.valueOf(cursor.getString(2)),
                cursor.getInt(3)
            )
        }
    }

    object FuellingEntry : Table<Fuelling> {
        const val COLUMN_NAME_VEHICLE_ID = "vehicle_id"
        const val COLUMN_NAME_DATE = "date"
        const val COLUMN_NAME_FUEL_AMOUNT = "amount"
        const val COLUMN_NAME_PRICE_PER_LITRE = "price_per_litre"
        const val COLUMN_NAME_FUEL_TYPE = "fuel_type"
        const val COLUMN_NAME_LAST_FUELLING_MILEAGE = "last_fuelling_mileage"
        const val COLUMN_NAME_MILEAGE = "mileage"
        override val tableName = "fuellings"

        override val sqlCreateEntries =
            "CREATE TABLE $tableName (" +
                    "${BaseColumns._ID} INTEGER PRIMARY KEY," +
                    "$COLUMN_NAME_VEHICLE_ID INTEGER," +
                    "$COLUMN_NAME_DATE INTEGER," +
                    "$COLUMN_NAME_FUEL_AMOUNT DOUBLE," +
                    "$COLUMN_NAME_PRICE_PER_LITRE DOUBLE," +
                    "$COLUMN_NAME_FUEL_TYPE TEXT," +
                    "$COLUMN_NAME_LAST_FUELLING_MILEAGE INTEGER," +
                    "$COLUMN_NAME_MILEAGE INTEGER," +
                    " FOREIGN KEY(vehicle_id) REFERENCES vehicle(vehicle_id))"

        override val sqlDeleteEntries = "DROP TABLE IF EXISTS ${VehicleEntry.tableName}"
        override fun values(fuelling: Fuelling): ContentValues {
            return ContentValues().apply {
                put(COLUMN_NAME_VEHICLE_ID, fuelling.vehicleId)
                put(COLUMN_NAME_DATE, fuelling.date.time)
                put(COLUMN_NAME_FUEL_AMOUNT, fuelling.fuelAmount)
                put(COLUMN_NAME_PRICE_PER_LITRE, fuelling.pricePerLitre)
                put(COLUMN_NAME_FUEL_TYPE, fuelling.fuelType.toString())
                put(COLUMN_NAME_LAST_FUELLING_MILEAGE, fuelling.lastFuellingMileage)
                put(COLUMN_NAME_MILEAGE, fuelling.mileage)
            }
        }

        override fun fromCursor(cursor: Cursor): Fuelling {
            return Fuelling(
                cursor.getInt(0),
                cursor.getInt(1),
                Date(cursor.getLong(2)),
                cursor.getDouble(3),
                cursor.getDouble(4),
                Fuelling.FuelType.valueOf(cursor.getString(5)),
                cursor.getInt(6),
                cursor.getInt(7)
            )
        }
    }

    val tables: Array<Table<*>> = arrayOf(VehicleEntry, FuellingEntry)
}