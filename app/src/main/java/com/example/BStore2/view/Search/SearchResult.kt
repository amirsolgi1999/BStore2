package com.example.BStore2.view.Search

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.BStore2.R
import com.example.BStore2.data.entities.product.Product
import com.example.BStore2.view.ProductItem


@Composable
fun SearchResult(
    products: List<Product>,
    newProducts:List<Product>,
    navController: NavController
){
    if (products.isEmpty() && newProducts.isEmpty()){
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            contentAlignment = Alignment.Center
        ){
            Column (
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ){
                AsyncImage(
                    model = R.drawable.dd,
                    contentDescription = "",
                    modifier = Modifier.size(200.dp)
                )

                Text("Sorry This product is not available!")
                Text("Search for other product!")
            }
        }
    }else{
        LazyRow (
            modifier = Modifier.padding(start = 16.dp, top = 8.dp, end = 8.dp)
        ){
            items(products){product ->
                ProductItem(product = product,navController)
                Spacer(modifier = Modifier.width(8.dp))
            }
            items(newProducts){newProduct->
                ProductItem(newProduct,navController)
                Spacer(modifier = Modifier.width(8.dp))

            }
        }
    }
}
