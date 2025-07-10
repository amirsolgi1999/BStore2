package com.example.BStore2.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.EmojiPeople
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.ManageAccounts
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.ShoppingCartCheckout
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemColors
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.BStore2.ui.theme.background
import com.example.BStore2.ui.theme.onsec
import com.example.BStore2.view.Auth.LoginScreen
import com.example.BStore2.view.profile.ProfileScreen
import com.example.BStore2.view.categories.CategoryScreen
import com.example.BStore2.view.home.HomeScreen
import com.example.BStore2.view.newInProducts.NewInProductScreen
import com.example.BStore2.view.popularProducts.PopularScreen
import com.example.BStore2.view.productDetail.ProductDetail
import com.example.BStore2.view.Search.SearchScreen
import com.example.BStore2.view.wishlist.WishlistScreen
import com.example.BStore2.view.cart.CartScreen

@Preview
@Composable
fun BStore(
){

    val navController= rememberNavController()

    val routesWithBottomBar = listOf(
        Screen.Home.route,
        Screen.Search.route,
        Screen.Wishlist.route,
        Screen.Basket.route,
        Screen.Profile.route,
        Screen.PopularProductScreen.route,
        Screen.NewInProductScreen.route,
        Screen.Category.route,
        Screen.Cart.route
    )

    val items = listOf(
        Screen.Home,
        Screen.Search,
        Screen.Wishlist,
        Screen.Cart,
        Screen.Profile
    )


    Scaffold (
        bottomBar = {
            val currentRoute=navController.currentBackStackEntryAsState().value?.destination?.route
            if (currentRoute in routesWithBottomBar){
                NavigationBar (
                    tonalElevation = 4.dp,
                    containerColor = Color.White
                ){
                    items.forEach { screen ->
                        NavigationBarItem(
                            colors = NavigationBarItemColors(
                                selectedIconColor = onsec,
                                selectedTextColor = Color.Black,
                                selectedIndicatorColor = background,
                                unselectedIconColor = Color.Black,
                                unselectedTextColor = Color.Black,
                                disabledIconColor = Color.Black,
                                disabledTextColor = Color.Black
                            ),
                            icon = {
                                Icon(
                                    imageVector = when (screen) {
                                        Screen.Cart -> Icons.Default.ShoppingCartCheckout
                                        Screen.Home -> Icons.Default.Home
                                        Screen.Search -> Icons.Default.Search
                                        Screen.Wishlist -> Icons.Default.Bookmark
                                        Screen.Profile -> Icons.Default.ManageAccounts
                                        else -> Icons.Default.EmojiPeople
                                    },
                                    contentDescription = screen.route
                                )
                            },
                            label = { Text(screen.route) },
                            selected = currentRoute == screen.route,
                            onClick = {
                                navController.navigate(screen.route){
                                    popUpTo(navController.graph.findStartDestination().id){
                                        saveState=true
                                    }
                                    launchSingleTop=true
                                    restoreState=false
                                }
                            }

                        )
                    }
                }
            }
        }
    ){ innerPadding ->
        NavHost(
            navController=navController,
            startDestination = Screen.Home.route,
            modifier = Modifier.padding(innerPadding)
        ){
            composable(Screen.Login.route) {
                LoginScreen(navController)
            }

            composable(Screen.Home.route){
                HomeScreen(navController=navController)
            }

            composable(route = Screen.Cart.route) {
                CartScreen(
                    navController,
                    onProductClick = { productId ->
                        navController.navigate("${Screen.ProductDetails.route}/$productId")
                    }
                )
            }
            composable(Screen.Wishlist.route) {
                WishlistScreen(
                    onProductClick = { productId ->
                        navController.navigate("${Screen.ProductDetails.route}/$productId")
                    },
                    navController = navController
                )
            }
            composable(route = Screen.Search.route) {
                SearchScreen(
                    navController = navController,
                )
            }
            composable(
                route = Screen.Category.route,
                arguments = listOf(navArgument(Screen.Category.CATEGORY_NAME_ARG) {
                    type = NavType.StringType
                })
            ) { backStackEntry ->
                val categoryName = backStackEntry.arguments?.getString(Screen.Category.CATEGORY_NAME_ARG) ?: ""
                CategoryScreen( navController=navController, category = categoryName)
            }
            composable(Screen.PopularProductScreen.route) {
                PopularScreen(navController = navController)
            }
            composable(Screen.NewInProductScreen.route) {
                NewInProductScreen(navController)
            }
            composable(
                route = Screen.ProductDetailsRoute.route,
                arguments = listOf(navArgument(Screen.ProductIdArg.route) {
                    type = NavType.IntType
                }),

                ) { backStackEntry ->
                val productId = backStackEntry.arguments?.getInt(Screen.ProductIdArg.route) ?: 0
                ProductDetail(
                    productId = productId,
                    navController = navController,
                )
            }

            composable(Screen.Profile.route) {
                ProfileScreen(navController = navController)
            }
        }
    }
}
