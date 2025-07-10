package com.example.BStore2.data.remote

import com.example.BStore2.data.entities.product.ProductResponse
import com.example.BStore2.data.entities.user.UserResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface StoreApiService {

    @GET("products")
    suspend fun getPopularProducts(): ProductResponse

    @GET("products")
    suspend fun getNewInProducts(
        @Query("page") page:Int=2
    ): ProductResponse

    @GET("products/category")
    suspend fun getWithCategory(
        @Query("type") category:String
    ): ProductResponse

    @GET("users")
    suspend fun getUsers(): UserResponse
}