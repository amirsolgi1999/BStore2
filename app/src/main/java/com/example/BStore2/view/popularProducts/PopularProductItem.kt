package com.example.BStore2.view.popularProducts

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.BStore2.navigation.Screen
import com.example.BStore2.data.entities.product.Product
import com.example.BStore2.ui.theme.onsec

@Composable
fun PopularProductItem(
    product: Product,
    navController: NavController,
    ){

    Column  (
        modifier = Modifier
            .clickable { navController.navigate("${Screen.ProductDetails.route}/${product.id}") }
        .padding(start = 4.dp, end = 4.dp, bottom = 8.dp)
    ){
        Column(
            modifier = Modifier
                .clip(RoundedCornerShape(topStart = 15.dp, topEnd = 15.dp))
                .height(150.dp)
                .fillMaxWidth()
                .background(Color.White),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            AsyncImage(
                model = product.image,
                contentDescription = "Product Image",
                modifier = Modifier.size(120.dp).aspectRatio(1f)
            )
        }

        Row (
            modifier = Modifier
                .clip(RoundedCornerShape(bottomEnd = 15.dp, bottomStart = 15.dp))
                .height(50.dp)
                .fillMaxWidth()
                .background(Color.White)
        ){ Row (
            modifier = Modifier.fillMaxSize().padding(horizontal = 4.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ){
            Text(text = "${product.title.substring(0,21)}...", fontSize = 10.sp, color = Color.Black, maxLines = 2)
            Column (
                modifier = Modifier
                    .size(50.dp,20.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(onsec),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ){
                Text(text = "${ product.price.toString() +"$"}", fontSize = 10.sp, color = Color.White)

            }
        }
        }
    }
}