package com.example.babysdiet.navigation

import androidx.navigation.NavHostController


class Screens(navController: NavHostController) {

    val home: (Int) -> Unit = { category ->
        navController.navigate("categories/$category")
    }

    val categories: (Int) -> Unit = { taskId ->
        navController.navigate("task/$taskId")
    }

}