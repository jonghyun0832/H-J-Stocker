package com.example.data.repositoryImpl

import com.example.data.BuildConfig
import com.example.data.datasource.local.datasource.AuthLocalDataSource
import com.example.data.datasource.remote.service.StockService
import com.example.data.datasource.remote.service.TokenService
import com.example.data.di.AuthStockService
import com.example.data.di.NoAuthStockService
import com.example.data.model.request.TokenRequest
import com.example.data.model.request.TokenRevokeRequest
import com.example.data.model.response.toDomain
import com.example.domain.model.Token
import com.example.domain.repository.AuthRepository
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    private val authLocalDataSource: AuthLocalDataSource,
    @param:NoAuthStockService private val tokenService: TokenService,
    @param:AuthStockService private val stockService: StockService
): AuthRepository {
    override suspend fun getAuthToken(): Token {
        val tokenResponse = tokenService.getAuthToken(
            request = TokenRequest(
                appKey = BuildConfig.MOCK_APP_KEY,
                secretKey = BuildConfig.MOCK_APP_SECRET
            )
        )

        authLocalDataSource.saveToken(tokenResponse.token)

        // TODO : 리턴 제거
        return tokenResponse.toDomain()
    }

    override suspend fun revokeToken() {
        stockService.revokeAccessToken(
            request = TokenRevokeRequest(
                appKey = BuildConfig.MOCK_APP_KEY,
                secretKey = BuildConfig.MOCK_APP_SECRET,
                token = authLocalDataSource.getToken() ?: ""
            )
        )
    }
}