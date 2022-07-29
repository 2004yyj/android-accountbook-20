package com.woowahan.accountbook.local.dao

import com.woowahan.accountbook.data.entity.CategoryData
import com.woowahan.accountbook.data.entity.HistoryData
import com.woowahan.accountbook.data.entity.PaymentMethodData
import com.woowahan.accountbook.data.local.HistoryDao
import com.woowahan.accountbook.local.helper.DatabaseOpenHelper
import com.woowahan.accountbook.local.util.runSQL
import com.woowahan.accountbook.local.util.runSQLWithReadableTransaction
import com.woowahan.accountbook.local.util.runSQLWithWritableTransaction
import javax.inject.Inject

class HistoryDaoImpl @Inject constructor(
    private val dbHelper: DatabaseOpenHelper
): HistoryDao {
    override suspend fun getTotalPayByMonthAndType(firstDayOfMonth: Long, firstDayOfNextMonth: Long, type: String): Long {
        val sql =
            "SELECT History.*, Category.*, PaymentMethod.* FROM History " +
                    "INNER JOIN Category " +
                    "ON History.category_id = Category.id " +
                    "LEFT OUTER JOIN PaymentMethod " +
                    "ON IFNULL(History.payment_method_id, -1) = PaymentMethod.id " +
                    "WHERE History.date >= ? AND History.date < ? AND History.amount ${if (type == "income") ">" else "<"} 0"

        return dbHelper.runSQLWithReadableTransaction {
            var totalPay = 0L
            val cursor = rawQuery(sql, arrayOf(firstDayOfMonth.toString(), firstDayOfNextMonth.toString()))
            while(cursor.moveToNext()) {
                if (cursor.getString(7) == type) {
                    totalPay +=
                        if (cursor.getString(7) == "income")
                            cursor.getLong(2)
                        else cursor.getLong(2) * -1
                }
            }
            cursor.close()
            totalPay
        }
    }

    override suspend fun getAllHistoriesByMonthAndType(firstDayOfMonth: Long, firstDayOfNextMonth: Long): List<HistoryData> {
        val sql =
            "SELECT History.*, Category.*, PaymentMethod.* FROM History " +
                    "INNER JOIN Category " +
                    "ON History.category_id = Category.id " +
                    "LEFT OUTER JOIN PaymentMethod " +
                    "ON IFNULL(History.payment_method_id, -1) = PaymentMethod.id " +
                    "WHERE History.date >= ? AND History.date < ?"
        return dbHelper.runSQLWithReadableTransaction {
            val cursor = rawQuery(sql, arrayOf(firstDayOfMonth.toString(), firstDayOfNextMonth.toString()))
            val list = mutableListOf<HistoryData>()
            while(cursor.moveToNext()) {
                list.add(
                    HistoryData(
                        cursor.getInt(0),
                        cursor.getLong(1),
                        cursor.getLong(2),
                        cursor.getString(3),
                        CategoryData(
                            cursor.getInt(6),
                            cursor.getString(7),
                            cursor.getString(8),
                            cursor.getString(9).toULong()
                        ),
                        if (cursor.getString(7) == "income") null
                        else PaymentMethodData(
                            cursor.getInt(10),
                            cursor.getString(11),
                        )
                    )
                )
            }
            cursor.close()
            list
        }
    }

    override suspend fun getAllHistoriesByMonthAndType(
        firstDayOfMonth: Long,
        firstDayOfNextMonth: Long,
        type: String
    ): List<HistoryData> {
        val sql =
            "SELECT History.*, Category.*, PaymentMethod.* FROM History " +
                    "INNER JOIN Category " +
                    "ON History.category_id = Category.id " +
                    "LEFT OUTER JOIN PaymentMethod " +
                    "ON IFNULL(History.payment_method_id, -1) = PaymentMethod.id " +
                    "WHERE History.date >= ? AND History.date < ? AND History.amount ${if (type == "income") ">" else "<"} 0"
        return dbHelper.runSQLWithReadableTransaction {
            val cursor = rawQuery(sql, arrayOf(firstDayOfMonth.toString(), firstDayOfNextMonth.toString()))
            val list = mutableListOf<HistoryData>()
            while(cursor.moveToNext()) {
                list.add(
                    HistoryData(
                        cursor.getInt(0),
                        cursor.getLong(1),
                        cursor.getLong(2),
                        cursor.getString(3),
                        CategoryData(
                            cursor.getInt(6),
                            cursor.getString(7),
                            cursor.getString(8),
                            cursor.getString(9).toULong()
                        ),
                        if (cursor.getString(7) == "income") null
                        else PaymentMethodData(
                            cursor.getInt(10),
                            cursor.getString(11),
                        )
                    )
                )
            }
            cursor.close()
            list
        }
    }

    override suspend fun createHistoryTable() {
        val sql =
            "CREATE TABLE IF NOT EXISTS History (" +
                "id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "date INTEGER NOT NULL, " +
                "amount INTEGER NOT NULL, " +
                "content TEXT NOT NULL, " +
                "category_id INTEGER REFERENCES Category(id), " +
                "payment_method_id INTEGER REFERENCES PaymentMethod(id)" +
            ");"
        dbHelper.runSQL {
            val statement = compileStatement(sql)
            statement.execute()
        }
    }

    override suspend fun dropHistoryTable() {
        val sql = "DROP TABLE History;"
        dbHelper.runSQL {
            val statement = compileStatement(sql)
            statement.execute()
        }
    }

    override suspend fun insertHistory(
        date: Long,
        amount: Long,
        content: String,
        category: CategoryData,
        paymentMethod: PaymentMethodData?
    ) {
        val sql = "INSERT INTO History (date, amount, content, category_id, payment_method_id) VALUES (?, ?, ?, ?, ${if (paymentMethod != null) "?" else "NULL"})"
        dbHelper.runSQLWithWritableTransaction {
            val statement = compileStatement(sql)
            statement.bindLong(1, date)
            statement.bindLong(2, amount.toLong())
            statement.bindString(3, content)
            statement.bindLong(4, category.id.toLong())
            paymentMethod?.id?.toLong()?.let { statement.bindLong(5, it) }
            statement.executeInsert()
        }
    }

    override suspend fun updateHistory(
        id: Int,
        date: Long,
        amount: Long,
        content: String,
        category: CategoryData,
        paymentMethod: PaymentMethodData?
    ) {
        val sql = "UPDATE History SET date = ?, amount = ?, content = ?, category_id = ?, payment_method_id = ${if (paymentMethod != null) "?" else "NULL"})"
        dbHelper.runSQLWithWritableTransaction {
            val statement = compileStatement(sql)
            statement.bindLong(1, date)
            statement.bindLong(2, amount.toLong())
            statement.bindString(3, content)
            statement.bindLong(4, category.id.toLong())
            paymentMethod?.id?.toLong()?.let { statement.bindLong(5, it) }
            statement.executeUpdateDelete()
        }
    }

    override suspend fun deleteHistory(id: Int) {
        val sql = "DELETE FROM History WHERE id = $id"
        dbHelper.runSQLWithWritableTransaction {
            val statement = compileStatement(sql)
            statement.executeUpdateDelete()
        }
    }
}