package com.woowahan.accountbook.data.local

import com.woowahan.accountbook.data.entity.CategoryData
import com.woowahan.accountbook.domain.model.Category
import kotlinx.coroutines.flow.Flow

interface CategoryDao {
    suspend fun getAllCategories(): List<CategoryData>
    suspend fun getCategoryByType(type: String): CategoryData
    suspend fun createCategoryTable()
    suspend fun dropCategoryTable()
    suspend fun insertCategory(type: String, name: String, color: Long)
    suspend fun updateCategory(id: Int, type: String, name: String, color: Long)
    suspend fun deleteCategory(id: Int)
}