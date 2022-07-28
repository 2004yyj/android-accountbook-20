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
    override fun onCreate(db: SQLiteDatabase?) {}
    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {}
}