package com.agh.wtm.vehiclemanager.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import androidx.core.content.contentValuesOf
import com.agh.wtm.vehiclemanager.model.Entity
import com.agh.wtm.vehiclemanager.model.Fuelling

class VehicleDBHelper(context: Context, private val tables: Array<VehicleContract.Table<*>>) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    override fun onCreate(db: SQLiteDatabase) {
        tables.forEach {
            db.execSQL(it.sqlCreateEntries)
        }
    }
    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        tables.forEach {
            db.execSQL(it.sqlDeleteEntries)
        }
        onCreate(db)
    }
    override fun onDowngrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        onUpgrade(db, oldVersion, newVersion)
    }

    fun <T> insert(contract: VehicleContract.Table<T>, entity: T): Long {
        val entityValue = contract.values(entity)
        return writableDatabase.insert(contract.tableName, null, entityValue)
    }

    fun <T: Entity> update(contract: VehicleContract.Table<T>, entity: T): Int {
        val entityValue = contract.values(entity)
        return writableDatabase.update(contract.tableName, entityValue, "_id=?", arrayOf(entity.getId().toString()))
    }

    fun <T> getAll(contract: VehicleContract.Table<T>): List<T> {
        val c = writableDatabase.query(contract.tableName, null, null, null, null, null, null)
        return generateSequence { if (c.moveToNext()) c else null }
            .map { contract.fromCursor(it) }
            .toList()
    }

    fun <T> getById(contract: VehicleContract.Table<T>, id: Int): T? {
        val c = writableDatabase.query(contract.tableName, null, "_id=?", arrayOf(id.toString()), null, null, null)
        return generateSequence { if (c.moveToNext()) c else null }
            .map { contract.fromCursor(it) }
            .toList().getOrNull(0)
    }

    fun getFuellingsForVehicle(vehicleId: Int): List<Fuelling> {
        val fuellings = VehicleContract.FuellingEntry
        val c = writableDatabase.query(fuellings.tableName, null, "vehicle_id=?", arrayOf(vehicleId.toString()), null, null, null)
        return generateSequence { if (c.moveToNext()) c else null }
            .map { fuellings.fromCursor(it) }
            .toList()
    }

    fun deleteFuellingsForVehicle(vehicleId: Int): Boolean {
        val fuellings = VehicleContract.FuellingEntry
        return writableDatabase.delete(fuellings.tableName, "vehicle_id=?",arrayOf(vehicleId.toString())) > 0
    }

    fun <T> deleteById(contract: VehicleContract.Table<T>, id: Int): Boolean {
        return writableDatabase.delete(contract.tableName, "_id=?", arrayOf(id.toString())) > 0
    }

    companion object {
        const val DATABASE_VERSION = 1
        const val DATABASE_NAME = "Vehicle.db"
    }
}