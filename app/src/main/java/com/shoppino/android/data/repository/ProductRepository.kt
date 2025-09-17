package com.shoppino.android.data.repository

import com.shoppino.android.data.api.ApiService
import com.shoppino.android.data.local.ProductDao
import com.shoppino.android.data.model.Product
import kotlinx.coroutines.flow.Flow
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ProductRepository @Inject constructor(
    private val productDao: ProductDao,
    private val apiService: ApiService,
    private val mockProductRepository: MockProductRepository
) {
    
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
    
    suspend fun getProducts(): Result<List<Product>> {
        return try {
            val response = apiService.getProducts()
            if (response.isSuccessful) {
                val products = response.body() ?: emptyList()
                productDao.insertProducts(products)
                Result.success(products)
            } else {
                // Fallback to mock data when API fails
                println("API failed, using mock data: ${response.message()}")
                val mockProducts = mockProductRepository.getMockProducts()
                productDao.insertProducts(mockProducts)
                Result.success(mockProducts)
            }
        } catch (e: Exception) {
            // Fallback to mock data when network fails
            println("Network error, using mock data: ${e.message}")
            val mockProducts = mockProductRepository.getMockProducts()
            productDao.insertProducts(mockProducts)
            Result.success(mockProducts)
        }
    }
    
    suspend fun likeProduct(productId: Long): Result<Unit> {
        return try {
            val response = apiService.likeProduct(productId)
            if (response.isSuccessful) {
                Result.success(Unit)
            } else {
                Result.failure(Exception("Failed to like product: ${response.message()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    suspend fun unlikeProduct(productId: Long): Result<Unit> {
        return try {
            val response = apiService.unlikeProduct(productId)
            if (response.isSuccessful) {
                Result.success(Unit)
            } else {
                Result.failure(Exception("Failed to unlike product: ${response.message()}"))
            }
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}
