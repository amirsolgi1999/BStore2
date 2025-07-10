package com.example.BStore2.data.entities.wishlist

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface WishlistDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(item:Wishlist)

    @Query("SELECT * FROM wishlist")
    suspend fun getAllWish():List<Wishlist>

    @Query("DELETE FROM wishlist WHERE id=:id")
    suspend fun delete(id:Int)

    @Query("DELETE FROM wishlist")
    suspend fun clear()

    @Query("SELECT id FROM wishlist")
    suspend fun getAllIds():List<Int>
}