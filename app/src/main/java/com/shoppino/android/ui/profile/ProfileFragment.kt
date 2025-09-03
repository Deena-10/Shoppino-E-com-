package com.shoppino.android.ui.profile

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.shoppino.android.databinding.FragmentProfileBinding

class ProfileFragment : Fragment() {
    
    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }
    
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupUI()
        checkLoginStatus()
    }
    
    private fun setupUI() {
        binding.btnLogin.setOnClickListener {
            showLoginDialog()
        }
        
        binding.btnRegister.setOnClickListener {
            showRegisterDialog()
        }
        
        binding.btnLogout.setOnClickListener {
            logout()
        }
        
        binding.btnEditProfile.setOnClickListener {
            editProfile()
        }
    }
    
    private fun checkLoginStatus() {
        // Check if user is logged in
        val isLoggedIn = false // Get from SharedPreferences or local storage
        if (isLoggedIn) {
            showProfileView()
        } else {
            showLoginView()
        }
    }
    
    private fun showLoginView() {
        binding.loginLayout.visibility = View.VISIBLE
        binding.profileLayout.visibility = View.GONE
    }
    
    private fun showProfileView() {
        binding.loginLayout.visibility = View.GONE
        binding.profileLayout.visibility = View.VISIBLE
        loadUserProfile()
    }
    
    private fun showLoginDialog() {
        // Show login dialog
    }
    
    private fun showRegisterDialog() {
        // Show register dialog
    }
    
    private fun loadUserProfile() {
        // Load user profile from API
    }
    
    private fun editProfile() {
        // Navigate to edit profile
    }
    
    private fun logout() {
        // Clear user session and show login view
        showLoginView()
    }
    
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
