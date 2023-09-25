package com.example.babysdiet.ui.screens.product

import android.annotation.SuppressLint
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import com.example.babysdiet.ui.viewmodels.SharedViewModel
import com.example.babysdiet.util.Action

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ProductsScreen(
    navigateToHomeScreen: (Action) -> Unit,
    navigateToCategoryScreen: (categoryId: Int, productId: Int) -> Unit,
    sharedViewModel: SharedViewModel,
    selectedProductId: Int,
    selectedCategoryId: Int
) {

    Scaffold(
        topBar = {
            ProductAppBar(
                selectedProductId = selectedProductId,
                onAddProductClicked = {},
                onBackNewProductClicked = {},
                onBackClicked = { action ->
                    sharedViewModel.action.value = action
                    if (selectedCategoryId == -1)
                        navigateToHomeScreen(action)
                    else
                        navigateToCategoryScreen(selectedCategoryId, selectedProductId)
                },
                onDeleteClicked = { action ->
                    if (selectedCategoryId == -1)
                        navigateToHomeScreen(action)
                    else
                        navigateToCategoryScreen(selectedCategoryId, selectedProductId)
                },
                onUpdateClicked = {}

            )
        },
        content = {
            ProductContent(
            )

        }
    )
}

