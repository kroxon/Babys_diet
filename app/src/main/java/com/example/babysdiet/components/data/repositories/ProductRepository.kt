package com.example.babysdiet.components.data.repositories

import com.example.babysdiet.components.data.daos.ProductDao
import com.example.babysdiet.components.data.models.Product
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@ViewModelScoped
class ProductRepository @Inject constructor(private val productDao: ProductDao) {
    val getAllProducts: Flow<List<Product>> = productDao.getAllProducts()
    val getAllergenProducts: Flow<List<Product>> = productDao.getAllergens(true)

    suspend fun addProduct(product: Product) {
        productDao.addProduct(product = product)
    }

    suspend fun deleteProduct(product: Product) {
        productDao.deleteProduct(product = product)
    }

    suspend fun updateProduct(product: Product) {
        productDao.updateProduct(product = product)
    }

    fun getProductCategory(searchQuery: String): Flow<List<Product>> {
        return productDao.getProductCategory(category = searchQuery)
    }

    fun getSelectedProduct(productId: Int): Flow<Product> {
        return productDao.getSelectedProduct(productId = productId)
    }

    fun searchByProductName(searchQuery: String): Flow<List<Product>> {
        return productDao.searchProductByName(searchQuery = searchQuery)
    }

}