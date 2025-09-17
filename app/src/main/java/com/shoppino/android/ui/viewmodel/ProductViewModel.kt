package com.shoppino.android.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.shoppino.android.data.model.Product
import com.shoppino.android.data.repository.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductViewModel @Inject constructor(
    private val productRepository: ProductRepository
) : ViewModel() {

    private val _products = MutableStateFlow<List<Product>>(emptyList())
    val products: StateFlow<List<Product>> = _products.asStateFlow()

    private val _filteredProducts = MutableStateFlow<List<Product>>(emptyList())
    val filteredProducts: StateFlow<List<Product>> = _filteredProducts.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()

    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()

    private val _selectedCategory = MutableStateFlow<String?>(null)
    val selectedCategory: StateFlow<String?> = _selectedCategory.asStateFlow()

    private val _categories = MutableStateFlow<List<String>>(emptyList())
    val categories: StateFlow<List<String>> = _categories.asStateFlow()

    init {
        loadProducts()
    }

    fun loadProducts() {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null
            
            try {
                val result = productRepository.getProducts()
                if (result.isSuccess) {
                    val productList = result.getOrNull() ?: emptyList()
                    _products.value = productList
                    _filteredProducts.value = productList
                    _categories.value = productList.map { it.category }.distinct()
                } else {
                    _error.value = result.exceptionOrNull()?.message ?: "Failed to load products"
                }
            } catch (e: Exception) {
                _error.value = e.message ?: "Failed to load products"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun searchProducts(query: String) {
        _searchQuery.value = query
        applyFilters()
    }

    fun filterByCategory(category: String?) {
        _selectedCategory.value = category
        applyFilters()
    }

    fun clearFilters() {
        _searchQuery.value = ""
        _selectedCategory.value = null
        _filteredProducts.value = _products.value
    }

    private fun applyFilters() {
        val query = _searchQuery.value.lowercase()
        val category = _selectedCategory.value
        
        val filtered = _products.value.filter { product ->
            val matchesSearch = query.isEmpty() || 
                product.name.lowercase().contains(query) ||
                product.description.lowercase().contains(query)
            
            val matchesCategory = category == null || product.category == category
            
            matchesSearch && matchesCategory
        }
        
        _filteredProducts.value = filtered
    }

    fun likeProduct(productId: Long) {
        viewModelScope.launch {
            try {
                val result = productRepository.likeProduct(productId)
                if (result.isSuccess) {
                    // Update local state optimistically
                    updateProductLikeStatus(productId, true)
                } else {
                    _error.value = result.exceptionOrNull()?.message ?: "Failed to like product"
                }
            } catch (e: Exception) {
                _error.value = e.message ?: "Failed to like product"
            }
        }
    }

    fun unlikeProduct(productId: Long) {
        viewModelScope.launch {
            try {
                val result = productRepository.unlikeProduct(productId)
                if (result.isSuccess) {
                    // Update local state optimistically
                    updateProductLikeStatus(productId, false)
                } else {
                    _error.value = result.exceptionOrNull()?.message ?: "Failed to unlike product"
                }
            } catch (e: Exception) {
                _error.value = e.message ?: "Failed to unlike product"
            }
        }
    }

    private fun updateProductLikeStatus(productId: Long, isLiked: Boolean) {
        _products.value = _products.value.map { product ->
            if (product.id == productId) {
                product.copy(isLiked = isLiked)
            } else {
                product
            }
        }
        applyFilters()
    }

    fun getProductById(id: Long): Product? {
        return _products.value.find { it.id == id }
    }

    fun clearError() {
        _error.value = null
    }

    fun refreshProducts() {
        loadProducts()
    }
}
