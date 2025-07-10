package com.example.BStore2.data.repository.repo

import com.example.BStore2.data.entities.cart.CartItem

interface CartRepository {

    suspend fun loadCart():Result<List<CartItem>>
    suspend fun getAllIds():Result<List<Int>>
    suspend fun insertCart(cartItem: CartItem)
    suspend fun removeFromCart(id:Int)
    suspend fun clearCart()
}