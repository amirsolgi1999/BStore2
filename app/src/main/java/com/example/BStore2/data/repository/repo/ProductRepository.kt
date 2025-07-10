package com.example.BStore2.data.repository.repo

import com.example.BStore2.data.entities.product.Product
import com.example.BStore2.data.entities.product.ProductResponse

interface ProductRepository {

    suspend fun getPopularProduct():Result<ProductResponse>
    suspend fun getNewInProduct():Result<ProductResponse>
    suspend fun getOtherProduct():Result<ProductResponse>
    suspend fun getProductByCategory(category:String):Result<List<Product>>
}