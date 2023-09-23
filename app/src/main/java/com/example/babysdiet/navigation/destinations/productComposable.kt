package com.example.babysdiet.navigation.destinations

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.babysdiet.ui.screens.categories.CategoriesScreen
import com.example.babysdiet.ui.screens.home.HomeScreen
import com.example.babysdiet.ui.screens.product.ProductsScreen
import com.example.babysdiet.ui.viewmodels.SharedViewModel
import com.example.babysdiet.util.Action
import com.example.babysdiet.util.Constants
import com.example.babysdiet.util.Constants.CATEGORY_ARGUMENT_KEY
import com.example.babysdiet.util.Constants.CATEGORY_SCREEN
import com.example.babysdiet.util.Constants.HOME_ARGUMENT_KEY
import com.example.babysdiet.util.Constants.HOME_SCREEN
import com.example.babysdiet.util.Constants.PRODUCT_SCREEN


fun NavGraphBuilder.productComposable(
    navigateToHomeScreen: (Action) -> Unit,
    navigateToCategoryScreen: (categoryId: Int, productId: Int) -> Unit,
    sharedViewModel: SharedViewModel
) {
    composable(
        route = PRODUCT_SCREEN,
        arguments = listOf(
            navArgument(Constants.PRODUCT_ARGUMENT_KEY) {
                type = NavType.IntType
            },
            navArgument(Constants.PRODUCT_ARGUMENT_KEY_2) {
                type = NavType.IntType
            },
        )
    ) { navBackStackEntry ->
        val categoryId = navBackStackEntry.arguments!!.getInt(Constants.PRODUCT_ARGUMENT_KEY)
        val productId = navBackStackEntry.arguments!!.getInt(Constants.PRODUCT_ARGUMENT_KEY_2)

        ProductsScreen(
            navigateToHomeScreen = navigateToHomeScreen,
            navigateToCategoryScreen = navigateToCategoryScreen,
            sharedViewModel = sharedViewModel,
            selectedProductId = productId,
            selectedCategoryId = categoryId
        )
    }
}

