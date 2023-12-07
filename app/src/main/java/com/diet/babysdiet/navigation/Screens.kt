package com.diet.babysdiet.navigation

import androidx.navigation.NavHostController
import com.diet.babysdiet.util.Action
import com.diet.babysdiet.util.Constants.HOME_SCREEN


class Screens(navController: NavHostController) {

    val home: (Action) -> Unit = { action ->
        navController.navigate(route = "home_screen/${action.name}") {
            popUpTo(HOME_SCREEN) { inclusive = true }
        }

    }

    val categories: (Int, Int, Action) -> Unit = { categoryId, productId, action ->
        navController.navigate(route = "categories_screen/$categoryId/$productId/${action}")
    }

    val product: (Int, Int) -> Unit = { categoryId, productId ->
        navController.navigate(route = "product_screen/$categoryId/$productId")
    }

    val diary: (Int, Int) -> Unit = { diaryId, productId ->
        navController.navigate(route = "diary_screen/$diaryId/$productId")
    }

}