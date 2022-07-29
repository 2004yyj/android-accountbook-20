package com.woowahan.accountbook.domain.repository

import com.woowahan.accountbook.domain.model.Category
import kotlinx.coroutines.flow.Flow

interface CategoryRepository {
    suspend fun getAllCategories(): List<Category>
    suspend fun getAllCategoryByType(type: String): List<Category>
    suspend fun getCategoryByName(name: String): Category
    suspend fun createCategoryTable()
    suspend fun dropCategoryTable()
    suspend fun insertCategory(type: String, name: String, color: ULong)
    suspend fun updateCategory(id: Int, type: String, name: String, color: ULong)
    suspend fun deleteCategory(id: Int)
}