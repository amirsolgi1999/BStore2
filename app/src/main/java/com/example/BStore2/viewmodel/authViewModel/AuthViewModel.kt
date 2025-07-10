package com.example.BStore2.viewmodel.authViewModel

import android.content.Context
import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.BStore2.data.repository.repo.AuthRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    val repository: AuthRepository,
    @ApplicationContext context: Context
):ViewModel() {

    private val _isLoggedIn=MutableStateFlow<AuthState>(AuthState.Idle)
    val isLoggedIn:StateFlow<AuthState> = _isLoggedIn


    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences("app_prefs", Context.MODE_PRIVATE)

    fun login(username:String,password:String){
        viewModelScope.launch {
            Timber.d(" Loading for login")
            val users=repository.getUsers(username,password)
                _isLoggedIn.value=when{
                    users.isSuccess -> AuthState.Success(users.getOrNull()!!.users.any{it.username==username && it.password == password})
                    else -> AuthState.Error(users.exceptionOrNull()?.message?:"Error")
                }
            Timber.d("login")


        }
    }

    fun isLoggedIn():Boolean{
        return sharedPreferences.getBoolean("is_logged_in",false)

    }
    fun logout(){
        sharedPreferences.edit().putBoolean("is_logged_in",false).apply()
        _isLoggedIn.value=AuthState.Success(false)
    }


}