package com.example.presentation.viewmodel.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.repository.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {
    fun getStockToken() {
        viewModelScope.launch {
            val token = authRepository.getAuthToken()
        }
    }

    fun revokeStockToken() {
        viewModelScope.launch {
            authRepository.revokeToken()
        }
    }
}