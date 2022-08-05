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
    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(CREATE_CATEGORY)
        db.execSQL(CREATE_PAYMENT_METHOD)
        db.execSQL(CREATE_HISTORY)
    }
    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL(DROP_CATEGORY)
        db.execSQL(DROP_PAYMENT_METHOD)
        db.execSQL(DROP_HISTORY)
        onCreate(db)
    }

    companion object {
        private const val CREATE_CATEGORY = "CREATE TABLE IF NOT EXISTS Category (id INTEGER PRIMARY KEY AUTOINCREMENT, type TEXT NOT NULL, name TEXT NOT NULL UNIQUE, color TEXT NOT NULL);"
        private const val DROP_CATEGORY = "DROP TABLE IF EXISTS Category;"
        private const val CREATE_HISTORY = "CREATE TABLE IF NOT EXISTS History (id INTEGER PRIMARY KEY AUTOINCREMENT, date INTEGER NOT NULL, amount INTEGER NOT NULL, content TEXT NOT NULL, category_id INTEGER REFERENCES Category(id), payment_method_id INTEGER REFERENCES PaymentMethod(id));"
        private const val DROP_HISTORY = "DROP TABLE IF EXISTS History;"
        private const val CREATE_PAYMENT_METHOD = "CREATE TABLE IF NOT EXISTS PaymentMethod (id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT NOT NULL UNIQUE);"
        private const val DROP_PAYMENT_METHOD = "DROP TABLE IF EXISTS PaymentMethod;"
    }
}