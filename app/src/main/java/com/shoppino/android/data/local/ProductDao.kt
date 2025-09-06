package com.shoppino.android.data.local

import androidx.room.*
import com.shoppino.android.data.model.Product
import kotlinx.coroutines.flow.Flow

@Dao
interface ProductDao {
    
    @Query("SELECT * FROM product")
    fun getAllProducts(): Flow<List<Product>>
    
    @Query("SELECT * FROM product WHERE category = :category")
    fun getProductsByCategory(category: String): Flow<List<Product>>
    
    @Query("SELECT * FROM product WHERE id = :id")
    suspend fun getProductById(id: Long): Product?
    
    @Query("SELECT * FROM product WHERE name LIKE '%' || :query || '%'")
    fun searchProducts(query: String): Flow<List<Product>>
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProduct(product: Product)
    
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertProducts(products: List<Product>)
    
    @Delete
    suspend fun deleteProduct(product: Product)
    
    @Query("DELETE FROM product")
    suspend fun deleteAllProducts()
}
