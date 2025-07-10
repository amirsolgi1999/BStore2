package com.example.BStore2.data.repository.repoImpl

import com.example.BStore2.data.entities.user.UserResponse
import com.example.BStore2.data.remote.StoreApiService
import com.example.BStore2.data.repository.repo.AuthRepository
import javax.inject.Inject

class AuthRepositoryImpl @Inject constructor(
    val apiService: StoreApiService
) :AuthRepository{
    override suspend fun getUsers(username:String,password:String): Result<UserResponse> {
        return try {
            val response=apiService.getUsers()
            Result.success(response)
        }catch (e:Exception){
            Result.failure(e)
        }
    }
}