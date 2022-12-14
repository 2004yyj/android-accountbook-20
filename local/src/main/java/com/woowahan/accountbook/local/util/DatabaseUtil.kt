package com.woowahan.accountbook.local.util

import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import java.sql.SQLException

fun SQLiteOpenHelper.runSQL(function: SQLiteDatabase.() -> Unit) {
    try {
        function.invoke(writableDatabase)
    } catch (e: SQLException) {
        throw e
    }
}

fun SQLiteOpenHelper.runSQLWithWritableTransaction(function: SQLiteDatabase.() -> Unit) {
    val db = writableDatabase
    try {
        db.beginTransaction()
        function.invoke(writableDatabase)
        db.setTransactionSuccessful()
    } catch (e: SQLException) {
        throw e
    } finally {
        db.endTransaction()
    }
}

fun <T> SQLiteOpenHelper.runSQLWithReadableTransaction(function: SQLiteDatabase.() -> T): T {
    val db = readableDatabase
    try {
        db.beginTransaction()
        return function.invoke(writableDatabase)
    } catch (e: SQLException) {
        throw e
    } finally {
        db.endTransaction()
    }
}

fun whereInSQLQueryIdList(idList: List<Int>): String {
    var query = String()
    idList.forEachIndexed { index, item ->
        query += "${if(index == 0) "(" else ""}$item${if(index != idList.size - 1) ", " else ")"}"
    }
    return query
}