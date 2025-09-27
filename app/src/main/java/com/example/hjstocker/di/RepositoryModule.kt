package com.example.hjstocker.di

import com.example.data.repositoryImpl.AuthRepositoryImpl
import com.example.domain.repository.AuthRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {
    @Binds
    @Singleton
    fun bindChartRepository(chartRepositoryImpl: AuthRepositoryImpl): AuthRepository
}