package com.woowahan.accountbook.local.helper

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log

class DatabaseOpenHelper(
    context: Context,
    dbName: String,
    version: Int
): SQLiteOpenHelper(context, dbName, null, version) {
    init { writableDatabase }
    private lateinit var onUpgradeListener: OnUpgradeListener
    fun setOnUpgradeListener(onUpgrade: (oldVersion: Int, newVersion: Int) -> Unit) {
        onUpgradeListener = object : OnUpgradeListener {
            override fun onUpgrade(oldVersion: Int, newVersion: Int) {
                onUpgrade(oldVersion, newVersion)
            }
        }
    }
    override fun onCreate(db: SQLiteDatabase?) {}
    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        onUpgradeListener.onUpgrade(oldVersion, newVersion)
    }
}

private interface OnUpgradeListener {
    fun onUpgrade(oldVersion: Int, newVersion: Int)
}