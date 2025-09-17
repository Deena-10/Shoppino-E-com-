package com.shoppino.android.ui.compose.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.shoppino.android.ui.compose.screens.auth.LoginScreen
import com.shoppino.android.ui.compose.screens.auth.SignupScreen
import com.shoppino.android.ui.compose.screens.auth.VerifyCodeScreen
import com.shoppino.android.ui.compose.screens.auth.ForgotPasswordScreen
import com.shoppino.android.ui.compose.screens.home.HomeScreen
import com.shoppino.android.ui.compose.screens.categories.CategoriesScreen
import com.shoppino.android.ui.compose.screens.catalog.CatalogScreen
import com.shoppino.android.ui.compose.screens.product.ProductDetailScreen
import com.shoppino.android.ui.compose.screens.settings.SettingsScreen
import com.shoppino.android.ui.compose.screens.cart.CartScreen
import com.shoppino.android.ui.compose.screens.checkout.CheckoutScreen
import com.shoppino.android.ui.compose.screens.orders.MyOrdersScreen
import com.shoppino.android.ui.compose.screens.profile.ProfileScreen
import com.shoppino.android.ui.compose.screens.admin.AdminProductsScreen
import com.shoppino.android.ui.compose.screens.admin.AdminOrdersScreen

@Composable
fun ShoppinoNavigation(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    NavHost(
        navController = navController,
        startDestination = "home",
        modifier = modifier
    ) {
        // Auth Screens
        composable("login") {
            LoginScreen(
                onNavigateToSignup = { navController.navigate("signup") },
                onNavigateToForgotPassword = { navController.navigate("forgot_password") },
                onLoginSuccess = { navController.navigate("home") }
            )
        }
        
        composable("signup") {
            SignupScreen(
                onNavigateToLogin = { navController.navigate("login") },
                onSignupSuccess = { navController.navigate("verify_code") }
            )
        }
        
        composable("verify_code") {
            VerifyCodeScreen(
                onNavigateToLogin = { navController.navigate("login") },
                onVerificationSuccess = { navController.navigate("home") }
            )
        }
        
        composable("forgot_password") {
            ForgotPasswordScreen(
                onNavigateToLogin = { navController.navigate("login") },
                onNavigateToVerifyCode = { navController.navigate("verify_code") }
            )
        }
        
        // Main Screens
        composable("home") {
            HomeScreen(
                onNavigateToProductDetails = { productId ->
                    navController.navigate("product_details/$productId")
                },
                onNavigateToCart = { navController.navigate("cart") },
                onNavigateToProfile = { navController.navigate("profile") }
            )
        }
        
        composable("categories") {
            CategoriesScreen(
                onNavigateToCategoryProducts = { category ->
                    navController.navigate("category_products/$category")
                }
            )
        }
        
        composable("category_products/{category}") { backStackEntry ->
            val category = backStackEntry.arguments?.getString("category") ?: ""
            CatalogScreen(
                onNavigateBack = { navController.popBackStack() },
                onNavigateToProductDetails = { productId ->
                    navController.navigate("product_details/$productId")
                }
            )
        }
        
        composable("catalog") {
            CatalogScreen(
                onNavigateBack = { navController.popBackStack() },
                onNavigateToProductDetails = { productId ->
                    navController.navigate("product_details/$productId")
                }
            )
        }
        
        composable("product_details/{productId}") { backStackEntry ->
            val productId = backStackEntry.arguments?.getString("productId")?.toLongOrNull()
            if (productId != null) {
                ProductDetailScreen(
                    productId = productId,
                    onNavigateBack = { navController.popBackStack() },
                    onNavigateToCart = { navController.navigate("cart") },
                    onNavigateToCheckout = { navController.navigate("checkout") },
                    onProductClick = { id -> navController.navigate("product_details/$id") }
                )
            }
        }
        
        composable("cart") {
            CartScreen(
                onNavigateBack = { navController.popBackStack() },
                onNavigateToCheckout = { navController.navigate("checkout") }
            )
        }
        
        composable("checkout") {
            CheckoutScreen(
                onNavigateBack = { navController.popBackStack() },
                onOrderPlaced = { navController.navigate("my_orders") }
            )
        }
        
        composable("orders") {
            MyOrdersScreen(
                onNavigateBack = { navController.popBackStack() }
            )
        }
        
        composable("my_orders") {
            MyOrdersScreen(
                onNavigateBack = { navController.popBackStack() }
            )
        }
        
        composable("settings") {
            SettingsScreen(
                onNavigateToLogin = { navController.navigate("login") }
            )
        }
        
        composable("profile") {
            ProfileScreen(
                onNavigateToLogin = { navController.navigate("login") },
                onNavigateToMyOrders = { navController.navigate("my_orders") },
                onNavigateToAdminProducts = { navController.navigate("admin_products") },
                onNavigateToAdminOrders = { navController.navigate("admin_orders") }
            )
        }
        
        // Admin Screens
        composable("admin_products") {
            AdminProductsScreen(
                onNavigateBack = { navController.popBackStack() }
            )
        }
        
        composable("admin_orders") {
            AdminOrdersScreen(
                onNavigateBack = { navController.popBackStack() }
            )
        }
    }
}
