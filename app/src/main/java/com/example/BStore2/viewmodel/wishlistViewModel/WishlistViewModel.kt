package com.example.BStore2.viewmodel.wishlistViewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.BStore2.data.entities.cart.CartItem
import com.example.BStore2.data.entities.wishlist.Wishlist
import com.example.BStore2.data.repository.repo.CartRepository
import com.example.BStore2.data.repository.repo.WishlistRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class WishlistViewModel @Inject constructor(
    val wishlistRepository: WishlistRepository,
    val cartRepository: CartRepository
):ViewModel(){

    private val _wishlist=MutableStateFlow<WishlistState>(WishlistState.Idle)
    val wishlist:StateFlow<WishlistState> = _wishlist


    init {
        loadWishlist()
    }

    fun loadWishlist(){
        viewModelScope.launch {
            Timber.d(" try to load wishlist products")
            val response=wishlistRepository.loadWishlist()
            _wishlist.value=when{
                response.isSuccess -> WishlistState.Success(response.getOrNull()!!)
                else-> WishlistState.Error(response.exceptionOrNull()?.message?:"Error")
            }
            Timber.d("load wishlist products")
        }
    }

    fun addToCart(item:Wishlist){
        viewModelScope.launch {
            try {
                val isAlreadyInCart=cartRepository.getAllIds().getOrNull()!!.contains(item.id)
                if (!isAlreadyInCart){
                    cartRepository.insertCart(
                        CartItem(item.id,item.title,item.price,item.image)
                    )
                }else{
                    Timber.e("error")
                }
            }catch (e:Exception){
                Timber.e("Failed add to cart",e.message)
            }
        }
    }

    fun removeFromWishlist(id:Int){
        viewModelScope.launch {
            try {
                Timber.d(" try remove from wishlist")
                wishlistRepository.removeFromWishlist(id)
                Timber.d("  remove from wishlist")
                loadWishlist()
            }catch (e:Exception){
                Timber.e("Can't remove from wishlist",e.message)
            }

        }
    }
}