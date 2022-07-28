package com.woowahan.accountbook.local.dao

import com.woowahan.accountbook.data.entity.PaymentMethodData
import com.woowahan.accountbook.data.local.dao.PaymentMethodDao
import com.woowahan.accountbook.local.helper.DatabaseOpenHelper
import com.woowahan.accountbook.local.util.runSQL
import com.woowahan.accountbook.local.util.runSQLWithReadableTransaction
import com.woowahan.accountbook.local.util.runSQLWithWritableTransaction
import javax.inject.Inject

class PaymentMethodDaoImpl @Inject constructor(
    private val dbHelper: DatabaseOpenHelper
): PaymentMethodDao {
    override suspend fun getAllPaymentMethods(): List<PaymentMethodData>  {
        val sql = "SELECT * FROM PaymentMethod"
        return dbHelper.runSQLWithReadableTransaction<List<PaymentMethodData>> {
            val cursor = rawQuery(sql, arrayOf())
            val list = mutableListOf<PaymentMethodData>()
            while (cursor.moveToNext()) {
                list.add(
                    PaymentMethodData(
                        cursor.getInt(0),
                        cursor.getString(1),
                    )
                )
            }
            cursor.close()
            return@runSQLWithReadableTransaction list
        }
    }

    override suspend fun getPaymentMethodByName(name: String): PaymentMethodData {
        val sql = "SELECT * FROM PaymentMethod WHERE name = ?"
        return dbHelper.runSQLWithReadableTransaction {
            val cursor = rawQuery(sql, arrayOf(name))
            val paymentMethodData = PaymentMethodData(
                cursor.getInt(0),
                cursor.getString(1),
            )
            cursor.close()
            return@runSQLWithReadableTransaction paymentMethodData
        }
    }

    override suspend fun createPaymentMethodTable() {
        val sql = "CREATE TABLE IF NOT EXISTS PaymentMethod (id INTEGER PRIMARY KEY AUTOINCREMENT, name TEXT NOT NULL UNIQUE);"
        dbHelper.runSQL {
            val statement = compileStatement(sql)
            statement.execute()
        }
    }

    override suspend fun dropPaymentMethodTable() {
        val sql = "DROP TABLE PaymentMethod;"
        dbHelper.runSQL {
            val statement = compileStatement(sql)
            statement.execute()
        }
    }

    override suspend fun insertPaymentMethod(name: String) {
        val sql = "INSERT INTO PaymentMethod (name) VALUES (?)"
        dbHelper.runSQLWithWritableTransaction {
            val statement = compileStatement(sql)
            statement.bindString(0, name)
            statement.executeInsert()
        }
    }

    override suspend fun updatePaymentMethod(id: Int, name: String) {
        val sql = "UPDATE PaymentMethod SET name = ? WHERE id = $id"
        dbHelper.runSQLWithWritableTransaction {
            val statement = compileStatement(sql)
            statement.bindString(0, name)
            statement.executeUpdateDelete()
        }
    }

    override suspend fun deletePaymentMethod(id: Int) {
        val sql = "DELETE FROM PaymentMethod WHERE id = $id"
        dbHelper.runSQLWithWritableTransaction {
            val statement = compileStatement(sql)
            statement.executeUpdateDelete()
        }
    }
}