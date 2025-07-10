package com.example.BStore2.view.productDetail

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.BStore2.view.CircularProgressIndicator
import com.example.BStore2.viewmodel.productViewModel.ProductState
import com.example.BStore2.viewmodel.productViewModel.ProductViewModel

@Composable
fun ProductDetail(
    productViewModel: ProductViewModel= hiltViewModel(),
    productId:Int,
    navController: NavController,
){



    val otherProduct by productViewModel.getOtherProductByWCId(productId).collectAsState()
    val newInProduct by productViewModel.getNewInProductByIdWC(productId).collectAsState()
    val popularProduct by productViewModel.getPopularProductByIdWC(productId).collectAsState(null)
    val popularProducts by productViewModel.popularProducts.collectAsState()
    val newInProducts by productViewModel.newInProducts.collectAsState()
    val otherProducts by productViewModel.otherProducts.collectAsState()

    Column {
            if (popularProducts is ProductState.Loading || newInProducts is ProductState.Loading){
                    CircularProgressIndicator()
            }
            if(popularProducts is ProductState.Success && (popularProducts as ProductState.Success).products.contains(popularProduct)){

                popularProduct?.let {
                    ProductScreen(
                        product = it,
                        navController = navController,
                        productId = productId
                    )
                }
            }

            else if (otherProducts is ProductState.Success && (otherProducts as ProductState.Success).products.contains(otherProduct)){

                otherProduct?.let {
                    ProductScreen(
                        product = it,
                        navController = navController,
                        productId = productId
                    )
                }

            }
            else if (newInProducts is ProductState.Success && (newInProducts as ProductState.Success).products.contains(newInProduct)){
                newInProduct?.let {
                    ProductScreen(
                        product = it,
                        navController = navController,
                        productId = productId
                    )
                }
            }

        }
}