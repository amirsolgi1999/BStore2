package com.example.BStore2.viewmodel.wishlistViewModel

import com.example.BStore2.data.entities.wishlist.Wishlist

sealed class WishlistState {
    object Idle:WishlistState()
    object Loading:WishlistState()
    data class Success(val products:List<Wishlist?>):WishlistState()
    data class Error(val message:String):WishlistState()
}