package com.woowahan.accountbook.local.dao

import com.woowahan.accountbook.data.entity.CategoryData
import com.woowahan.accountbook.data.entity.HistoryData
import com.woowahan.accountbook.data.entity.PaymentMethodData
import com.woowahan.accountbook.data.local.HistoryDao
import com.woowahan.accountbook.domain.model.Category
import com.woowahan.accountbook.domain.model.PaymentMethod
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
                    "WHERE (History.date >= ? OR History.date < ?) AND History.amount ${if (type == "income") ">" else "<"} 0"

        return dbHelper.runSQLWithReadableTransaction {
            var totalPay = 0L
            val cursor = rawQuery(sql, arrayOf(firstDayOfMonth.toString(), firstDayOfNextMonth.toString()))
            while(cursor.moveToNext()) totalPay += cursor.getInt(0)
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
                    "WHERE History.date >= ? OR History.date < ?"
        return dbHelper.runSQLWithReadableTransaction {
            val cursor = rawQuery(sql, arrayOf(firstDayOfMonth.toString(), firstDayOfNextMonth.toString()))
            val list = mutableListOf<HistoryData>()
            while(cursor.moveToNext()) {
                list.add(
                    HistoryData(
                        cursor.getInt(0),
                        cursor.getLong(1),
                        cursor.getInt(2),
                        cursor.getString(3),
                        CategoryData(
                            cursor.getInt(4),
                            cursor.getString(5),
                            cursor.getString(6),
                            cursor.getString(7).toULong()
                        ),
                        if (cursor.getInt(8) < 0)
                            null
                        else {
                            PaymentMethodData(
                                cursor.getInt(8),
                                cursor.getString(9)
                            )
                        }
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
                    "WHERE (History.date >= ? OR History.date < ?) AND History.amount ${if (type == "income") ">" else "<"} 0"
        return dbHelper.runSQLWithReadableTransaction {
            val cursor = rawQuery(sql, arrayOf(firstDayOfMonth.toString(), firstDayOfNextMonth.toString()))
            val list = mutableListOf<HistoryData>()
            while(cursor.moveToNext()) {
                list.add(
                    HistoryData(
                        cursor.getInt(0),
                        cursor.getLong(1),
                        cursor.getInt(2),
                        cursor.getString(3),
                        CategoryData(
                            cursor.getInt(4),
                            cursor.getString(5),
                            cursor.getString(6),
                            cursor.getString(7).toULong()
                        ),
                        if (cursor.getInt(8) < 0)
                            null
                        else {
                            PaymentMethodData(
                                cursor.getInt(8),
                                cursor.getString(9)
                            )
                        }
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
        amount: Int,
        content: String,
        category: CategoryData,
        paymentMethod: PaymentMethodData?
    ) {
        val sql = "INSERT INTO History (date, amount, content, category_id, payment_method_id) VALUES (?, ?, ?, ?, ${if (paymentMethod != null) "?" else "NULL"})"
        dbHelper.runSQLWithWritableTransaction {
            val statement = compileStatement(sql)
            statement.bindLong(0, date)
            statement.bindLong(1, amount.toLong())
            statement.bindString(2, content)
            statement.bindLong(3, category.id.toLong())
            paymentMethod?.id?.toLong()?.let { statement.bindLong(4, it) }
            statement.executeInsert()
        }
    }

    override suspend fun updateHistory(
        id: Int,
        date: Long,
        amount: Int,
        content: String,
        category: CategoryData,
        paymentMethod: PaymentMethodData?
    ) {
        val sql = "UPDATE History SET date = ?, amount = ?, content = ?, category_id = ?, payment_method_id = ${if (paymentMethod != null) "?" else "NULL"})"
        dbHelper.runSQLWithWritableTransaction {
            val statement = compileStatement(sql)
            statement.bindLong(0, date)
            statement.bindLong(1, amount.toLong())
            statement.bindString(2, content)
            statement.bindLong(3, category.id.toLong())
            paymentMethod?.id?.toLong()?.let { statement.bindLong(4, it) }
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