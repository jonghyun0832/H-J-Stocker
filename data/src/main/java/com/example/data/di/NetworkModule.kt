package com.example.data.di

import com.example.data.datasource.local.datasource.AuthLocalDataSource
import com.example.data.datasource.remote.service.StockService
import com.example.data.datasource.remote.service.TokenService
import com.example.data.interceptor.StockInterceptor
import com.example.data.interceptor.TokenProvider
import com.example.data.interceptor.TokenProviderImpl
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton
import kotlin.apply
import kotlin.jvm.java

@Module
@InstallIn(SingletonComponent::class)
internal object NetworkModule {
    private const val BASE_URL_STOCK = "https://api.kiwoom.com"
    private const val MOCK_BASE_URL_STOCK = "https://mockapi.kiwoom.com"

    private const val CONNECT_TIMEOUT = 30L
    private const val WRITE_TIMEOUT = 30L
    private const val READ_TIMEOUT = 30L

    @Provides
    @Singleton
    fun provideGson(): Gson = GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create()

    @Provides
    @Singleton
    fun provideHttpLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
    }

    @Provides
    @Singleton
    fun provideTokenProvider(
        authLocalDataSource: AuthLocalDataSource
    ): TokenProvider = TokenProviderImpl(authLocalDataSource)

    @Provides
    @Singleton
    fun provideStockInterceptor(
        tokenProvider: TokenProvider
    ): StockInterceptor {
        return StockInterceptor(tokenProvider)
    }

    @Provides
    @Singleton
    @NoAuthStockOkHttpClient
    fun provideNoAuthStockOkHttpClient(
        loggingInterceptor: HttpLoggingInterceptor
    ): OkHttpClient {
        return OkHttpClient.Builder().apply {
            connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
            writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS)
            readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
            addInterceptor(loggingInterceptor)
        }.build()
//        if (BuildConfig.DEBUG) {
//            builder.addInterceptor(interceptor)
//        }
        // TODO : Debug인 경우에만 interceptor 추가
    }

    @Provides
    @Singleton
    @AuthStockOkHttpClient
    fun provideAuthStockOkHttpClient(
        loggingInterceptor: HttpLoggingInterceptor,
        stockInterceptor: StockInterceptor
    ): OkHttpClient {
        return OkHttpClient.Builder().apply {
            connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
            writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS)
            readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
            addInterceptor(stockInterceptor)
            addInterceptor(loggingInterceptor)
        }.build()
//        if (BuildConfig.DEBUG) {
//            builder.addInterceptor(interceptor)
//        }
        // TODO : Debug인 경우에만 interceptor 추가
    }

    @Provides
    @Singleton
    @NoAuthStockRetrofit
    fun provideNoAuthStockRetrofit(
        gson: Gson,
        @NoAuthStockOkHttpClient okHttpClient: OkHttpClient
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(MOCK_BASE_URL_STOCK)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(okHttpClient)
            .build()
    }

    @Provides
    @Singleton
    @AuthStockRetrofit
    fun provideAuthStockRetrofit(
        gson: Gson,
        @AuthStockOkHttpClient okHttpClient: OkHttpClient
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(MOCK_BASE_URL_STOCK)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .client(okHttpClient)
            .build()
    }

    @Provides
    @Singleton
    @NoAuthStockService
    fun provideTokenService(@NoAuthStockRetrofit retrofit: Retrofit): TokenService {
        return retrofit.create(TokenService::class.java)
    }

    @Provides
    @Singleton
    @AuthStockService
    fun provideStockService(@AuthStockRetrofit retrofit: Retrofit): StockService {
        return retrofit.create(StockService::class.java)
    }
}