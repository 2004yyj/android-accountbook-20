package com.woowahan.accountbook.domain.repository

import com.woowahan.accountbook.domain.model.Category
import kotlinx.coroutines.flow.Flow

interface CategoryRepository {
    suspend fun getAllCategories(): List<Category>
    suspend fun getCategoryByType(type: String): Category
    suspend fun createCategoryTable()
    suspend fun dropCategoryTable()
    suspend fun insertCategory(type: String, name: String, color: Long)
    suspend fun updateCategory(id: Int, type: String, name: String, color: Long)
    suspend fun deleteCategory(id: Int)
}