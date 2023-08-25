package com.example.babysdiet.navigation

import androidx.navigation.NavHostController
import com.example.babysdiet.util.Action
import com.example.babysdiet.util.Constants.HOME_SCREEN


class Screens(navController: NavHostController) {

    val home: (Action) -> Unit = { action ->
        navController.navigate(route = "home_screen/${action.name}") {
            popUpTo(HOME_SCREEN) { inclusive = true }
        }

    }

    val categories: (Int) -> Unit = { categoryId ->
        navController.navigate(route = "categories_screen/$categoryId")
    }

//    val product: (Int) -> Unit = { productId ->
//        navController.navigate(route = "product_screen/$productId")
//    }

    val diary: (Int) -> Unit = { diaryId ->
        navController.navigate(route = "diary_screen/$diaryId")
    }

}