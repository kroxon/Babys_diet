package com.example.babysdiet.navigation

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.babysdiet.ui.screens.Screen
import com.example.babysdiet.ui.screens.categories.CategoriesScreen
import com.example.babysdiet.ui.screens.home.HomeScreen
import com.example.babysdiet.ui.screens.product.ProductsScreen
import com.example.babysdiet.util.Constants.CATEGORY_ARGUMENT_KEY
import com.example.babysdiet.util.Constants.CATEGORY_ARGUMENT_KEY2
import com.example.babysdiet.util.Constants.PRODUCT_ARGUMENT_KEY

@Composable
fun SetupNavGraph(
    navController: NavHostController
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Home.route
    ) {
        composable(
            route = Screen.Home.route
        ) {
            HomeScreen(navController)
        }
        composable(
            route = Screen.Categories.route
//            ,
//            arguments = listOf(
//                navArgument(CATEGORY_ARGUMENT_KEY) {
//                    type = NavType.IntType
//                },
//                navArgument(CATEGORY_ARGUMENT_KEY2) {
//                    type = NavType.StringType
//                })
        ) {
            CategoriesScreen(navController)
        }
        composable(
            route = Screen.Products.route,
            arguments = listOf(
                navArgument(PRODUCT_ARGUMENT_KEY) {
                    type = NavType.IntType
                })
        ) {
            Log.d("Arguments", it.arguments?.getInt(PRODUCT_ARGUMENT_KEY).toString())
            ProductsScreen(navController)
        }
    }
}