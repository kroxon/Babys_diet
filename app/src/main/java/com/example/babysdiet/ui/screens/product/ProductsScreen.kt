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

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ProductsScreen(
    navigateToHomeScreen: (Action) -> Unit,
    navigateToCategoryScreen: (categoryId: Int, productId: Int) -> Unit,
    navigateToDiaryScreen: (diaryId: Int, productId: Int) -> Unit,
    sharedViewModel: SharedViewModel,
    selectedProductId: Int,
    selectedCategoryId: Int,
    selectedProduct: Product?
) {
    val action by sharedViewModel.action

    val allDiaries by sharedViewModel.allDiaries.collectAsState()


    val productName: String by sharedViewModel.nameProduct
    val productDescription: String by sharedViewModel.descriptionProduct
    val productIsAllergen: Boolean by sharedViewModel.isAllergenProduct

    val snackbarHostState = remember { SnackbarHostState() }

    DisplaySnackbar(
        handleDatabaseAction = { sharedViewModel.handleDatabaseActions(action = action) },
        snackbarHostState = snackbarHostState,
        action = action,
        onUndoClicked = {
            sharedViewModel.action.value = it
        }
    )

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        topBar = {
            ProductAppBar(
                selectedProductId = selectedProductId,
                onAddProductClicked = { action ->
                    sharedViewModel.action.value = action
                    navigateToCategoryScreen(selectedCategoryId, selectedProductId)
                },
                onBackNewProductClicked = { action ->
                    sharedViewModel.action.value = action
                    navigateToCategoryScreen(selectedCategoryId, selectedProductId)
                },
                onBackClicked = { action ->
                    sharedViewModel.action.value = action
                    if (selectedCategoryId == -1)
                        navigateToHomeScreen(action)
                    else
                        navigateToCategoryScreen(selectedCategoryId, selectedProductId)
                },
                onDeleteClicked = { action ->
                    sharedViewModel.action.value = action
                    if (selectedCategoryId == -1)
                        navigateToHomeScreen(action)
                    else
                        navigateToCategoryScreen(selectedCategoryId, selectedProductId)
                },
                onUpdateClicked = { action ->
                    sharedViewModel.action.value = action
                    if (selectedCategoryId == -1)
                        navigateToHomeScreen(action)
                    else
                        navigateToCategoryScreen(selectedCategoryId, selectedProductId)
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
                onTitleChange = {},
                onDescriptionChange = {},
                onIsAllergenChange = {},
                navigateToDiaryScreen = navigateToDiaryScreen
            )

        }
    )
}

@Composable
fun DisplaySnackbar(
    onUndoClicked: (Action) -> Unit,
    handleDatabaseAction: () -> Unit,
    snackbarHostState: SnackbarHostState,
    action: Action
) {
    handleDatabaseAction()
    val scope = rememberCoroutineScope()
    LaunchedEffect(key1 = action) {
        if (action != Action.NO_ACTION) {
            scope.launch {
                val snackBarResult = snackbarHostState.showSnackbar(
                    message = setMessage(
                        action = action
                    ),
                    actionLabel = setActionLabel(action)
                )
                undoDeleteProduct(
                    action = action,
                    snackBarResult = snackBarResult,
                    onUndoClicked = onUndoClicked
                )
            }
        }
    }
}


private fun setMessage(action: Action): String {
    return when (action) {
        Action.DELETE_ALL_PRODUCTS -> "All products deleted."
        else -> "${action.name}"
    }
}

private fun setActionLabel(action: Action): String {
    return if (action == Action.DELETE_PRODUCT) "UNDO" else "OK"
}

private fun undoDeleteProduct(
    action: Action,
    snackBarResult: SnackbarResult,
    onUndoClicked: (Action) -> Unit
) {
    if (snackBarResult == SnackbarResult.ActionPerformed &&
        action == Action.DELETE_PRODUCT
    )
        onUndoClicked(Action.UNDO_PRODUCT)
}

