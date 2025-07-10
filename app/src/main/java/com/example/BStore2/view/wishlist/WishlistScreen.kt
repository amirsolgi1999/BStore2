package com.example.BStore2.view.wishlist
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.BStore2.navigation.Screen
import com.example.BStore2.ui.theme.background
import com.example.BStore2.ui.theme.onsec
import com.example.BStore2.view.CircularProgressIndicator
import com.example.BStore2.viewmodel.wishlistViewModel.WishlistState
import com.example.BStore2.viewmodel.wishlistViewModel.WishlistViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WishlistScreen(
    navController: NavController,
    onProductClick: (Int) -> Unit = {},
    wishlistViewModel: WishlistViewModel= hiltViewModel(),
) {
    val wishlist by wishlistViewModel.wishlist.collectAsState()

    when {
        wishlist is WishlistState.Loading ->{
            CircularProgressIndicator()
        }
        wishlist is WishlistState.Success ->{
            if ((wishlist as WishlistState.Success).products.isEmpty()) {
                Column(
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Text("Your wishlist is empty!")
                    Spacer(modifier = Modifier.height(8.dp))
                    Button(
                        onClick = { navController.navigate(Screen.PopularProductScreen.route) },
                        colors = ButtonDefaults.buttonColors(containerColor = onsec),

                        ) {
                        Text("Add Product")
                    }
                }
            }
            else{
                Column (
                    modifier = Modifier
                        .fillMaxSize()
                        .background(background)
                ){

                    Row (
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp)
                            .background(Color.White)
                    ){
                        Text(
                            text = "Wishlist",
                            fontSize = 22.sp,
                            fontWeight = FontWeight.Medium,
                            modifier = Modifier.padding(16.dp)
                        )

                    }
                    LazyVerticalGrid (
                        GridCells.Fixed(3),
                        modifier = Modifier
                            .padding(start = 16.dp, end = 16.dp, top = 8.dp)
                    ) {
                        items((wishlist as WishlistState.Success).products.size) { index ->
                            (wishlist as WishlistState.Success).products[index]?.let {
                                WishlistItemView(
                                    item = it,
                                    onRemove = { wishlistViewModel.removeFromWishlist((wishlist as WishlistState.Success).products[index]!!.id) },
                                    onClickAdd ={wishlistViewModel.addToCart((wishlist as WishlistState.Success).products[index]!!)},
                                    onItem = {onProductClick((wishlist as WishlistState.Success).products[index]!!.id)}
                                )
                            }
                        }

                    }
                }
            }
        }
        }



}


