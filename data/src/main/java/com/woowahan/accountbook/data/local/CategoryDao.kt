package com.woowahan.accountbook.data.local

import com.woowahan.accountbook.data.entity.CategoryData
import com.woowahan.accountbook.data.entity.PaymentTypeData
import com.woowahan.accountbook.domain.model.Category
import kotlinx.coroutines.flow.Flow

interface CategoryDao {
    suspend fun getAllCategories(): List<CategoryData>
    suspend fun getAllCategoryByType(type: String): List<CategoryData>
    suspend fun getCategoryByName(name: String): CategoryData
    suspend fun createCategoryTable()
    suspend fun dropCategoryTable()
    suspend fun insertCategory(type: PaymentTypeData, name: String, color: ULong)
    suspend fun updateCategory(id: Int, type: PaymentTypeData, name: String, color: ULong)
    suspend fun deleteCategory(id: Int)
}