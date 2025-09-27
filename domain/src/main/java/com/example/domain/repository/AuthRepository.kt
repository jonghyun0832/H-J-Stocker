package com.example.domain.repository

import com.example.domain.model.Token

interface AuthRepository {
    suspend fun getAuthToken(): Token
    suspend fun revokeToken()
}