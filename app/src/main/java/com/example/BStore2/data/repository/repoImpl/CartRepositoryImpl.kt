package com.example.BStore2.data.repository.repoImpl

import com.example.BStore2.data.entities.cart.CartDao
import com.example.BStore2.data.entities.cart.CartItem
import com.example.BStore2.data.repository.repo.CartRepository
import javax.inject.Inject

class CartRepositoryImpl @Inject constructor(
    val cartDao: CartDao
) : CartRepository{
    override suspend fun loadCart(): Result<List<CartItem>> {
        return try {
            val response=cartDao.getAllCart()
            Result.success(response)
        }catch (e:Exception){
            Result.failure(e)
        }
    }

    override suspend fun getAllIds(): Result<List<Int>> {
        return try {
            val response=cartDao.getAllIds()
            Result.success(response)
        }catch (e:Exception){
            Result.failure(e)
        }
    }

    override suspend fun insertCart(cartItem: CartItem) {
        try {
            cartDao.insert(cartItem)
        }catch (e:Exception){
            println("Error")
        }
    }

    override suspend fun removeFromCart(id: Int) {
        try {
            cartDao.delete(id)
        }catch (e:Exception){
            println("Error")
        }
    }

    override suspend fun clearCart() {
        try {
            cartDao.clear()
        }catch (e:Exception){
            println("error")
        }
    }
}