package com.woowahan.accountbook.local.dao

import com.woowahan.accountbook.data.entity.*
import com.woowahan.accountbook.data.local.HistoryDao
import com.woowahan.accountbook.local.helper.DatabaseOpenHelper
import com.woowahan.accountbook.local.util.runSQL
import com.woowahan.accountbook.local.util.runSQLWithReadableTransaction
import com.woowahan.accountbook.local.util.runSQLWithWritableTransaction
import com.woowahan.accountbook.local.util.whereInSQLQueryIdList
import javax.inject.Inject

class HistoryDaoImpl @Inject constructor(
    private val dbHelper: DatabaseOpenHelper
): HistoryDao {
    override suspend fun getTotalPayByMonthAndType(firstDayOfMonth: Long, firstDayOfNextMonth: Long, type: PaymentTypeData): Long {
        val sql =
            "SELECT History.*, Category.*, PaymentMethod.* FROM History " +
                    "INNER JOIN Category " +
                    "ON History.category_id = Category.id " +
                    "LEFT OUTER JOIN PaymentMethod " +
                    "ON IFNULL(History.payment_method_id, -1) = PaymentMethod.id " +
                    "WHERE History.date >= ? AND History.date < ? AND History.amount ${if (type == PaymentTypeData.Income) ">" else "<"} 0"

        return dbHelper.runSQLWithReadableTransaction {
            var totalPay = 0L
            val cursor = rawQuery(sql, arrayOf(firstDayOfMonth.toString(), firstDayOfNextMonth.toString()))
            while(cursor.moveToNext()) {
                if (PaymentTypeData.valueOf(cursor.getString(7)) == type) {
                    totalPay +=
                        if (PaymentTypeData.valueOf(cursor.getString(7)) == PaymentTypeData.Income)
                            cursor.getLong(2)
                        else cursor.getLong(2) * -1
                }
            }
            cursor.close()
            totalPay
        }
    }

    override suspend fun getAllHistoriesByMonthAndType(firstDayOfMonth: Long, firstDayOfNextMonth: Long): List<HistoryData> {
        val sql ="SELECT History.*, " +
                "IFNULL(income.amount, 0), " +
                "IFNULL(expense.amount, 0), " +
                "Category.*, PaymentMethod.* " +
                "FROM History " +
                "LEFT OUTER JOIN (SELECT total(amount) as amount, date FROM History WHERE amount > 0 GROUP BY date) as income " +
                "ON History.date = income.date " +
                "LEFT OUTER JOIN (SELECT total(amount) as amount, date FROM History WHERE amount < 0 GROUP BY date) as expense " +
                "ON income.date = expense.date " +
                "INNER JOIN Category " +
                "ON History.category_id = Category.id " +
                "LEFT OUTER JOIN PaymentMethod " +
                "ON IFNULL(History.payment_method_id, -1) = PaymentMethod.id " +
                "WHERE History.date >= ? AND History.date < ? " +
                "ORDER BY History.date"
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
                        cursor.getLong(6),
                        cursor.getLong(7),
                        CategoryData(
                            cursor.getInt(8),
                            PaymentTypeData.valueOf(cursor.getString(9)),
                            cursor.getString(10),
                            cursor.getString(11).toULong()
                        ),
                        if (PaymentTypeData.valueOf(cursor.getString(9)) == PaymentTypeData.Income) null
                        else PaymentMethodData(
                            cursor.getInt(12),
                            cursor.getString(13),
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
        type: PaymentTypeData
    ): List<HistoryData> {

        val sql ="SELECT History.*, " +
                "IFNULL(income.amount, 0), " +
                "IFNULL(expense.amount, 0), " +
                "Category.*, PaymentMethod.* " +
                "FROM History " +
                "LEFT OUTER JOIN (SELECT total(amount) as amount, date FROM History WHERE amount > 0 GROUP BY date) as income " +
                "ON History.date = income.date " +
                "LEFT OUTER JOIN (SELECT total(amount) as amount, date FROM History WHERE amount < 0 GROUP BY date) as expense " +
                "ON income.date = expense.date " +
                "INNER JOIN Category " +
                "ON History.category_id = Category.id " +
                "LEFT OUTER JOIN PaymentMethod " +
                "ON IFNULL(History.payment_method_id, -1) = PaymentMethod.id " +
                "WHERE History.date >= ? AND History.date < ? AND History.amount ${if (type == PaymentTypeData.Income) ">" else "<"} 0 " +
                "ORDER BY History.date"

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
                        cursor.getLong(6),
                        cursor.getLong(7),
                        CategoryData(
                            cursor.getInt(8),
                            PaymentTypeData.valueOf(cursor.getString(9)),
                            cursor.getString(10),
                            cursor.getString(11).toULong()
                        ),
                        if (PaymentTypeData.valueOf(cursor.getString(9)) == PaymentTypeData.Income) null
                        else PaymentMethodData(
                            cursor.getInt(12),
                            cursor.getString(13),
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

    override suspend fun deleteAllHistory(idList: List<Int>) {
        val sql = "DELETE FROM History WHERE id IN ${whereInSQLQueryIdList(idList)}"
        dbHelper.runSQLWithWritableTransaction {
            val statement = compileStatement(sql)
            statement.executeUpdateDelete()
        }
    }

    override suspend fun getAllStatisticsByCategoryType(
        firstDayOfMonth: Long,
        firstDayOfNextMonth: Long
    ): List<StatisticData> {
        val sql = "SELECT C.name, total(H.amount), " +
                "total(H.amount)/(SELECT total(amount) FROM History WHERE amount < 0 AND date >= ? AND date < ?), " +
                "C.color " +
                "FROM History AS H " +
                "INNER JOIN Category AS C " +
                "ON H.category_id = C.id " +
                "WHERE H.amount < 0 AND H.date >= ? AND H.date < ? GROUP BY C.id"
        return dbHelper.runSQLWithReadableTransaction {
            val cursor = rawQuery(sql,
                arrayOf(
                    firstDayOfMonth.toString(),
                    firstDayOfNextMonth.toString(),
                    firstDayOfMonth.toString(),
                    firstDayOfNextMonth.toString()
                )
            )
            val list = mutableListOf<StatisticData>()
            while (cursor.moveToNext()) {
                list.add(
                    StatisticData(
                        cursor.getString(0),
                        cursor.getLong(1),
                        cursor.getFloat(2),
                        cursor.getString(3).toULong()
                    )
                )
            }
            list
        }
    }
}