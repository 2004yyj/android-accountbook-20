package com.woowahan.accountbook.local.dao

import com.woowahan.accountbook.data.entity.CategoryData
import com.woowahan.accountbook.data.entity.PaymentTypeData
import com.woowahan.accountbook.data.local.CategoryDao
import com.woowahan.accountbook.local.helper.DatabaseOpenHelper
import com.woowahan.accountbook.local.util.runSQL
import com.woowahan.accountbook.local.util.runSQLWithReadableTransaction
import com.woowahan.accountbook.local.util.runSQLWithWritableTransaction
import javax.inject.Inject

class CategoryDaoImpl @Inject constructor(
    private val dbHelper: DatabaseOpenHelper
): CategoryDao {
    override suspend fun getAllCategories(): List<CategoryData> {
        val sql = "SELECT * FROM Category"
        return dbHelper.runSQLWithReadableTransaction<List<CategoryData>> {
            val cursor = rawQuery(sql, arrayOf())
            val list = mutableListOf<CategoryData>()
            while (cursor.moveToNext()) {
                list.add(
                    CategoryData(
                        cursor.getInt(0),
                        PaymentTypeData.valueOf(cursor.getString(1)),
                        cursor.getString(2),
                        cursor.getString(3).toULong()
                    )
                )
            }
            cursor.close()
            return@runSQLWithReadableTransaction list
        }
    }

    override suspend fun getAllCategoryByType(type: String): List<CategoryData> {
        val sql = "SELECT * FROM Category WHERE type = ?"
        return dbHelper.runSQLWithReadableTransaction<List<CategoryData>> {
            val cursor = rawQuery(sql, arrayOf(type))
            val list = mutableListOf<CategoryData>()
            while (cursor.moveToNext()) {
                list.add(
                    CategoryData(
                        cursor.getInt(0),
                        PaymentTypeData.valueOf(cursor.getString(1)),
                        cursor.getString(2),
                        cursor.getString(3).toULong()
                    )
                )
            }
            cursor.close()
            return@runSQLWithReadableTransaction list
        }
    }

    override suspend fun getCategoryByName(name: String): CategoryData {
        val sql = "SELECT * FROM Category WHERE name = ?"
        return dbHelper.runSQLWithReadableTransaction {
            val cursor = rawQuery(sql, arrayOf(name))
            cursor.moveToFirst()
            val categoryData = CategoryData(
                cursor.getInt(0),
                PaymentTypeData.valueOf(cursor.getString(1)),
                cursor.getString(2),
                cursor.getString(3).toULong()
            )
            cursor.close()
            return@runSQLWithReadableTransaction categoryData
        }
    }

    override suspend fun createCategoryTable() {
        val sql = "CREATE TABLE IF NOT EXISTS Category (id INTEGER PRIMARY KEY AUTOINCREMENT, type TEXT NOT NULL, name TEXT NOT NULL UNIQUE, color TEXT NOT NULL);"
        dbHelper.runSQL {
            val statement = compileStatement(sql)
            statement.execute()
        }
    }

    override suspend fun dropCategoryTable() {
        val sql = "DROP TABLE Category;"
        dbHelper.runSQL {
            val statement = compileStatement(sql)
            statement.execute()
        }
    }

    override suspend fun insertCategory(type: PaymentTypeData, name: String, color: ULong) {
        val sql = "INSERT INTO Category (type, name, color) VALUES (?, ?, ?)"
        dbHelper.runSQLWithWritableTransaction {
            val statement = compileStatement(sql)
            statement.bindString(1, type.toString())
            statement.bindString(2, name)
            statement.bindString(3, color.toString())
            statement.executeInsert()
        }
    }

    override suspend fun updateCategory(id: Int, type: PaymentTypeData, name: String, color: ULong) {
        val sql = "UPDATE Category SET type = ?, name = ?, color = ? WHERE id = $id"
        dbHelper.runSQLWithWritableTransaction {
            val statement = compileStatement(sql)
            statement.bindString(1, type.toString())
            statement.bindString(2, name)
            statement.bindString(3, color.toString())
            statement.executeUpdateDelete()
        }
    }

    override suspend fun deleteCategory(id: Int) {
        val sql = "DELETE FROM Category WHERE id = $id"
        dbHelper.runSQLWithWritableTransaction {
            val statement = compileStatement(sql)
            statement.executeUpdateDelete()
        }
    }
}