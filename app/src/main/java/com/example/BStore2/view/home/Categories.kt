package com.example.BStore2.view.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.BStore2.R
import com.example.BStore2.navigation.Screen
import com.example.BStore2.data.entities.Category
import com.example.BStore2.ui.theme.onsec
import com.example.BStore2.ui.theme.sec

@Composable
fun Categories(
    navController: NavController,
) {

    Text(
        modifier = Modifier.padding(vertical = 8.dp, horizontal = 16.dp),
        text = "Category",
        fontSize = 18.sp,
        fontWeight = FontWeight.Medium
    )

    val categories = listOf(
        Category(R.drawable.tv, "Tv", "tv"),
        Category(R.drawable.audio, "Audio", "audio"),
        Category(R.drawable.console, "Console", "gaming"),
        Category(R.drawable.laptop, "Laptop", "laptop"),
        Category(R.drawable.mobile, "Mobile", "mobile"),
        Category(R.drawable.appliance, "Utensil", "appliances")
    )

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp, horizontal = 16.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        categories.forEach { category ->
            CategoryItem(
                icon = category.icon,
                text = category.text,
                navController = navController,
                textA = category.route
            )
        }
    }
}

@Composable
fun CategoryItem(
    icon: Int,
    text: String,
    navController: NavController,
    textA: String
) {
    val circleModifier = Modifier
        .padding(start = 12.dp)
        .size(50.dp)
        .clip(CircleShape)
        .background(sec)
        .clickable {
            navController.navigate(Screen.Category.createRoute(textA)) {
                popUpTo(Screen.Home.route) { inclusive = false }
                launchSingleTop = true
            }
        }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Column(
            modifier = circleModifier,
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            androidx.compose.material.Icon(
                modifier = Modifier.size(25.dp),
                painter = painterResource(icon),
                tint = onsec,
                contentDescription = text
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = text, modifier = Modifier.padding(start = 10.dp))
    }
}


