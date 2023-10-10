package com.example.babysdiet.ui.screens.categories

import android.annotation.SuppressLint
import android.content.Context
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.activity.OnBackPressedDispatcher
import androidx.activity.compose.LocalOnBackPressedDispatcherOwner
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringArrayResource
import com.example.babysdiet.R
import com.example.babysdiet.ui.viewmodels.SharedViewModel
import com.example.babysdiet.util.Action
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun CategoriesScreen(
    navigateToHomeScreen: (Action) -> Unit,
    navigateToProductScreen: (categoryId: Int, productId: Int) -> Unit,
    sharedViewModel: SharedViewModel,
    selectedProductId: Int,
    selectedCategoryId: Int
) {
    val context = LocalContext.current
    val action by sharedViewModel.action

    LaunchedEffect(key1 = true) {
        sharedViewModel.categorySelection.value = MutableList(12) { false }
        (sharedViewModel.categorySelection.value as MutableList<Boolean>)[selectedCategoryId] = true
        sharedViewModel.getSelectedProducts()
    }


    val selectedProducts by sharedViewModel.selectedProducts.collectAsState()
    val allDiaries by sharedViewModel.allDiaries.collectAsState()

    BackHandler(onBackPressed = { navigateToHomeScreen(Action.NO_ACTION) })

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
            CategoriesAppBar(
                navigateToHomeScreen = { action ->
                    if (action == Action.NO_ACTION)
                        navigateToHomeScreen(action)
                    else {
                        if (sharedViewModel.validateDiaryFields())
                            navigateToHomeScreen(action)
                        else
                            displayToast(context = context)
                    }
                },
                navigateToProductScreen = navigateToProductScreen,
                categoryId = selectedCategoryId
            )
        },
        content = {
            CategoriesContent(
                selectedProducts = selectedProducts,
                allDiaries = allDiaries,
                navigateToProductScreen = { productId ->
                    navigateToProductScreen(selectedCategoryId, productId)
                },
                selectedCategoryId = selectedCategoryId,
                selectedProductId = selectedProductId
            )

        }
    )
}

fun displayToast(context: Context) {
    Toast.makeText(
        context, context.getString(R.string.selectProduct),
        Toast.LENGTH_SHORT
    ).show()
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