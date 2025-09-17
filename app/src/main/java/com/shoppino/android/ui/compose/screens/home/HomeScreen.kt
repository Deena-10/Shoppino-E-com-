package com.shoppino.android.ui.compose.screens.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.draw.shadow
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import androidx.hilt.navigation.compose.hiltViewModel
import com.shoppino.android.ui.compose.components.ProductCard
import com.shoppino.android.ui.compose.components.ProductGrid
import com.shoppino.android.ui.compose.components.SearchBar
import com.shoppino.android.ui.compose.components.GradientBackground
import com.shoppino.android.ui.compose.theme.*
import com.shoppino.android.ui.viewmodel.ProductViewModel
import com.shoppino.android.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    onNavigateToProductDetails: (Long) -> Unit,
    onNavigateToCart: () -> Unit,
    onNavigateToProfile: () -> Unit,
    viewModel: ProductViewModel = hiltViewModel()
) {
    val products by viewModel.filteredProducts.collectAsState()
    val categories by viewModel.categories.collectAsState()
    val selectedCategory by viewModel.selectedCategory.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val error by viewModel.error.collectAsState()

    // Light gradient background
    GradientBackground(
        colors = listOf(BackgroundWhite, SurfaceLight)
    )
    
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        // Top App Bar with enhanced styling - moved to very top
        TopAppBar(
            title = {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    AsyncImage(
                        model = painterResource(id = R.drawable.shoppino1),
                        contentDescription = "Shoppino Logo",
                        modifier = Modifier
                            .size(32.dp)
                            .clip(RoundedCornerShape(4.dp)),
                        contentScale = ContentScale.Fit
                    )
                    Text(
                        text = "Shoppino",
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold,
                        color = ShoppinoBlue
                    )
                }
            },
            actions = {
                IconButton(onClick = onNavigateToCart) {
                    Icon(
                        imageVector = Icons.Default.ShoppingCart,
                        contentDescription = "Cart",
                        tint = ShoppinoBlue,
                        modifier = Modifier.size(24.dp)
                    )
                }
            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = ShoppinoWhite.copy(alpha = 0.95f)
            ),
            modifier = Modifier.shadow(4.dp, RoundedCornerShape(0.dp))
        )

        // Search Bar with minimal spacing
        SearchBar(
            onSearchQueryChanged = { query ->
                viewModel.searchProducts(query)
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 4.dp)
        )

        // Category Filter Chips with improved layout
        if (categories.isNotEmpty()) {
            LazyRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp, vertical = 4.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp),
                contentPadding = PaddingValues(horizontal = 4.dp)
            ) {
                item {
                    FilterChip(
                        onClick = { viewModel.filterByCategory(null) },
                        label = { 
                            Text(
                                "All",
                                fontWeight = if (selectedCategory == null) FontWeight.Bold else FontWeight.Normal
                            ) 
                        },
                        selected = selectedCategory == null,
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = ShoppinoBlue,
                            selectedLabelColor = ShoppinoWhite,
                            containerColor = SurfaceLight,
                            labelColor = ShoppinoGray
                        ),
                        modifier = Modifier.height(40.dp)
                    )
                }
                items(categories) { category ->
                    FilterChip(
                        onClick = { viewModel.filterByCategory(category) },
                        label = { 
                            Text(
                                category,
                                fontWeight = if (selectedCategory == category) FontWeight.Bold else FontWeight.Normal
                            ) 
                        },
                        selected = selectedCategory == category,
                        colors = FilterChipDefaults.filterChipColors(
                            selectedContainerColor = ShoppinoBlue,
                            selectedLabelColor = ShoppinoWhite,
                            containerColor = SurfaceLight,
                            labelColor = ShoppinoGray
                        ),
                        modifier = Modifier.height(40.dp)
                    )
                }
            }
            
            Spacer(modifier = Modifier.height(8.dp))
        }


        // Products Grid with improved layout
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp)
        ) {
            if (isLoading) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        CircularProgressIndicator(
                            color = ShoppinoBlue,
                            modifier = Modifier.size(48.dp),
                            strokeWidth = 4.dp
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        Text(
                            text = "Loading products...",
                            color = ShoppinoGray,
                            style = MaterialTheme.typography.bodyMedium
                        )
                    }
                }
            } else if (error != null) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = ShoppinoWhite
                        ),
                        elevation = CardDefaults.cardElevation(defaultElevation = 8.dp)
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier.padding(24.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Error,
                                contentDescription = "Error",
                                tint = ShoppinoRed,
                                modifier = Modifier.size(64.dp)
                            )
                            Spacer(modifier = Modifier.height(16.dp))
                            Text(
                                text = "Oops! Something went wrong",
                                color = ShoppinoRed,
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = error ?: "Unknown error occurred",
                                color = ShoppinoGray,
                                style = MaterialTheme.typography.bodyMedium,
                                textAlign = androidx.compose.ui.text.style.TextAlign.Center
                            )
                            Spacer(modifier = Modifier.height(24.dp))
                            Button(
                                onClick = { viewModel.refreshProducts() },
                                colors = ButtonDefaults.buttonColors(
                                    containerColor = ShoppinoBlue
                                ),
                                modifier = Modifier.fillMaxWidth()
                            ) {
                                Text(
                                    "Try Again",
                                    fontWeight = FontWeight.Bold
                                )
                            }
                        }
                    }
                }
            } else if (products.isEmpty()) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        colors = CardDefaults.cardColors(
                            containerColor = ShoppinoWhite
                        ),
                        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
                    ) {
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier.padding(24.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.SearchOff,
                                contentDescription = "No products",
                                tint = ShoppinoMediumGray,
                                modifier = Modifier.size(64.dp)
                            )
                            Spacer(modifier = Modifier.height(16.dp))
                            Text(
                                text = "No products found",
                                color = ShoppinoGray,
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold
                            )
                            Spacer(modifier = Modifier.height(8.dp))
                            Text(
                                text = "Try adjusting your search or filter",
                                color = ShoppinoMediumGray,
                                style = MaterialTheme.typography.bodyMedium,
                                textAlign = androidx.compose.ui.text.style.TextAlign.Center
                            )
                        }
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
