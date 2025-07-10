package com.example.BStore2.data.repository.repo

import com.example.BStore2.data.entities.wishlist.Wishlist

interface WishlistRepository {

    suspend fun loadWishlist():Result<List<Wishlist>>
    suspend fun insertWish(wishlist: Wishlist)
    suspend fun removeFromWishlist(id:Int)
    suspend fun getAllIds():Result<List<Int>>
    suspend fun clearWishlist()
}