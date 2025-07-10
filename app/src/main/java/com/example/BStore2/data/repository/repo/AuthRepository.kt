package com.example.BStore2.data.repository.repo

import com.example.BStore2.data.entities.user.UserResponse

interface AuthRepository {
    suspend fun getUsers(username:String,password:String):Result<UserResponse>
}