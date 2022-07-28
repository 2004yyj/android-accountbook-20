package com.woowahan.accountbook.local.datasource

import com.woowahan.accountbook.data.datasource.CategoryDataSource
import com.woowahan.accountbook.data.local.CategoryDao
import com.woowahan.accountbook.data.mapper.toModel
import com.woowahan.accountbook.domain.model.Category
import javax.inject.Inject

class CategoryDataSourceImpl @Inject constructor(
    private val dao: CategoryDao
): CategoryDataSource {
    override suspend fun getAllCategories(): List<Category> {
        return dao.getAllCategories().map { it.toModel() }
    }

    override suspend fun getCategoryByType(type: String): Category {
        return dao.getCategoryByType(type).toModel()
    }

    override suspend fun createCategoryTable() {
        return dao.createCategoryTable()
    }

    override suspend fun dropCategoryTable() {
        return dao.dropCategoryTable()
    }

    override suspend fun insertCategory(type: String, name: String, color: Long) {
        return dao.insertCategory(type, name, color)
    }

    override suspend fun updateCategory(id: Int, type: String, name: String, color: Long) {
        return dao.updateCategory(id, type, name, color)
    }

    override suspend fun deleteCategory(id: Int) {
        return dao.deleteCategory(id)
    }
}