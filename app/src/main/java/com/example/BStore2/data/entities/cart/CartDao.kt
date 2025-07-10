package com.example.BStore2.data.entities.cart

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface CartDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(item: CartItem)

    @Query("DELETE FROM cart")
    suspend fun clear()

    @Query("DELETE FROM cart WHERE id=:id")
    suspend fun delete(id:Int)

    @Query("SELECT * FROM cart")
    suspend fun getAllCart():List<CartItem>

    @Query("SELECT id FROM cart ")
    suspend fun getAllIds():List<Int>
}