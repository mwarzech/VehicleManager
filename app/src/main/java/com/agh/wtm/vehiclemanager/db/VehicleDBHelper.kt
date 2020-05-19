package com.agh.wtm.vehiclemanager.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class VehicleDBHelperHelper(context: Context, private val tables: Array<VehicleContract.Table<*>>) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
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

//    inline fun <reified T> insert(entity: T) {
//        val contract: VehicleContract.Table<T> = tables.find { it.entityReflection == entity!!::class }
//        contract.values(entity)
//    }

    companion object {
        const val DATABASE_VERSION = 1
        const val DATABASE_NAME = "Vehicle.db"
    }
}