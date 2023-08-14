package com.example.babysdiet.navigation

import androidx.navigation.NavHostController


class Screens(navController: NavHostController) {

    val home: () -> Unit = {
        navController.navigate("categories_screen")
    }

    val categories: (Int) -> Unit = { categoryId ->
        navController.navigate("product_screen/$categoryId")
    }

}