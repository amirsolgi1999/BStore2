package com.example.BStore2.view.profile
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.BStore2.navigation.Screen
import com.example.BStore2.ui.theme.background
import com.example.BStore2.ui.theme.onsec
import com.example.BStore2.viewmodel.CartViewModel.CartViewModel
import com.example.BStore2.viewmodel.authViewModel.AuthViewModel


@Composable
fun ProfileScreen(
    loginViewModel: AuthViewModel= hiltViewModel(),
    cartViewModel: CartViewModel= hiltViewModel(),
    navController: NavController

) {

    var showDialog by remember { mutableStateOf(false) }

    Column (
        modifier = Modifier
            .fillMaxSize()
            .background(background),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Text("User Profile")
        Button(
            colors = ButtonDefaults.buttonColors(containerColor = onsec),
            onClick = { showDialog = true }
        ) {
            Text("Logout")
        }

    }

    if (showDialog) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text("Logout") },
            text = { Text("Do you want to clear wishlist and cart?") },
            confirmButton = {
                Button(
                    colors = ButtonDefaults.buttonColors(containerColor = onsec),
                    onClick = {
                        cartViewModel.clearCart()
                        loginViewModel.logout()
                        showDialog = false
                        loginViewModel.logout()
                        navController.navigate(Screen.Login.route)

                    }
                ) {
                    Text("Clear cart and Logout")
                }
            },
            dismissButton = {
                Button(
                    colors = ButtonDefaults.buttonColors(containerColor = onsec),
                    onClick = {
                        loginViewModel.logout()
                        showDialog = false
                        navController.navigate(Screen.Login.route)
                    }
                ) {
                    Text("Keep Data")
                }
            }
        )
    }
}