package com.example.BStore2.viewmodel.CartViewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.BStore2.data.repository.repo.CartRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(
    val cartRepository: CartRepository
):ViewModel() {

    private val _cart= MutableStateFlow<CartState>(CartState.Idle)
    val cart:StateFlow<CartState> = _cart

    init {
        loadCart()
    }

    fun loadCart(){

        viewModelScope.launch {
            Timber.d(" try to load cart products")
            val response=cartRepository.loadCart()
            _cart.value=when{
                response.isSuccess ->CartState.Success(response.getOrNull()!!)
                else -> CartState.Error(response.exceptionOrNull()?.message?:"Error")
            }
            Timber.d("load cart products")

        }
    }

    fun removeFromCart(id:Int){
        viewModelScope.launch {
            try {
                Timber.d(" try remove from cart")
                cartRepository.removeFromCart(id)
                Timber.d(" remove from cart")
                loadCart()
            }catch (e:Exception){
                Timber.e("remove from cart",e.message)
            }
        }
    }

    fun clearCart(){
        viewModelScope.launch {
            try {
                cartRepository.clearCart()
                Timber.d(" clear cart")

            }catch (e:Exception){
                Timber.e(e.message,"cant clear cart!")

            }
        }
    }
}