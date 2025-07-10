package com.example.BStore2.view.cart

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.BStore2.navigation.Screen
import com.example.BStore2.ui.theme.background
import com.example.BStore2.ui.theme.onsec
import com.example.BStore2.viewmodel.CartViewModel.CartState
import com.example.BStore2.viewmodel.CartViewModel.CartViewModel


@Composable
fun CartScreen(
    navController: NavController,
    viewmodel:CartViewModel= hiltViewModel(),
    onProductClick: (Int) -> Unit = {},
){

    var showDialog by remember { mutableStateOf(false) }
    val cartList by viewmodel.cart.collectAsState()
    val context= LocalContext.current


    when{


        cartList is CartState.Loading ->{
            Column (
                modifier = Modifier.fillMaxSize()
            ){
                CircularProgressIndicator()
            }
        }

        cartList is CartState.Success ->{
            if ((cartList as CartState.Success).products.isEmpty()){
                Column (
                    modifier = Modifier.fillMaxSize(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ){
                    Text("Your basket is empty!")
                    Spacer(modifier = Modifier.height(8.dp))
                    Button(
                        onClick = {navController.navigate(Screen.PopularProductScreen.route)},
                        colors = ButtonDefaults.buttonColors(containerColor = onsec),

                        ) {
                        Text("Add Product")
                    }
                }
            }
            else{
                Box (
                    modifier = Modifier
                        .fillMaxSize()
                        .background(background),
                    contentAlignment = Alignment.TopCenter
                ){

                    Column {
                        Row (
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(56.dp)
                                .background(Color.White)
                        ){
                            Text(
                                text = "Basket",
                                fontSize = 22.sp,
                                fontWeight = FontWeight.Medium,
                                modifier = Modifier.padding(16.dp)
                            )

                        }
                        LazyColumn(
                            modifier = Modifier
                                .padding(16.dp)
                        ) {
                            items((cartList as CartState.Success).products.size) { index ->
                                CartItemView (
                                    item = (cartList as CartState.Success).products[index]!!,
                                    onRemove = { viewmodel.removeFromCart((cartList as CartState.Success).products[index]!!.id) },
                                    onClick = {onProductClick((cartList as CartState.Success).products[index]!!.id)}
                                )
                            }
                        }
                    }
                    Button(
                        colors = ButtonDefaults.buttonColors(containerColor = onsec),
                        modifier = Modifier
                            .height(56.dp)
                            .padding(8.dp)
                            .fillMaxWidth()
                            .align(Alignment.BottomEnd)
                            .clip(RoundedCornerShape(10.dp)),
                        onClick = {
                            if ((cartList as CartState.Success).products.isNotEmpty() ){
                                showDialog = true
                            }else{
                                Toast.makeText(context,"add a product!",Toast.LENGTH_LONG).show()

                            }
                        }

                    ) {
                        Text("Pay")
                    }

                    if (showDialog) {
                        AlertDialog(
                            onDismissRequest = { showDialog = false },
                            title = { Text("Logout") },
                            text = { Text("Do you want to pay?") },
                            confirmButton = {
                                Button(
                                    colors = ButtonDefaults.buttonColors(containerColor = onsec),
                                    onClick = {
                                        viewmodel.clearCart()
                                        showDialog = false
                                        navController.navigate(Screen.Cart.route)
                                    }) { Text("pay") }
                            },
                            dismissButton = {
                                Button(
                                    colors = ButtonDefaults.buttonColors(containerColor = onsec),
                                    onClick = {
                                        showDialog = false

                                    }) { Text("Keep Data") }
                            }
                        )
                    }
                }
            }
        }
    }


}

