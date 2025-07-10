package com.example.BStore2.data.repository.repoImpl

import com.example.BStore2.data.entities.wishlist.Wishlist
import com.example.BStore2.data.entities.wishlist.WishlistDao
import com.example.BStore2.data.repository.repo.WishlistRepository
import javax.inject.Inject

class WishlistRepositoryImpl @Inject constructor(
    val wishlistDao: WishlistDao
) :WishlistRepository{
    override suspend fun loadWishlist(): Result<List<Wishlist>> {
        return try {
            val response=wishlistDao.getAllWish()
            Result.success(response)
        }catch (e:Exception){
            Result.failure(e)
        }
    }

    override suspend fun insertWish(wishlist: Wishlist) {
        try {
            wishlistDao.insert(wishlist)
        }catch (e:Exception){
            println("Error insert wish")
        }
    }

    override suspend fun removeFromWishlist(id: Int) {
        try {
            wishlistDao.delete(id)
        }catch (e:Exception){
            println("error delete wish")
        }
    }

    override suspend fun getAllIds(): Result<List<Int>> {
        return try {
            val response=wishlistDao.getAllIds()
            Result.success(response)
        }catch (e:Exception){
            Result.failure(e)
        }
    }

    override suspend fun clearWishlist() {
        try {
            wishlistDao.clear()
        }catch (e:Exception){
            println("Error to clear wishlist")
        }
    }
}