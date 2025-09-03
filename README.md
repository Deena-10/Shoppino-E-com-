# Shoppino - Android E-commerce App

A mini E-commerce Android application built with Java and Kotlin, designed to integrate with an existing Spring Boot backend.

## 🚀 Features

- **Product Management**: Browse, search, and view products by category
- **Shopping Cart**: Add/remove items, manage quantities
- **User Authentication**: Login, registration, Google OAuth, JWT tokens
- **Order Management**: Place orders, view order history
- **Offline Support**: Local database with Room persistence
- **Modern UI**: Material Design components with custom color scheme

## 🛠️ Tech Stack

- **Language**: Java & Kotlin
- **Architecture**: MVVM with Repository pattern
- **Local Database**: Room Persistence Library
- **Networking**: Retrofit + OkHttp
- **Image Loading**: Glide
- **Authentication**: JWT, Google OAuth, OTP verification
- **UI Components**: Material Design, Navigation Component, ViewPager2

## 📱 Screenshots

*Screenshots will be added here*

## 🔧 Setup & Installation

### Prerequisites

- Android Studio Arctic Fox or later
- Android SDK 24+
- Java 17 or later
- Gradle 8.10.2+

### Backend Requirements

- Spring Boot application running on `localhost:8080`
- MySQL database named "ecomm"
- JWT authentication enabled
- Google OAuth configured

### Installation Steps

1. **Clone the repository**
   ```bash
   git clone https://github.com/Deena-10/Shoppino-E-com-.git
   cd Shoppino
   ```

2. **Configure backend connection**
   - Update `app/src/main/java/com/shoppino/android/config/DatabaseConfig.kt`
   - Set `API_BASE_URL` to your Spring Boot backend URL
   - For emulator: `http://10.0.2.2:8080/api/`
   - For device: `http://YOUR_LOCAL_IP:8080/api/`

3. **Configure Google OAuth** (optional)
   - Add your Google OAuth client ID to the project
   - Update Google Services configuration

4. **Build and Run**
   - Open project in Android Studio
   - Sync project with Gradle files
   - Run on emulator or device

## 🗄️ Database Schema

The app integrates with the following MySQL tables:

- **users**: User accounts and authentication
- **products**: Product catalog with images and details
- **cart_items**: Shopping cart contents
- **orders**: Order information and status
- **order_items**: Individual items within orders

## 🔐 Security Features

- JWT token-based authentication
- Secure API communication with HTTPS
- OAuth2 integration for Google sign-in
- OTP verification for password reset
- Role-based access control (USER/ADMIN)

## 📁 Project Structure

```
app/
├── src/main/
│   ├── java/com/shoppino/android/
│   │   ├── config/          # Configuration files
│   │   ├── data/            # Data models and DAOs
│   │   ├── network/         # API service and networking
│   │   ├── repository/      # Data repositories
│   │   ├── ui/              # UI components and fragments
│   │   ├── utils/           # Utility classes
│   │   └── MainActivity.kt  # Main activity
│   ├── res/                 # Resources (layouts, drawables, etc.)
│   └── AndroidManifest.xml  # App manifest
├── build.gradle             # App-level build configuration
└── proguard-rules.pro       # ProGuard rules
```

## 🌐 API Endpoints

The app communicates with the following backend endpoints:

- **Authentication**: `/api/auth/login`, `/api/auth/register`
- **Products**: `/api/products`, `/api/products/{id}`
- **Cart**: `/api/cart`, `/api/cart/{productId}`
- **Orders**: `/api/orders/place`, `/api/orders/my`
- **User Profile**: `/api/user/profile`

## 🎨 UI/UX Features

- **Color Scheme**: Matches your React JS website design
- **Material Design**: Modern Android UI components
- **Responsive Layout**: Adapts to different screen sizes
- **Smooth Navigation**: Fragment-based navigation with animations
- **Dark/Light Theme**: Follows system theme preferences

## 🚀 Running the App

1. **Start your Spring Boot backend**
2. **Launch Android Studio**
3. **Open the project**
4. **Sync Gradle files**
5. **Run on emulator or device**

## 📝 Contributing

1. Fork the repository
2. Create a feature branch
3. Make your changes
4. Test thoroughly
5. Submit a pull request

## 📄 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.

## 🤝 Support

If you encounter any issues:

1. Check the [Issues](https://github.com/Deena-10/Shoppino-E-com-/issues) page
2. Create a new issue with detailed information
3. Include device/emulator details and error logs

## 🔗 Related Projects

- **Frontend**: React JS E-commerce website
- **Backend**: Spring Boot REST API
- **Database**: MySQL with "ecomm" schema

---

**Built with ❤️ using Java, Kotlin, and Android Studio**
