package com.example.babysdiet.ui.screens.product

import android.annotation.SuppressLint
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import com.example.babysdiet.components.data.models.Product
import com.example.babysdiet.ui.viewmodels.SharedViewModel
import com.example.babysdiet.util.Action
import kotlinx.coroutines.launch
import kotlin.math.absoluteValue

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ProductsScreen(
    navigateToHomeScreen: (Action) -> Unit,
    navigateToCategoryScreen: (categoryId: Int, productId: Int, action: Action) -> Unit,
    navigateToDiaryScreen: (diaryId: Int, productId: Int) -> Unit,
    sharedViewModel: SharedViewModel,
    selectedProductId: Int,
    selectedCategoryId: Int,
    selectedProduct: Product?
) {

    val allDiaries by sharedViewModel.allDiaries.collectAsState()


    val productName: String by sharedViewModel.nameProduct
    val productDescription: String by sharedViewModel.descriptionProduct
    val productIsAllergen: Boolean by sharedViewModel.isAllergenProduct


    Scaffold(
        topBar = {
            ProductAppBar(
                selectedProductId = selectedProductId,
                onAddProductClicked = { action ->
//                    sharedViewModel.action.value = action
                    navigateToCategoryScreen(selectedCategoryId, selectedProductId, Action.ADD_PRODUCT)
                },
                onBackNewProductClicked = { action ->
//                    sharedViewModel.action.value = action
                    navigateToCategoryScreen(selectedCategoryId, selectedProductId, Action.NO_ACTION)
                },
                onBackClicked = { action ->
//                    sharedViewModel.action.value = action
                    if (selectedCategoryId < 0 )
                        navigateToHomeScreen(action)
                    else
                        navigateToCategoryScreen(selectedCategoryId, selectedProductId, Action.NO_ACTION)
                },
                onDeleteClicked = { action ->
//                    sharedViewModel.action.value = action
                    if (selectedCategoryId < 0 )
                        navigateToHomeScreen(action)
                    else
                        navigateToCategoryScreen(selectedCategoryId, selectedProductId, Action.DELETE_PRODUCT)
                },
                onUpdateClicked = { action ->
//                    sharedViewModel.action.value = action
                    if (selectedCategoryId < 0 )
                        navigateToHomeScreen(action)
                    else
                        navigateToCategoryScreen(selectedCategoryId, selectedProductId, Action.UPDATE_PRODUCT)
                }

            )
        },
        content = {
            ProductContent(
                name = productName,
                description = productDescription,
                isAllergen = productIsAllergen,
                productId = selectedProductId,
                categoryId = selectedCategoryId,
                selectedProduct = selectedProduct,
                allDiaries = allDiaries,
                onTitleChange = {sharedViewModel.nameProduct.value = it},
                onDescriptionChange = {sharedViewModel.descriptionProduct.value = it},
                onIsAllergenChange = {sharedViewModel.isAllergenProduct.value = it},
                navigateToDiaryScreen = navigateToDiaryScreen
            )

        }
    )
}



