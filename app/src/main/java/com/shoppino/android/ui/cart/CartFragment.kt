package com.shoppino.android.ui.cart

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.shoppino.android.databinding.FragmentCartBinding

class CartFragment : Fragment() {
    
    private var _binding: FragmentCartBinding? = null
    private val binding get() = _binding!!
    
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCartBinding.inflate(inflater, container, false)
        return binding.root
    }
    
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUI()
        loadCartItems()
        setupCheckoutButton()
    }
    
    private fun setupUI() {
        binding.rvCartItems.layoutManager = LinearLayoutManager(context)
    }
    
    private fun loadCartItems() {
        updateTotalPrice()
    }
    
    private fun updateTotalPrice() {
        val total = calculateTotal()
        binding.tvTotalPrice.text = "$${String.format("%.2f", total)}"
    }
    
    private fun calculateTotal(): Double {
        return 0.0
    }
    
    private fun setupCheckoutButton() {
        binding.btnCheckout.setOnClickListener {
            navigateToCheckout()
        }
    }
    
    private fun navigateToCheckout() {
    }
    
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
