package com.shoppino.android.ui.catalog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.shoppino.android.databinding.FragmentCatalogBinding

class CatalogFragment : Fragment() {
    
    private var _binding: FragmentCatalogBinding? = null
    private val binding get() = _binding!!
    
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCatalogBinding.inflate(inflater, container, false)
        return binding.root
    }
    
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUI()
        setupSearch()
        loadProducts()
    }
    
    private fun setupUI() {
        binding.rvProducts.layoutManager = GridLayoutManager(context, 2)
        // Setup product adapter
    }
    
    private fun setupSearch() {
        binding.searchView.setOnQueryTextListener(object : android.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                searchProducts(query)
                return true
            }
            
            override fun onQueryTextChange(newText: String?): Boolean {
                // Implement real-time search if needed
                return true
            }
        })
    }
    
    private fun searchProducts(query: String?) {
        // Implement product search
    }
    
    private fun loadProducts() {
        // Load products from API
    }
    
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
