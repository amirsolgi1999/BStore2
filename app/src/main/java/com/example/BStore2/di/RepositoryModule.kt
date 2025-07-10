package com.example.BStore2.di

import com.example.BStore2.data.repository.repo.AuthRepository
import com.example.BStore2.data.repository.repo.CartRepository
import com.example.BStore2.data.repository.repo.ProductRepository
import com.example.BStore2.data.repository.repo.WishlistRepository
import com.example.BStore2.data.repository.repoImpl.AuthRepositoryImpl
import com.example.BStore2.data.repository.repoImpl.CartRepositoryImpl
import com.example.BStore2.data.repository.repoImpl.ProductRepositoryImpl
import com.example.BStore2.data.repository.repoImpl.WishlistRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindProductRepository(
        productRepositoryImpl: ProductRepositoryImpl
    ):ProductRepository

    @Binds
    @Singleton
    abstract fun bindCartRepository(
        cartRepoImpl: CartRepositoryImpl
    ):CartRepository

    @Binds
    @Singleton
    abstract fun bindWishlistRepository(
        wishlistRepositoryImpl: WishlistRepositoryImpl
    ):WishlistRepository

    @Binds
    @Singleton
    abstract fun bindAuthRepository(
        authRepositoryImpl: AuthRepositoryImpl
    ):AuthRepository

}

