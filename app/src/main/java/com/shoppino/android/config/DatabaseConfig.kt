package com.shoppino.android.config

/**
 * 🔧 **DATABASE CONFIGURATION FOR YOUR EXISTING "ECOMM" DATABASE**
 * 
 * This connects to your existing XAMPP MySQL database called "ecomm"
 * which already has your web project tables and data.
 */
object DatabaseConfig {
    
    // 🌐 **API Configuration - Connect to your Spring Boot backend**
    // Your Spring Boot backend should connect to the "ecomm" database
    const val API_BASE_URL = "http://10.0.2.2:8080/api/"
    
    // 📱 **Device Configuration**
    // For Android Emulator: use "10.0.2.2" (points to your PC's localhost)
    // For Physical Device: use your PC's actual IP address (e.g., "192.168.1.100")
    
    // 🔄 **Alternative URLs (uncomment the one you need)**
    // const val API_BASE_URL = "http://192.168.1.100:8080/api/"  // Physical device
    // const val API_BASE_URL = "http://localhost:8080/api/"       // Same machine
    
    // ⚙️ **Your Existing Database Tables in "ecomm":**
    // - product (24 products with id, description, img, name, price, rating, stock, category, deleted)
    // - user (6 users with id, email, name, password, role, address, phone_number, refresh_token, verified, verificationCode, otpExpiryTime)
    // - cart_item (cart items with id, product_id, quantity)
    // - orders (124 orders with id, customerName, customerEmail, orderDate, totalPrice, user_id)
    // - order_item (order items with id, productName, quantity, price, order_id, product_id)
    
    // 🚀 **Your Spring Boot API Endpoints:**
    // - GET /api/products - Get all products
    // - GET /api/products/{id} - Get product by ID
    // - GET /api/products/category/{category} - Get products by category
    // - GET /api/products/search?query={query} - Search products
    // - GET /api/cart - Get cart items
    // - POST /api/cart - Add to cart
    // - DELETE /api/cart/{productId} - Remove from cart
    // - POST /api/orders/place - Place order
    // - GET /api/orders/my - Get user orders
    // - GET /api/user/profile - Get user profile
    // - PUT /api/user/profile - Update user profile
    
    // 🔑 **Authentication Headers (if needed)**
    const val AUTH_HEADER = "Authorization"
    const val AUTH_PREFIX = "Bearer "
    
    // ⏱️ **Network Timeouts**
    const val CONNECT_TIMEOUT = 30L  // seconds
    const val READ_TIMEOUT = 30L     // seconds
    const val WRITE_TIMEOUT = 30L    // seconds
}
