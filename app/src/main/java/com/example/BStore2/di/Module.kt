package com.example.BStore2.di

import android.content.Context
import androidx.room.Room
import com.example.BStore2.data.remote.RetrofitInstance.BASE_URL
import com.example.BStore2.data.remote.StoreApiService
import com.example.BStore2.data.entities.cart.CartDao
import com.example.BStore2.data.entities.wishlist.WishlistDao
import com.example.BStore2.data.local.StoreAppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object Module {

    @Provides
    @Singleton
    fun provideRetrofit():Retrofit{
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideStoreApiService(retrofit: Retrofit): StoreApiService {
        return retrofit.create(StoreApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideContext1(@ApplicationContext context: Context): Context = context

    @Provides
    @Singleton
    fun provideContext(application: android.app.Application): Context {
        return application.applicationContext
    }

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context):StoreAppDatabase{
        return Room.databaseBuilder(
            context = context,
            StoreAppDatabase::class.java,
            "store_database"
        ).build()
    }

    @Provides
    @Singleton
    fun provideCartDao(appDatabase: StoreAppDatabase):CartDao{
        return appDatabase.cartDao()
    }

    @Provides
    @Singleton
    fun provideWishlistDao(appDatabase: StoreAppDatabase):WishlistDao{
        return appDatabase.wishDao()
    }


}