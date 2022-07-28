package com.woowahan.accountbook.data.repository

import com.woowahan.accountbook.data.datasource.CategoryDataSource
import com.woowahan.accountbook.domain.model.Category
import com.woowahan.accountbook.domain.repository.CategoryRepository
import javax.inject.Inject

class CategoryRepositoryImpl @Inject constructor(
    private val dataSource: CategoryDataSource
): CategoryRepository {
    override suspend fun getAllCategories(): List<Category> {
        return dataSource.getAllCategories()
    }

    override suspend fun getCategoryByName(name: String): Category {
        return dataSource.getCategoryByName(name)
    }

    override suspend fun createCategoryTable() {
        return dataSource.createCategoryTable()
    }

    override suspend fun dropCategoryTable() {
        return dataSource.dropCategoryTable()
    }

    override suspend fun insertCategory(type: String, name: String, color: Long) {
        return dataSource.insertCategory(type, name, color)
    }

    override suspend fun updateCategory(id: Int, type: String, name: String, color: Long) {
        return dataSource.updateCategory(id, type, name, color)
    }

    override suspend fun deleteCategory(id: Int) {
        return dataSource.deleteCategory(id)
    }

}