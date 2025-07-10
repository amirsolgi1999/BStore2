package com.example.BStore2.data.entities.product

data class ProductResponse(
    val status:String,
    val message:String,
    val products:List<Product>
)
