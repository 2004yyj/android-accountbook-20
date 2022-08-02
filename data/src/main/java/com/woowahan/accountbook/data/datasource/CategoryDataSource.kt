package com.woowahan.accountbook.data.datasource

import com.woowahan.accountbook.domain.model.Category
import com.woowahan.accountbook.domain.model.PaymentType
import kotlinx.coroutines.flow.Flow

interface CategoryDataSource {
    suspend fun getAllCategories(): List<Category>
    suspend fun getAllCategoryByType(type: PaymentType): List<Category>
    suspend fun getCategoryByName(name: String): Category
    suspend fun getCategoryById(id: Int): Category
    suspend fun createCategoryTable()
    suspend fun dropCategoryTable()
    suspend fun insertCategory(type: PaymentType, name: String, color: ULong)
    suspend fun updateCategory(id: Int, type: PaymentType, name: String, color: ULong)
    suspend fun deleteCategory(id: Int)
}