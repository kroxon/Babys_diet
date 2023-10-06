package com.example.babysdiet.navigation.destinations

import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.babysdiet.ui.screens.product.ProductsScreen
import com.example.babysdiet.ui.viewmodels.SharedViewModel
import com.example.babysdiet.util.Action
import com.example.babysdiet.util.Constants
import com.example.babysdiet.util.Constants.PRODUCT_SCREEN
import kotlin.math.absoluteValue


fun NavGraphBuilder.productComposable(
    navigateToHomeScreen: (Action) -> Unit,
    navigateToCategoryScreen: (categoryId: Int, productId: Int, action: Action) -> Unit,
    navigateToDiaryScreen: (diaryId: Int, productId: Int) -> Unit,
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

        LaunchedEffect(key1 = productId) {
            sharedViewModel.getSelectedProduct(productId = productId)
        }
        val selectedProduct by sharedViewModel.selectedProduct.collectAsState()

//        LaunchedEffect(key1 = productId) {
//            sharedViewModel.getSelectedProduct(productId = productId)
//        }

//        var isCodeExecuted by remember { mutableStateOf(false) }
//        if (!isCodeExecuted) {
//            sharedViewModel.getSelectedProduct(productId = productId)
//            isCodeExecuted = true
//        }
//        val selectedProduct by remember { sharedViewModel.selectedProduct }.collectAsState()

        LaunchedEffect(key1 = selectedProduct) {
            if ((selectedProduct != null) || productId == -1)
                sharedViewModel.updateProductFields(
                    selectedProduct = selectedProduct,
                    categoryId = categoryId.absoluteValue
                )
        }

        ProductsScreen(
            navigateToHomeScreen = navigateToHomeScreen,
            navigateToCategoryScreen = navigateToCategoryScreen,
            navigateToDiaryScreen = navigateToDiaryScreen,
            sharedViewModel = sharedViewModel,
            selectedProductId = productId,
            selectedCategoryId = categoryId,
            selectedProduct = selectedProduct
        )
    }
}

