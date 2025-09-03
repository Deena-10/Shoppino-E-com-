# 🗄️ Database Connection Setup Guide

## 📍 **Where to Configure Database Connection**

### **Primary Configuration File:**

```
app/src/main/java/com/shoppino/android/config/DatabaseConfig.kt
```

### **What to Change:**

```kotlin
// 🔧 **CHANGE THIS LINE** in DatabaseConfig.kt
const val API_BASE_URL = "http://10.0.2.2:8080/api/"
```

## 🌐 **URL Configuration Options**

### **1. For Android Emulator (Default)**

```kotlin
const val API_BASE_URL = "http://10.0.2.2:8080/api/"
```

- `10.0.2.2` is a special IP that Android emulator uses to connect to your PC's localhost
- Port `8080` is the default Spring Boot port

### **2. For Physical Android Device**

```kotlin
const val API_BASE_URL = "http://192.168.1.100:8080/api/"
```

- Replace `192.168.1.100` with your PC's actual IP address
- Find your IP: `ipconfig` (Windows) or `ifconfig` (Mac/Linux)

### **3. For Testing on Same Machine**

```kotlin
const val API_BASE_URL = "http://localhost:8080/api/"
```

### **4. For Production Server**

```kotlin
const val API_BASE_URL = "https://yourdomain.com/api/"
```

## 🔧 **Step-by-Step Setup**

### **Step 1: Configure Your Spring Boot Backend**

1. Ensure your Spring Boot app is running on port 8080
2. Verify MySQL is connected and running
3. Test your API endpoints (e.g., `http://localhost:8080/api/products`)

### **Step 2: Update DatabaseConfig.kt**

1. Open `app/src/main/java/com/shoppino/android/config/DatabaseConfig.kt`
2. Change the `API_BASE_URL` to match your setup
3. Save the file

### **Step 3: Test Connection**

1. Build and run the Android app
2. Check logcat for network requests
3. Verify data is loading from your MySQL database

## 🚨 **Common Issues & Solutions**

### **Issue: "Connection refused"**

- **Solution**: Check if Spring Boot is running
- **Solution**: Verify port number in URL

### **Issue: "Network on main thread"**

- **Solution**: Already handled with coroutines in the app

### **Issue: "Cannot resolve host"**

- **Solution**: Check IP address spelling
- **Solution**: Ensure device and PC are on same network

### **Issue: "Timeout"**

- **Solution**: Increase timeout values in DatabaseConfig.kt
- **Solution**: Check firewall settings

## 📱 **Testing Your Connection**

### **1. Check Logs**

Look for these in Android Studio's Logcat:

```
D/OkHttp: --> GET http://your-ip:8080/api/products
D/OkHttp: <-- 200 OK http://your-ip:8080/api/products
```

### **2. Test API Endpoints**

Use Postman or browser to test:

- `GET http://localhost:8080/api/products`
- `GET http://localhost:8080/api/auth/login`

### **3. Verify Database**

Check MySQL directly:

```sql
USE shoppino_db;
SELECT * FROM products;
```

## 🔄 **Quick Configuration Examples**

### **Local Development (Emulator)**

```kotlin
const val API_BASE_URL = "http://10.0.2.2:8080/api/"
```

### **Local Development (Physical Device)**

```kotlin
const val API_BASE_URL = "http://192.168.1.50:8080/api/"
```

### **Production**

```kotlin
const val API_BASE_URL = "https://api.shoppino.com/api/"
```

## 📞 **Need Help?**

1. **Check Spring Boot logs** for backend errors
2. **Check Android Logcat** for network errors
3. **Verify MySQL connection** in Spring Boot
4. **Test API endpoints** with Postman first
5. **Check network permissions** in AndroidManifest.xml

---

**Remember**: The Android app connects to your Spring Boot backend, which then connects to MySQL. You don't connect directly to MySQL from Android!
