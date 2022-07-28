package com.woowahan.accountbook.local.dao

import com.woowahan.accountbook.data.entity.CategoryData
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
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getLong(3)
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
            val categoryData = CategoryData(
                cursor.getInt(0),
                cursor.getString(1),
                cursor.getString(2),
                cursor.getLong(3)
            )
            cursor.close()
            return@runSQLWithReadableTransaction categoryData
        }
    }

    override suspend fun createCategoryTable() {
        val sql = "CREATE TABLE IF NOT EXISTS Category (id INTEGER PRIMARY KEY AUTOINCREMENT, type TEXT NOT NULL, name TEXT NOT NULL UNIQUE, color INTEGER NOT NULL);"
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

    override suspend fun insertCategory(type: String, name: String, color: Long) {
        val sql = "INSERT INTO Category (type, name, color) VALUES (?, ?, ?)"
        dbHelper.runSQLWithWritableTransaction {
            val statement = compileStatement(sql)
            statement.bindString(0, type)
            statement.bindString(1, name)
            statement.bindLong(2, color)
            statement.executeInsert()
        }
    }

    override suspend fun updateCategory(id: Int, type: String, name: String, color: Long) {
        val sql = "UPDATE Category SET type = ?, name = ?, color = ? WHERE id = $id"
        dbHelper.runSQLWithWritableTransaction {
            val statement = compileStatement(sql)
            statement.bindString(0, type)
            statement.bindString(1, name)
            statement.bindLong(2, color)
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