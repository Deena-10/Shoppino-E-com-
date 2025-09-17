package com.shoppino.android.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

data class PaymentOrderRequest(
    val amount: Int,
    val currency: String = "INR",
    val receipt: String
)

data class PaymentOrderResponse(
    val id: String,
    val amount: Int,
    val currency: String,
    val receipt: String,
    val status: String,
    val created_at: Long
)

data class PaymentVerificationRequest(
    val razorpay_order_id: String,
    val razorpay_payment_id: String,
    val razorpay_signature: String
)

data class PaymentVerificationResponse(
    val success: Boolean,
    val message: String,
    val orderId: String? = null
)

@Parcelize
data class PaymentState(
    val isLoading: Boolean = false,
    val orderId: String? = null,
    val paymentId: String? = null,
    val amount: Int = 0,
    val currency: String = "INR",
    val error: String? = null,
    val isPaymentCompleted: Boolean = false
) : Parcelable

data class RazorpayOptions(
    val key: String,
    val amount: Int,
    val currency: String,
    val name: String,
    val description: String,
    val order_id: String,
    val prefill: RazorpayPrefill? = null,
    val theme: RazorpayTheme? = null
)

data class RazorpayPrefill(
    val name: String? = null,
    val email: String? = null,
    val contact: String? = null
)

data class RazorpayTheme(
    val color: String = "#2196F3"
)
