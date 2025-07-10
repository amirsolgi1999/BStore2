package com.example.BStore2.view.home

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ShoppingCartCheckout
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.BStore2.R
import com.example.BStore2.navigation.Screen
import com.example.BStore2.data.entities.product.Product
import com.example.BStore2.ui.theme.background
import com.example.BStore2.view.CircularProgressIndicator
import com.example.BStore2.view.Search.SearchResult
import com.example.BStore2.viewmodel.productViewModel.ProductState
import com.example.BStore2.viewmodel.productViewModel.ProductViewModel


@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun HomeScreen(
    viewModel: ProductViewModel= hiltViewModel(),
    navController: NavController,
    ){

    val popularProducts by viewModel.popularProducts.collectAsState()
    val newProduct by viewModel.newInProducts.collectAsState()
    val searchQuery by viewModel.searchProduct
    val filterProducts by viewModel.filteredPopularProducts
    val filterNewProducts by viewModel.filteredNewProducts




    when {
        popularProducts is ProductState.Success && newProduct is ProductState.Success -> {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .background(background)
            ) {
                TopBarA(
                    iconStart = null,
                    textStart = "B Store",
                    iconEnd = Icons.Default.ShoppingCartCheckout,
                    onClickStart = { navController.popBackStack() },
                    onClickEnd = { navController.navigate(Screen.Cart.route) }
                )

                SearchBar(
                    query = searchQuery,
                    onQueryChange = viewModel::onSearchQueryChange
                )
                if (searchQuery.isNotEmpty()) {
                    SearchResult(
                        products = filterProducts,
                        newProducts = filterNewProducts,
                        navController
                    )
                } else {
                    MainContent(
                        products = (popularProducts as ProductState.Success).products,
                        newProducts = (newProduct as ProductState.Success).products,
                        navController
                    )
                }
            }
        }
        else -> {
            Column(
                modifier = Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                when {
                    popularProducts is ProductState.Error || newProduct is ProductState.Error -> {
                        Text("Error in loading products")
                    }
                    popularProducts is ProductState.Loading || newProduct is ProductState.Loading -> {
                        CircularProgressIndicator()
                    }
                    else -> {
                        Text("Waiting for product...")
                    }
                }
            }
        }
    }
}

@Composable
fun MainContent(
    products:List<Product>,
    newProducts:List<Product>,
    navController: NavController,
) {

    Column {

        Box(
            modifier = Modifier
                .clickable {   navController.navigate(Screen.PopularProductScreen.route) }
                .padding(horizontal = 16.dp)
                .height(210.dp)
                .fillMaxWidth()
        ) {
            AsyncImage(
                modifier = Modifier
                    .clip(RoundedCornerShape(15.dp))
                    .fillMaxSize()
                    .aspectRatio(1f),
                model = R.drawable.frame,
                contentDescription = "ss"
            )
        }
        Categories(navController = navController)

        ProductSection(
            title = "Popular",
            products = products,
            navController = navController,
            destination = Screen.PopularProductScreen.route
        )
        ProductSection(
            title = "New In",
            products = newProducts,
            navController = navController,
            destination = Screen.NewInProductScreen.route
        )
    }
}










