package com.example.data.datasource.local.datasource

interface AuthLocalDataSource {
    suspend fun saveToken(token: String)
    suspend fun getToken(): String?
    suspend fun clearToken()
}