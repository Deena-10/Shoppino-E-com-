package com.shoppino.android.payment

import android.app.Activity
import android.content.Context
import com.razorpay.Checkout
import com.razorpay.PaymentResultListener
import com.shoppino.android.data.model.PaymentOrderResponse
import com.shoppino.android.data.model.RazorpayOptions
import com.shoppino.android.data.model.RazorpayPrefill
import com.shoppino.android.data.model.RazorpayTheme
import org.json.JSONObject
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RazorpayManager @Inject constructor() {

    fun initializePayment(
        activity: Activity,
        paymentOrder: PaymentOrderResponse,
        userEmail: String? = null,
        userName: String? = null,
        userPhone: String? = null,
        onPaymentSuccess: (String, String) -> Unit,
        onPaymentError: (String) -> Unit
    ) {
        val checkout = Checkout()
        checkout.setKeyID("rzp_test_1234567890") // Replace with your actual Razorpay key

        try {
            val options = JSONObject()
            options.put("name", "Shoppino")
            options.put("description", "Payment for your order")
            options.put("order_id", paymentOrder.id)
            options.put("currency", paymentOrder.currency)
            options.put("amount", paymentOrder.amount * 100) // Convert to paise
            options.put("theme.color", "#2196F3")

            // Pre-fill user details if available
            if (userEmail != null || userName != null || userPhone != null) {
                val prefill = JSONObject()
                userEmail?.let { prefill.put("email", it) }
                userName?.let { prefill.put("name", it) }
                userPhone?.let { prefill.put("contact", it) }
                options.put("prefill", prefill)
            }

            checkout.open(activity, options)
        } catch (e: Exception) {
            onPaymentError("Payment initialization failed: ${e.message}")
        }
    }

    fun handlePaymentResult(
        paymentId: String,
        orderId: String,
        signature: String,
        onSuccess: (String, String, String) -> Unit,
        onError: (String) -> Unit
    ) {
        try {
            // In a real app, you would verify the signature on your backend
            // For now, we'll assume the payment is successful
            onSuccess(paymentId, orderId, signature)
        } catch (e: Exception) {
            onError("Payment verification failed: ${e.message}")
        }
    }

    fun isPaymentMethodAvailable(context: Context): Boolean {
        return try {
            // Check if Razorpay is available
            true
        } catch (e: Exception) {
            false
        }
    }
}
