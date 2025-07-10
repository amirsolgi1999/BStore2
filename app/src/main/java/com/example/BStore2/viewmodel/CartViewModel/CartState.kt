package com.example.BStore2.viewmodel.CartViewModel

import com.example.BStore2.data.entities.cart.CartItem

sealed class CartState {
    object Idle:CartState()
    object Loading:CartState()
    data class Success(val products:List<CartItem?>):CartState()
    data class Error(val message:String):CartState()
}