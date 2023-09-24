package com.example.babysdiet.navigation.destinations

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.babysdiet.ui.screens.categories.CategoriesScreen
import com.example.babysdiet.ui.screens.home.HomeScreen
import com.example.babysdiet.ui.viewmodels.SharedViewModel
import com.example.babysdiet.util.Action
import com.example.babysdiet.util.Constants
import com.example.babysdiet.util.Constants.CATEGORY_ARGUMENT_KEY
import com.example.babysdiet.util.Constants.CATEGORY_SCREEN
import com.example.babysdiet.util.Constants.HOME_ARGUMENT_KEY
import com.example.babysdiet.util.Constants.HOME_SCREEN


fun NavGraphBuilder.categoriesComposable(
    navigateToHomeScreen: (Action) -> Unit,
    navigateToProductScreen: (productId: Int) -> Unit,
    sharedViewModel: SharedViewModel
) {
    composable(
        route = CATEGORY_SCREEN,
        arguments = listOf(
            navArgument(Constants.CATEGORY_ARGUMENT_KEY) {
                type = NavType.IntType
            },
            navArgument(Constants.CATEGORY_ARGUMENT_KEY_2) {
                type = NavType.IntType
            },
        )
    ) { navBackStackEntry ->
        val categoryId = navBackStackEntry.arguments!!.getInt(Constants.CATEGORY_ARGUMENT_KEY)
        val productId = navBackStackEntry.arguments!!.getInt(Constants.CATEGORY_ARGUMENT_KEY_2)

        CategoriesScreen(
            navigateToHomeScreen = navigateToHomeScreen,
            sharedViewModel = sharedViewModel,
            selectedProductId = productId,
            selectedCategoryId = categoryId,
            navigateToProductScreen = navigateToProductScreen
        )
    }
}

