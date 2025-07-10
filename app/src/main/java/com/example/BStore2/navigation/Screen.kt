package com.example.BStore2.navigation


sealed class Screen(val route:String){
    object Home: Screen("Home")
    object Search: Screen("Search")
    object Profile: Screen("Profile")
    object Login : Screen("Login")
    object Wishlist: Screen("Wishlist")
    object Basket: Screen("Basket")
    object Cart: Screen("Cart")
    object ProductIdArg: Screen("productId")
    object PopularProductScreen : Screen("PopularProductScreen")
    object NewInProductScreen : Screen("NewInProductScreen")
    object ProductDetails : Screen("product_details")
    object ProductDetailsRoute: Screen("product_details/{productId}")
    object Category: Screen("category/{categoryName}"){
        const val CATEGORY_NAME_ARG="categoryName"
        fun createRoute(categoryName:String)="category/$categoryName"
    }

}
















































/*

sealed class Screen(val route: String) {
    data object Main : Screen("main") {
        const val cityArg = "city"
        val routeWithArgs = "$route?$cityArg={$cityArg}"
        fun createRoute(city: String? = null): String {
            return if (city != null) "$route?$cityArg=$city" else route
        }
    }
}
@Composable
fun WeatherAppNavHost() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = Screen.Main.route) {
        composable(
            route = Screen.Main.routeWithArgs,
            arguments = listOf(navArgument(Screen.Main.cityArg) {
                type = NavType.StringType
                nullable = true
            })
        ) { backStackEntry ->
            val city = backStackEntry.arguments?.getString(Screen.Main.cityArg)
            MainScreenContent(
                navController = navController,
                city = city
            )
        }
        composable(
            route = "hour",
        ) {
            ForecastHourly()
        }

    }
}*/