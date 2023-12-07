package com.diet.babysdiet.ui.screens.product

import android.annotation.SuppressLint
import androidx.activity.OnBackPressedCallback
import androidx.activity.OnBackPressedDispatcher
import androidx.activity.compose.LocalOnBackPressedDispatcherOwner
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import com.diet.babysdiet.components.data.models.Product
import com.diet.babysdiet.ui.viewmodels.SharedViewModel
import com.diet.babysdiet.util.Action

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


    BackHandler(onBackPressed = {
        if (selectedCategoryId < 0) {
            navigateToHomeScreen(
                Action.NO_ACTION
            )
        } else {
            navigateToCategoryScreen(
                selectedCategoryId,
                selectedProductId,
                Action.NO_ACTION
            )
        }
    })

    Scaffold(
        topBar = {
            ProductAppBar(
                selectedProductId = selectedProductId,
                onAddProductClicked = { action ->
//                    sharedViewModel.action.value = action
                    navigateToCategoryScreen(
                        selectedCategoryId,
                        selectedProductId,
                        Action.ADD_PRODUCT
                    )
                },
                onBackNewProductClicked = { action ->
//                    sharedViewModel.action.value = action
                    navigateToCategoryScreen(
                        selectedCategoryId,
                        selectedProductId,
                        Action.NO_ACTION
                    )
                },
                onBackClicked = { action ->
//                    sharedViewModel.action.value = action
                    if (selectedCategoryId < 0)
                        navigateToHomeScreen(action)
                    else
                        navigateToCategoryScreen(
                            selectedCategoryId,
                            selectedProductId,
                            Action.NO_ACTION
                        )
                },
                onDeleteClicked = { action ->
//                    sharedViewModel.action.value = action
                    if (selectedCategoryId < 0)
                        navigateToHomeScreen(action)
                    else
                        navigateToCategoryScreen(
                            selectedCategoryId,
                            selectedProductId,
                            Action.DELETE_PRODUCT
                        )
                },
                onUpdateClicked = { action ->
//                    sharedViewModel.action.value = action
                    if (selectedCategoryId < 0)
                        navigateToHomeScreen(action)
                    else
                        navigateToCategoryScreen(
                            selectedCategoryId,
                            selectedProductId,
                            Action.UPDATE_PRODUCT
                        )
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
                onTitleChange = { sharedViewModel.nameProduct.value = it },
                onDescriptionChange = { sharedViewModel.descriptionProduct.value = it },
                onIsAllergenChange = { sharedViewModel.isAllergenProduct.value = it },
                navigateToDiaryScreen = navigateToDiaryScreen
            )

        }
    )
}

@Composable
fun BackHandler(
    backDispatcher: OnBackPressedDispatcher? = LocalOnBackPressedDispatcherOwner.current?.onBackPressedDispatcher,
    onBackPressed: () -> Unit
) {
    val currentOnBackPressed by rememberUpdatedState(newValue = onBackPressed)
    val backCallback = remember {
        object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                currentOnBackPressed()
            }
        }
    }
    DisposableEffect(key1 = backDispatcher) {
        backDispatcher?.addCallback(backCallback)
        onDispose {
            backCallback.remove()
        }
    }
}



