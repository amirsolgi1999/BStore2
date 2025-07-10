package com.example.BStore2.viewmodel.authViewModel

sealed class AuthState {
    object Idle:AuthState()
    object Loading:AuthState()
    data class Success(val auth: Boolean):AuthState()
    data class Error(val message:String):AuthState()
}