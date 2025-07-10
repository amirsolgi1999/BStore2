package com.example.BStore2.view.categories

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.BStore2.navigation.Screen
import com.example.BStore2.ui.theme.background
import com.example.BStore2.ui.theme.onsec
import com.example.BStore2.ui.theme.sec
import com.example.BStore2.view.CircularProgressIndicator
import com.example.BStore2.view.popularProducts.PopularProductItem
import com.example.BStore2.viewmodel.productViewModel.ProductState
import com.example.BStore2.viewmodel.productViewModel.ProductViewModel

@Composable
fun CategoryScreen (
    viewModel: ProductViewModel = hiltViewModel(),
    navController: NavController,
    category:String,
) {

    val products by viewModel.catProduct.collectAsState()
    viewModel.getProductByCategory(category)

    when(products){
        is ProductState.Loading ->{
           CircularProgressIndicator()
        }
        is ProductState.Success ->{
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(background)
            ) {
                Row(
                    modifier = Modifier.background(Color.White).fillMaxWidth()
                ) {
                    Row(
                        modifier = Modifier
                            .height(56.dp)
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp)
                            .background(Color.White),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            modifier = Modifier.clickable { navController.navigate(Screen.Home.route) },
                            imageVector = Icons.Default.ArrowBackIosNew,
                            contentDescription = ""
                        )
                        Column(
                            modifier = Modifier
                                .clip(RoundedCornerShape(10.dp))
                                .background(sec)
                        ) {
                            Column(
                                modifier = Modifier
                                    .padding(4.dp)
                                    .clip(RoundedCornerShape(10.dp))
                                    .height(35.dp)
                                    .background(onsec),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Center
                            ) {
                                Text(
                                    category,
                                    color = sec,
                                    fontSize = 20.sp,
                                    modifier = Modifier
                                        .padding(horizontal = 8.dp)
                                )
                            }
                        }
                    }
                }
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    modifier = Modifier.padding(start = 16.dp, top = 8.dp, end = 16.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalArrangement = Arrangement.Center
                ) {
                    items((products as ProductState.Success).products) { product ->
                        PopularProductItem(
                            product = product,
                            navController,
                        )
                    }
                }
            }
        }
        else -> {}
    }



}