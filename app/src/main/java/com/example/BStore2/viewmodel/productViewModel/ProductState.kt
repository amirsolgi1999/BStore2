package com.example.BStore2.viewmodel.productViewModel

import com.example.BStore2.data.entities.product.Product

sealed class ProductState {
    object Idle:ProductState()
    object Loading:ProductState()
    data class Success(val products:List<Product>):ProductState()
    data class Error(val message:String):ProductState()
}