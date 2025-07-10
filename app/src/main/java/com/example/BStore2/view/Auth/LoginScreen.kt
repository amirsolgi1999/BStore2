package com.example.BStore2.view.Auth
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.BStore2.navigation.Screen
import com.example.BStore2.ui.theme.background
import com.example.BStore2.ui.theme.onsec
import com.example.BStore2.viewmodel.authViewModel.AuthState
import com.example.BStore2.viewmodel.authViewModel.AuthViewModel

@Composable
fun LoginScreen(
    navController: NavController,
    viewModel: AuthViewModel= hiltViewModel()
) {

    val isLoggedIn by viewModel.isLoggedIn.collectAsState()
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }


    LaunchedEffect(isLoggedIn) {
        when (isLoggedIn) {
            is AuthState.Success -> {
                if ((isLoggedIn as AuthState.Success).auth) {
                    navController.navigate(Screen.Home.route) {
                        popUpTo(Screen.Login.route) { inclusive = true }
                    }
                }
            }

            else -> {

            }
        }
    }

    Column (
        modifier = Modifier
            .fillMaxSize()
            .background(background)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ){

        TextField(
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White,
                disabledContainerColor = Color.White

            ),
            value = username,
            onValueChange = {username=it},
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp)
                .clip(RoundedCornerShape(15.dp)),
            placeholder = {
                Row (
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ){

                    Text("Username")
                }
            }
        )

        Spacer(modifier = Modifier.height(8.dp))
        TextField(
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White,
                disabledContainerColor = Color.White

            ),
            value = password,
            onValueChange = {password=it},
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 8.dp)
                .clip(RoundedCornerShape(15.dp)),
            placeholder = {
                Row (
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ){

                    Text("Password")
                }
            }
        )
        Spacer(modifier = Modifier.height(8.dp))

        Button(
            colors = ButtonDefaults.buttonColors(containerColor = onsec),
            onClick = {viewModel.login(username,password)}
        ) {
            Text("Login")
        }

    }
    LaunchedEffect(isLoggedIn) {
        when (isLoggedIn) {
            is AuthState.Success -> {
                if ((isLoggedIn as AuthState.Success).auth) {
                    navController.navigate(Screen.Home.route) {
                    }
                }
            }
            else -> {
            }
        }
    }



}