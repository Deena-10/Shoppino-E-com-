package com.shoppino.android.ui.compose.screens.catalog

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.shoppino.android.ui.compose.components.ProductCard
import com.shoppino.android.ui.compose.components.ProductGrid
import com.shoppino.android.ui.compose.components.SearchBar
import com.shoppino.android.ui.compose.theme.*
import com.shoppino.android.ui.viewmodel.ProductViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CatalogScreen(
    onNavigateBack: () -> Unit,
    onNavigateToProductDetails: (Long) -> Unit,
    viewModel: ProductViewModel = hiltViewModel()
) {
    val products by viewModel.filteredProducts.collectAsState()
    val categories by viewModel.categories.collectAsState()
    val selectedCategory by viewModel.selectedCategory.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val error by viewModel.error.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BackgroundWhite)
    ) {
        // Top App Bar
        TopAppBar(
            title = {
                Text(
                    text = "Product Catalog",
                    style = MaterialTheme.typography.headlineSmall,
                    fontWeight = FontWeight.Bold,
                    color = ShoppinoBlue
                )
            },
            navigationIcon = {
                IconButton(onClick = onNavigateBack) {
                    Icon(
                        imageVector = Icons.Default.ArrowBack,
                        contentDescription = "Back"
                    )
                }
            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = ShoppinoWhite
            )
        )

        // Search Bar
        SearchBar(
            onSearchQueryChanged = { query ->
                viewModel.searchProducts(query)
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        )

        // Category Filter Chips
        if (categories.isNotEmpty()) {
            LazyRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                item {
                    FilterChip(
                        onClick = { viewModel.filterByCategory(null) },
                        label = { Text("All") },
                        selected = selectedCategory == null,
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = ShoppinoBlue,
                            selectedLabelColor = ShoppinoWhite
                        )
                    )
                }
                items(categories) { category ->
                    FilterChip(
                        onClick = { viewModel.filterByCategory(category) },
                        label = { Text(category) },
                        selected = selectedCategory == category,
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = ShoppinoBlue,
                            selectedLabelColor = ShoppinoWhite
                        )
                    )
                }
            }
            
            Spacer(modifier = Modifier.height(16.dp))
        }

        // Products Grid
        Box(
            modifier = Modifier.fillMaxSize()
        ) {
            if (isLoading) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator(
                        color = ShoppinoBlue
                    )
                }
            } else if (error != null) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Icon(
                            imageVector = Icons.Default.Error,
                            contentDescription = "Error",
                            tint = ShoppinoRed,
                            modifier = Modifier.size(48.dp)
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            text = error ?: "Unknown error",
                            color = ShoppinoRed,
                            style = MaterialTheme.typography.bodyMedium
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Button(
                            onClick = { viewModel.refreshProducts() },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = ShoppinoBlue
                            )
                        ) {
                            Text("Retry")
                        }
                    }
                }
            } else if (products.isEmpty()) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Icon(
                            imageVector = Icons.Default.SearchOff,
                            contentDescription = "No products",
                            tint = ShoppinoMediumGray,
                            modifier = Modifier.size(48.dp)
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            text = "No products found",
                            color = ShoppinoMediumGray,
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                }
            } else {
                ProductGrid(
                    products = products,
                    onProductClick = onNavigateToProductDetails,
                    onLikeClick = { productId ->
                        val product = products.find { it.id == productId }
                        if (product?.isLiked == true) {
                            viewModel.unlikeProduct(productId)
                        } else {
                            viewModel.likeProduct(productId)
                        }
                    }
                )
            }
        }
    }
}
