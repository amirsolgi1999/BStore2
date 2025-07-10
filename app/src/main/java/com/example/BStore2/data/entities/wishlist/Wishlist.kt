package com.example.BStore2.data.entities.wishlist

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "wishlist")
data class Wishlist(
    @PrimaryKey
    val id:Int,
    val title:String,
    val price:Double,
    val image:String
)
