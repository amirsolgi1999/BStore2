package com.example.BStore2.viewmodel.productViewModel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.BStore2.data.entities.cart.CartItem
import com.example.BStore2.data.entities.product.Product
import com.example.BStore2.data.entities.wishlist.Wishlist
import com.example.BStore2.data.repository.repo.CartRepository
import com.example.BStore2.data.repository.repo.ProductRepository
import com.example.BStore2.data.repository.repo.WishlistRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class ProductViewModel @Inject constructor(
    val productRepository: ProductRepository,
    val wishlistRepository: WishlistRepository,
    val cartRepository: CartRepository
) :ViewModel(){

    private val _popularProducts= MutableStateFlow<ProductState>(ProductState.Idle)
    val popularProducts : StateFlow<ProductState> = _popularProducts

    private val _newInProducts= MutableStateFlow<ProductState>(ProductState.Idle)
    val newInProducts : StateFlow<ProductState> = _newInProducts

    private val _otherProducts= MutableStateFlow<ProductState>(ProductState.Idle)
    val otherProducts : StateFlow<ProductState> = _otherProducts

    private val _selectedCategories = mutableStateOf<Set<String>>(emptySet())
    val selectedCategories : State<Set<String>> = _selectedCategories

    private val _catProduct= MutableStateFlow<ProductState>(ProductState.Idle)
    val catProduct:StateFlow<ProductState> = _catProduct

    private val _searchProduct = mutableStateOf("")
    val searchProduct: State<String> = _searchProduct

    private val _filteredPopularProducts = mutableStateOf<List<Product>>(emptyList())
    val filteredPopularProducts: State<List<Product>> = _filteredPopularProducts

    private val _filteredNewProducts = mutableStateOf<List<Product>>(emptyList())
    val filteredNewProducts: State<List<Product>> = _filteredNewProducts

    private val _wishlistIds=MutableStateFlow<Set<Int>>(emptySet())
    val wishlistIds:StateFlow<Set<Int>> = _wishlistIds

    private val _cartIds=MutableStateFlow<Set<Int>>(emptySet())
    val cartIds:StateFlow<Set<Int>> = _cartIds


    init {
        getPopularProducts()
        getOtherProducts()
        getNewInProducts()
        loadWishlistAndCartIds()
    }

    fun getPopularProducts(){
        viewModelScope.launch {
            Timber.d(" try to load popular products")
            val response=productRepository.getPopularProduct()
            _popularProducts.value=when{
                response.isSuccess -> ProductState.Success(response.getOrNull()!!.products)
                else -> ProductState.Error(response.exceptionOrNull()?.message?:"Error")
            }
            Timber.d(" get to load popular products")

        }
    }

    fun getNewInProducts(){
        viewModelScope.launch {
            Timber.d(" try to load new in products")
            val response=productRepository.getNewInProduct()
            _newInProducts.value=when{
                response.isSuccess -> ProductState.Success(response.getOrNull()!!.products)
                else -> ProductState.Error(response.exceptionOrNull()?.message?:"Error")
            }
            Timber.d(" get to load new in products")
        }
    }

    fun getOtherProducts(){
        viewModelScope.launch {
            Timber.d(" try to load other products")
            val response=productRepository.getOtherProduct()
            _otherProducts.value=when{
                response.isSuccess -> ProductState.Success(response.getOrNull()!!.products)
                else -> ProductState.Error(response.exceptionOrNull()?.message?:"Error")
            }
            Timber.d(" get to load other products")

        }
    }


    fun getProductByCategory(category:String){
        viewModelScope.launch {
            Timber.d(" try product by category: ${category}")
            val response=productRepository.getProductByCategory(category)
            Timber.d(" get product by category: ${category}")
            _catProduct.value=when{
                response.isSuccess -> ProductState.Success(response.getOrNull()!!)
                else -> ProductState.Error(response.exceptionOrNull()?.message?:"Error")
            }
        }
    }


    fun loadProductsByCategories(categories:Set<String>){
        viewModelScope.launch {
            if (categories.isEmpty()){
                getPopularProducts()
                Timber.d(" is empty ${categories}")
            }else{
                val allProducts= mutableListOf<Product>()
                categories.forEach { category ->
                    Timber.d(" get  Categories ${category}")
                    val products=productRepository.getProductByCategory(category)
                    allProducts.addAll(products.getOrNull()!!)
                }
                _popularProducts.value=ProductState.Success(allProducts.distinctBy { it.id })
                _selectedCategories.value=categories
                Timber.d(" get  ${categories}")
            }
        }
    }

    fun updatedCategories(categories:Set<String>){
        Timber.d(" try Updated Categories ${categories}")
        _selectedCategories.value=categories
        loadProductsByCategories(categories)
        Timber.d(" get Updated Categories ${categories}")
    }

    fun searchProduct(){
        val query=_searchProduct.value

        viewModelScope.launch {
            try {
                _filteredPopularProducts.value=when(val state=_popularProducts.value){
                    is  ProductState.Success ->{

                        if (query.isEmpty()){
                            state.products
                        }else{
                            state.products.filter {
                                it.title.contains(query, ignoreCase = true)
                            }.also { Timber.d("Filtered popular products count: ${it.size}") }
                        }

                    }else ->{
                        emptyList()
                    }
                }

                _filteredNewProducts.value=when(val state=_newInProducts.value){
                    is  ProductState.Success ->{

                        if (query.isEmpty()){
                            state.products
                        }else{
                            state.products.filter {
                                it.title.contains(query, ignoreCase = true)
                            }.also { Timber.d("Filtered popular products count: ${it.size}") }
                        }

                    }else ->{
                        Timber.d("No new products available")
                        emptyList()
                    }
                }
            }catch (e:Exception){
                Timber.e(e, "Error searching products")

            }
        }
    }


    fun onSearchQueryChange(query: String) {
        Timber.d("Search query changed to", query)
        _searchProduct.value = query
        searchProduct()
    }

    fun loadWishlistAndCartIds() {
        viewModelScope.launch {
            Timber.d("Loading wishlist and cart IDs...")
            try {
                _wishlistIds.value = wishlistRepository.getAllIds().getOrNull()!!.toSet()
                _cartIds.value = cartRepository.getAllIds().getOrNull()!!.toSet()
                Timber.d("Loaded  wishlist IDs and  cart IDs", _wishlistIds.value.size, _cartIds.value.size)
            } catch (e: Exception) {
                Timber.e(e, "Failed to load wishlist or cart IDs")
            }
        }
    }


    fun addToWishlist(product: Product) {
        viewModelScope.launch {
            Timber.d("Adding product  to wishlist", product.title)
            try {
                val isAlreadyInWishlist = wishlistRepository.getAllIds().getOrNull()!!.contains(product.id)
                if (!isAlreadyInWishlist) {
                    wishlistRepository.insertWish(Wishlist(product.id,product.title,product.price,product.image))

                    loadWishlistAndCartIds()
                    Timber.d("Product  added to wishlist", product.title)
                } else {
                    Timber.w("Product  already in wishlist", product.title)
                }
            } catch (e: Exception) {
                Timber.e(e, "Failed to add product to wishlist", product.title)
            }
        }
    }


    fun addToCart(product: Product) {
        viewModelScope.launch {
            Timber.d("Adding product  to cart", product.title)
            try {
                val isAlreadyInCart = _cartIds.value.contains(product.id)
                if (!isAlreadyInCart) {
                    cartRepository.insertCart(
                        CartItem(product.id, product.title, product.price, product.image)
                    )
                    loadWishlistAndCartIds()
                    Timber.d("Product  added to cart", product.title)
                } else {
                    Timber.w("Product  already in cart", product.title)
                }
            } catch (e: Exception) {
                Timber.e(e, "Failed to add product to cart", product.title)
            }
        }
    }




    fun getPopularProductByIdWC(productId: Int): StateFlow<Product?> {
        Timber.d("Getting popular product by ID", productId)
        return _popularProducts.map { state ->
            when (state) {
                is ProductState.Success -> {
                    Timber.d("Available products: ${state.products.map { it.id }}")
                    state.products.find { it.id == productId }
                }
                else -> {
                    Timber.d("State is not Success: $state")
                    null
                }
            }
        }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), null)
    }


    fun getNewInProductByIdWC(productId: Int): StateFlow<Product?> {
        Timber.d("Getting new in product by ID", productId)
        return _newInProducts.map { state ->
            when (state) {
                is ProductState.Success -> {
                    Timber.d("Available products: ${state.products.map { it.id }}")
                    state.products.find { it.id == productId }
                }
                else -> {
                    Timber.d("State is not Success: $state")
                    null
                }
            }
        }.stateIn(viewModelScope, SharingStarted.Lazily, null)
    }


    fun getOtherProductByWCId(productId: Int):StateFlow<Product?>{
        Timber.d("Getting other product by ID", productId)
        return _otherProducts.map { state ->
            when (state) {
                is ProductState.Success -> {
                    Timber.d("Available products: ${state.products.map { it.id }}")
                    state.products.find { it.id == productId }
                }
                else -> {
                    Timber.d("State is not Success: $state")
                    null
                }
            }
        }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), null)
    }


}