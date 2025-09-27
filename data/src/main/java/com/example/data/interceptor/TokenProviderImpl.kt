package com.example.data.interceptor

import com.example.data.datasource.local.datasource.AuthLocalDataSource
import kotlinx.coroutines.runBlocking
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TokenProviderImpl @Inject constructor(
    private val authLocalDataSource: AuthLocalDataSource,
) : TokenProvider {
    override fun getTokenBlocking(): String? {
        return runBlocking {
            authLocalDataSource.getToken()
        }
    }
}