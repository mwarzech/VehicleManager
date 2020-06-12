package com.agh.wtm.vehiclemanager.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

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

    fun <T> getAll(contract: VehicleContract.Table<T>): List<T> {
        val c = writableDatabase.query(contract.tableName, null, null, null, null, null, null)
        return generateSequence { if (c.moveToNext()) c else null }
            .map { contract.fromCursor(it) }
            .toList()
    }

    fun <T> getById(contract: VehicleContract.Table<T>, id: Int): T {
        val c = writableDatabase.query(contract.tableName, null, "_id=?", arrayOf(id.toString()), null, null, null)
        return generateSequence { if (c.moveToNext()) c else null }
            .map { contract.fromCursor(it) }
            .toList()[0]
    }

    companion object {
        const val DATABASE_VERSION = 1
        const val DATABASE_NAME = "Vehicle.db"
    }
}