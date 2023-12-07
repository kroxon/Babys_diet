package com.diet.babysdiet.navigation.destinations

import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.diet.babysdiet.ui.screens.categories.CategoriesScreen
import com.diet.babysdiet.ui.viewmodels.SharedViewModel
import com.diet.babysdiet.util.Action
import com.diet.babysdiet.util.Constants.CATEGORY_ARGUMENT_KEY
import com.diet.babysdiet.util.Constants.CATEGORY_ARGUMENT_KEY_2
import com.diet.babysdiet.util.Constants.CATEGORY_ARGUMENT_KEY_3
import com.diet.babysdiet.util.Constants.CATEGORY_SCREEN
import com.diet.babysdiet.util.toAction


fun NavGraphBuilder.categoriesComposable(
    navigateToHomeScreen: (Action) -> Unit,
    navigateToProductScreen: (categoryId: Int, productId: Int) -> Unit,
    sharedViewModel: SharedViewModel
) {
    composable(
        route = CATEGORY_SCREEN,
        arguments = listOf(
            navArgument(CATEGORY_ARGUMENT_KEY) {
                type = NavType.IntType
            },
            navArgument(CATEGORY_ARGUMENT_KEY_2) {
                type = NavType.IntType
            },
            navArgument(CATEGORY_ARGUMENT_KEY_3) {
                type = NavType.StringType
            }
        )
    ) { navBackStackEntry ->
        val categoryId = navBackStackEntry.arguments!!.getInt(CATEGORY_ARGUMENT_KEY)
        val productId = navBackStackEntry.arguments!!.getInt(CATEGORY_ARGUMENT_KEY_2)
        val action = navBackStackEntry.arguments?.getString(CATEGORY_ARGUMENT_KEY_3).toAction()

        LaunchedEffect(key1 = action){
            sharedViewModel.action.value = action
        }

        CategoriesScreen(
            navigateToHomeScreen = navigateToHomeScreen,
            sharedViewModel = sharedViewModel,
            selectedProductId = productId,
            selectedCategoryId = categoryId,
            navigateToProductScreen = navigateToProductScreen
        )
    }
}


