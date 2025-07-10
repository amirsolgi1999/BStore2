package com.example.BStore2.data.repository.repoImpl

import com.example.BStore2.data.remote.StoreApiService
import com.example.BStore2.data.entities.product.Product
import com.example.BStore2.data.entities.product.ProductResponse
import com.example.BStore2.data.repository.repo.ProductRepository
import javax.inject.Inject

class ProductRepositoryImpl @Inject constructor(
    val apiService: StoreApiService
):ProductRepository{
    override suspend fun getPopularProduct(): Result<ProductResponse> {
        return try {
            val response=apiService.getPopularProducts()
            Result.success(response)
        }catch (e:Exception){
            Result.failure(e)
        }
    }

    override suspend fun getNewInProduct(): Result<ProductResponse> {
        return try {
            val response=apiService.getNewInProducts()
            Result.success(response)
        }catch (e:Exception){
            Result.failure(e)
        }
    }

    override suspend fun getOtherProduct(): Result<ProductResponse> {
        return try {
            val response=apiService.getNewInProducts(3)
            Result.success(response)
        }catch (e:Exception){
            Result.failure(e)
        }
    }

    override suspend fun getProductByCategory(category: String): Result<List<Product>> {
        return try {
            val response=apiService.getWithCategory(category).products
            Result.success(response)
        }catch (e:Exception){
            Result.failure(e)
        }
    }

}