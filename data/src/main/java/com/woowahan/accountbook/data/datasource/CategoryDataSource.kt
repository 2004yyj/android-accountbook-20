package com.woowahan.accountbook.data.datasource

import com.woowahan.accountbook.domain.model.Category
import kotlinx.coroutines.flow.Flow

interface CategoryDataSource {
    suspend fun getAllCategories(): List<Category>
    suspend fun getCategoryByName(name: String): Category
    suspend fun createCategoryTable()
    suspend fun dropCategoryTable()
    suspend fun insertCategory(type: String, name: String, color: Long)
    suspend fun updateCategory(id: Int, type: String, name: String, color: Long)
    suspend fun deleteCategory(id: Int)
}