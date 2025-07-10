package com.example.BStore2.view.wishlist

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddShoppingCart
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.BStore2.data.entities.wishlist.Wishlist
import com.example.BStore2.ui.theme.onsec
import com.example.BStore2.ui.theme.sec


@Composable
fun WishlistItemView(
    item: Wishlist,
    onRemove: () -> Unit,
    onClickAdd: () ->Unit,
    onItem:() -> Unit,
) {


    Column (
        modifier = Modifier
            .clickable {onItem()}
            .padding(4.dp)
            .clip(RoundedCornerShape(15.dp))
            .height(230.dp)
            .fillMaxWidth()
            .background(Color.White),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ){
        AsyncImage(
            model = item.image,
            contentDescription = "",
            modifier = Modifier
                .padding(6.dp)
                .height(95.dp)
                .fillMaxWidth()
        )

        Column (modifier = Modifier.padding(4.dp), horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center){
            Text(
                item.title.substring(0,12), maxLines = 1
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(item.price.toString())

        }
        Divider(modifier = Modifier.fillMaxWidth(), color = sec)

        Row (modifier = Modifier.weight(1f),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ){

            Icon(
                modifier = Modifier.weight(0.5f)
                   .clickable { onClickAdd() }
                ,
                imageVector = Icons.Default.AddShoppingCart,
                contentDescription = "",
                tint = onsec
            )

            Icon(
                modifier = Modifier.weight(0.5f).padding(end = 2.dp).clickable { onRemove() },
                imageVector = Icons.Default.Delete,
                contentDescription = ""
            )
        }
    }
}