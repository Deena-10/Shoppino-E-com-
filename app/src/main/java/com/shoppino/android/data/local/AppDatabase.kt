package com.shoppino.android.data.local

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import android.content.Context
import com.shoppino.android.data.model.Product
import com.shoppino.android.data.model.User
import com.shoppino.android.data.model.CartItem
import com.shoppino.android.data.model.Order
import com.shoppino.android.data.model.OrderItem

@Database(
    entities = [
        Product::class, 
        User::class, 
        CartItem::class, 
        Order::class, 
        OrderItem::class
    ],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    
    abstract fun productDao(): ProductDao
    abstract fun userDao(): UserDao
    abstract fun cartDao(): CartDao
    abstract fun orderDao(): OrderDao
    abstract fun orderItemDao(): OrderItemDao
    
    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null
        
        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "shoppino_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
