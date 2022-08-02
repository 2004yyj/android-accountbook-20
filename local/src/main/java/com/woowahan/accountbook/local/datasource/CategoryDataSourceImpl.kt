package com.woowahan.accountbook.local.datasource

import com.woowahan.accountbook.data.datasource.CategoryDataSource
import com.woowahan.accountbook.data.local.CategoryDao
import com.woowahan.accountbook.data.mapper.toEntity
import com.woowahan.accountbook.data.mapper.toModel
import com.woowahan.accountbook.domain.model.Category
import com.woowahan.accountbook.domain.model.PaymentType
import javax.inject.Inject

class CategoryDataSourceImpl @Inject constructor(
    private val dao: CategoryDao
): CategoryDataSource {
    override suspend fun getAllCategories(): List<Category> {
        return dao.getAllCategories().map { it.toModel() }
    }

    override suspend fun getAllCategoryByType(type: PaymentType): List<Category> {
        return dao.getAllCategoryByType(type.toString()).map { it.toModel() }
    }

    override suspend fun getCategoryByName(name: String): Category {
        return dao.getCategoryByName(name).toModel()
    }

    override suspend fun getCategoryById(id: Int): Category {
        return dao.getCategoryById(id).toModel()
    }

    override suspend fun createCategoryTable() {
        return dao.createCategoryTable()
    }

    override suspend fun dropCategoryTable() {
        return dao.dropCategoryTable()
    }

    override suspend fun insertCategory(type: PaymentType, name: String, color: ULong) {
        return dao.insertCategory(type.toEntity(), name, color)
    }

    override suspend fun updateCategory(id: Int, type: PaymentType, name: String, color: ULong) {
        return dao.updateCategory(id, type.toEntity(), name, color)
    }

    override suspend fun deleteCategory(id: Int) {
        return dao.deleteCategory(id)
    }
}