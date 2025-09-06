package com.shoppino.android.data.repository

import com.shoppino.android.data.api.ApiService
import com.shoppino.android.data.local.ProductDao
import com.shoppino.android.data.model.Product
import com.shoppino.android.data.network.NetworkConfig
import kotlinx.coroutines.flow.Flow
import retrofit2.Response

class ProductRepository(
    private val productDao: ProductDao
) {
    
    private val apiService: ApiService = NetworkConfig.apiService
    
    fun getAllProducts(): Flow<List<Product>> {
        return productDao.getAllProducts()
    }
    
    fun getProductsByCategory(category: String): Flow<List<Product>> {
        return productDao.getProductsByCategory(category)
    }
    
    fun searchProducts(query: String): Flow<List<Product>> {
        return productDao.searchProducts(query)
    }
    
    suspend fun refreshProductsFromApi() {
        try {
            val response = apiService.getProducts()
            if (response.isSuccessful) {
                response.body()?.let { products ->
                    productDao.insertProducts(products)
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
    
    suspend fun getProductById(id: Long): Product? {
        return productDao.getProductById(id)
    }
    
    suspend fun insertProduct(product: Product) {
        productDao.insertProduct(product)
    }
    
    suspend fun insertProducts(products: List<Product>) {
        productDao.insertProducts(products)
    }
}
