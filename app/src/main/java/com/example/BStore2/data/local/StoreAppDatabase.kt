package com.example.BStore2.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.BStore2.data.entities.cart.CartDao
import com.example.BStore2.data.entities.cart.CartItem
import com.example.BStore2.data.entities.wishlist.Wishlist
import com.example.BStore2.data.entities.wishlist.WishlistDao

@Database(
    entities = [CartItem::class,
               Wishlist::class],
    version = 1
)
abstract class StoreAppDatabase : RoomDatabase(){

    abstract fun cartDao():CartDao
    abstract fun wishDao():WishlistDao
}