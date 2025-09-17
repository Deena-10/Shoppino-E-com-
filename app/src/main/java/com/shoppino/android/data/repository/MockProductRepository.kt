package com.shoppino.android.data.repository

import com.shoppino.android.data.model.Product
import kotlinx.coroutines.delay
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MockProductRepository @Inject constructor() {
    
    private val mockProducts = listOf(
        // Mobile Phones
        Product(
            id = 26,
            name = "Redmi A5 5G (Starry Black, 4GB RAM, 64GB Storage)",
            description = "Global Debut SD 4s Gen 2 | Segment Largest 6.88in 120Hz | 50MP Dual Camera | 18W Fast Charging",
            img = "https://m.media-amazon.com/images/I/718HzJbvY1L._SL1500_.jpg",
            rating = 5,
            stock = 2,
            price = 7999.0,
            category = "Mobile",
            deleted = false,
            isLiked = false
        ),
        Product(
            id = 93,
            name = "Motorola Edge 50 Fusion 5G",
            description = "(Forest Blue, 12GB RAM, 256GB Storage)",
            img = "https://m.media-amazon.com/images/I/71ZJ6bsARtL._SX522_.jpg",
            rating = 5,
            stock = 20,
            price = 21800.0,
            category = "Mobile",
            deleted = false,
            isLiked = false
        ),
        Product(
            id = 94,
            name = "iQOO Neo 10R 5G",
            description = "Moonknight Titanium, 8GB RAM, 128GB Storage",
            img = "https://m.media-amazon.com/images/I/610NUM9jlxL._SX425_.jpg",
            rating = 4,
            stock = 19,
            price = 26998.0,
            category = "Mobile",
            deleted = false,
            isLiked = false
        ),
        Product(
            id = 95,
            name = "Honor X7c 5G Dual SIM",
            description = "16GB (8+8) RAM, 256GB Storage | 50MP AI Camera | 5 Star SGS Certified | IP64 Standards | 120Hz Refresh Rate | 5200mAh Battery | Without Charger | (Vegan Leather Forest Green)",
            img = "https://m.media-amazon.com/images/I/81+32GDaWVL._SX425_.jpg",
            rating = 4,
            stock = 6,
            price = 15998.0,
            category = "Mobile",
            deleted = false,
            isLiked = false
        ),
        Product(
            id = 96,
            name = "Redmi 14C 5G (Stargaze Black, 6GB RAM, 128GB Storage)",
            description = "Superfast 4nm Snapdragon 4 Gen 2 | 120Hz 17.47cm (6.88\") Display | 5160mAh Battery | 50MP Dual Camera | Premium Starlight Design",
            img = "https://m.media-amazon.com/images/I/81PHUjI+raL._SX425_.jpg",
            rating = 4,
            stock = 13,
            price = 11000.0,
            category = "Mobile",
            deleted = false,
            isLiked = false
        ),
        Product(
            id = 97,
            name = "realme NARZO 80 Lite 5G",
            description = "Crystal Purple, 6GB+128GB | 6000mAh Long-Lasting Battery | MediaTek Dimensity 6300 5G | AI Assist | IP64 Rated Water & Dust Resistance | Military-Grade Durability",
            img = "https://m.media-amazon.com/images/I/71Vjn1DfArl._SX425_.jpg",
            rating = 4,
            stock = 8,
            price = 10999.0,
            category = "Mobile",
            deleted = false,
            isLiked = false
        ),
        Product(
            id = 98,
            name = "Lava Shark 5G",
            description = "Steller Blue, 4GB RAM, 64GB Storage | Bigger 6.75 HD+ Notch Display | Octacore 5G Processor | 13 MP AI Camera | 5000 mAh Battery | IP54 Rated Dust & Water Splash Proof",
            img = "https://m.media-amazon.com/images/I/51nZ5TcPTWL._SX425_.jpg",
            rating = 3,
            stock = 6,
            price = 7999.0,
            category = "Mobile",
            deleted = false,
            isLiked = false
        ),
        Product(
            id = 99,
            name = "POCO C75 5G",
            description = "Aqua Bliss (4GB, 64GB)",
            img = "https://m.media-amazon.com/images/I/61DnXnpjn1L._SX425_.jpg",
            rating = 3,
            stock = 3,
            price = 7500.0,
            category = "Mobile",
            deleted = false,
            isLiked = false
        ),
        
        // Laptops
        Product(
            id = 100,
            name = "Acer ALG, Intel Core i7-13th Gen 13620H Processor, NVIDIA GeForce RTX 3050-6GB",
            description = "16GB/512GB, FHD, 39.62cm(15.6), 144Hz, Windows 11 Home, Steel Gray, 1.99KG, AL15G-53, Premium Metal Body, Gaming Laptop",
            img = "https://m.media-amazon.com/images/I/81sAOthl+7L._SX450_.jpg",
            rating = 5,
            stock = 12,
            price = 70000.0,
            category = "Laptop",
            deleted = false,
            isLiked = false
        ),
        Product(
            id = 102,
            name = "Acer Nitro V 16, Intel Core i5-14th Gen 14450HX Processor, RTX 4050-6 GB",
            description = "laptop",
            img = "https://m.media-amazon.com/images/I/815hVOKMQZL._SY450_.jpg",
            rating = 4,
            stock = 5,
            price = 84500.0,
            category = "Laptop",
            deleted = false,
            isLiked = false
        ),
        Product(
            id = 103,
            name = "HP 14, 13th Gen Intel Core i3",
            description = "1315U Laptop (8GB DDR4,512GB SSD) Anti-Glare, Micro-Edge, 14/35.6cm, FHD, Win11, M365 Basic(1yr),Office Home24, Silver,1.4kg, FHD Camera w/Privacy Shutter",
            img = "https://m.media-amazon.com/images/I/71wXgNowzAL._SL1500_.jpg",
            rating = 4,
            stock = 9,
            price = 36990.0,
            category = "Laptop",
            deleted = false,
            isLiked = false
        ),
        Product(
            id = 104,
            name = "ASUS Vivobook S16",
            description = "13th Gen,Intel Core i5-13420H,Metallic Design Laptop(Intel UHD iGPU/16GB RAM/512GB SSD/FHD+/16\\\"/144Hz/Windows 11/M365...",
            img = "https://m.media-amazon.com/images/I/71LwIxMzWDL._SL1500_.jpg",
            rating = 4,
            stock = 7,
            price = 55000.0,
            category = "Laptop",
            deleted = false,
            isLiked = false
        ),
        Product(
            id = 105,
            name = "Lenovo Smartchoice Ideapad Slim 3 13Th Gen Intel Core I7",
            description = "13620H 15.3 Inch(38.8Cm) WUXGA IPS Laptop(16GB RAM/512GB SSD/Windows 11/Office Home 2024/Backlit Keyboard/1Yr ADP Free/Grey/1.6Kg),83K100CJIN",
            img = "https://m.media-amazon.com/images/I/71HMPbAf-iL._SL1500_.jpg",
            rating = 4,
            stock = 2,
            price = 60000.0,
            category = "Laptop",
            deleted = false,
            isLiked = false
        ),
        
        // Cameras
        Product(
            id = 106,
            name = "Nikon D7500 20.9MP Digital SLR Camera (Black) with AF",
            description = "DX NIKKOR 18-140mm f/3.5-5.6G ED VR Lens",
            img = "https://m.media-amazon.com/images/I/71iKNJ6rVIL._SL1000_.jpg",
            rating = 4,
            stock = 13,
            price = 75000.0,
            category = "Camera",
            deleted = false,
            isLiked = false
        ),
        Product(
            id = 107,
            name = "Canon EOS R6 Mark II",
            description = "24.2 MP Mirrorless Camera-Body Only (Black)",
            img = "https://m.media-amazon.com/images/I/61S-4Ri3Z2L._SL1500_.jpg",
            rating = 4,
            stock = 6,
            price = 150000.0,
            category = "Camera",
            deleted = false,
            isLiked = false
        ),
        Product(
            id = 108,
            name = "Canon EOS R50 V Mirrorless Camera Kit with RF-S14",
            description = "30mm F4-6.3 is STM PZ Lens – Black | 24.2 MP APS-C Sensor, 4K Video, Compact",
            img = "https://m.media-amazon.com/images/I/61J1Go-JNZL._SL1500_.jpg",
            rating = 4,
            stock = 4,
            price = 75500.0,
            category = "Camera",
            deleted = false,
            isLiked = false
        ),
        Product(
            id = 109,
            name = "Sony New Alpha ILCE-6400K (Previously ILCE",
            description = "ILCE-6400L) with Upgraded SELP1650 (Ver 2) Power Zoom Lens | 24.2MP | APS-C Sensor | Fast Auto Focus | 4K Vlogging Camera | Tiltable LCD – Black",
            img = "https://m.media-amazon.com/images/I/816sNzQ+mhL._SL1500_.jpg",
            rating = 5,
            stock = 1,
            price = 71000.0,
            category = "Camera",
            deleted = false,
            isLiked = false
        ),
        
        // Speakers
        Product(
            id = 110,
            name = "Portronics 15W Breeze 7 Wireless Bluetooth Speaker",
            description = "Rotational Volume Control, Upto 6 Hours Playtime, Bluetooth 5.4V, Passive Bass Radiator, TWS Mode, RGB LED Lights, Type C Charging(Black)",
            img = "https://m.media-amazon.com/images/I/71SOUKUelxL._SL1500_.jpg",
            rating = 5,
            stock = 8,
            price = 1200.0,
            category = "Speaker",
            deleted = false,
            isLiked = false
        ),
        Product(
            id = 111,
            name = "Portronics SoundDrum 1 12W",
            description = "TWS Portable Bluetooth Speaker with Powerful Bass, Bluetooth 5.3V, 360° Surround Sound, USB Drive in, Type C Fast Charging(Black)",
            img = "https://m.media-amazon.com/images/I/61CXB7roShL._SL1500_.jpg",
            rating = 4,
            stock = 4,
            price = 700.0,
            category = "Speaker",
            deleted = false,
            isLiked = false
        ),
        Product(
            id = 112,
            name = "Sony SA-D40M2 All in One 4.1ch Home Theatre System",
            description = "100W Power Output and Powerful Subwoofer – Black",
            img = "https://m.media-amazon.com/images/I/81cKrOHbENL._SL1500_.jpg",
            rating = 4,
            stock = 8,
            price = 9000.0,
            category = "Speaker",
            deleted = false,
            isLiked = false
        ),
        Product(
            id = 113,
            name = "ZEBRONICS Twist 5",
            description = "5.1 Home Theatre Speaker, 90 Watts, Bluetooth v5.1 | USB | AUX | FM, 5.25 inch Subwoofer, Powerful Bass, Stylish Design",
            img = "https://m.media-amazon.com/images/I/81h777RrPEL._SL1500_.jpg",
            rating = 4,
            stock = 3,
            price = 3000.0,
            category = "Speaker",
            deleted = false,
            isLiked = false
        ),
        Product(
            id = 114,
            name = "TRONICA Super King 40W 5.1",
            description = "Bluetooth Home Theater System with FM/PenDrive/Sd Card/Mobile/Aux Support & Remote",
            img = "https://m.media-amazon.com/images/I/611j3YK3hEL._SL1491_.jpg",
            rating = 3,
            stock = 2,
            price = 2200.0,
            category = "Speaker",
            deleted = false,
            isLiked = false
        ),
        Product(
            id = 115,
            name = "amazon basics Wired Multimedia Speaker with Remote",
            description = "40W RMS | Bluetooth v5.3 | Aux in, USB, Micro TF Card | Compatible with TVs, Smart Phones, Tablets, PCs (Black)",
            img = "https://m.media-amazon.com/images/I/51cKMI0lYEL._SL1500_.jpg",
            rating = 5,
            stock = 4,
            price = 1900.0,
            category = "Speaker",
            deleted = false,
            isLiked = false
        )
    )
    
    suspend fun getMockProducts(): List<Product> {
        // Simulate network delay
        delay(1000)
        return mockProducts
    }
    
    suspend fun getMockProductsByCategory(category: String): List<Product> {
        delay(500)
        return mockProducts.filter { it.category.equals(category, ignoreCase = true) }
    }
    
    suspend fun searchMockProducts(query: String): List<Product> {
        delay(500)
        return mockProducts.filter { 
            it.name.contains(query, ignoreCase = true) || 
            it.description.contains(query, ignoreCase = true) 
        }
    }
}
