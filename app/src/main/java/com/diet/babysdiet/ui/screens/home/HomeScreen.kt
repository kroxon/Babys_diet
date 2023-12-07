package com.diet.babysdiet.ui.screens.home

import android.annotation.SuppressLint
import android.content.Context
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import com.diet.babysdiet.R
import com.diet.babysdiet.ui.theme.fabContentColor
import com.diet.babysdiet.ui.viewmodels.SharedViewModel
import com.diet.babysdiet.util.Action

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun HomeScreen(
    navigateToCategoryScreen: (categoryId: Int, productId: Int, action: Action) -> Unit,
    navigateToDiaryScreen: (diaryId: Int, productId: Int) -> Unit,
    navigateToProductScreen: (productId: Int, categoryId: Int) -> Unit,
    sharedViewModel: SharedViewModel
) {
    LaunchedEffect(key1 = true) {
        sharedViewModel.getAllProducts()
        sharedViewModel.getAllergenProducts()
    }
    LaunchedEffect(key1 = true) {
        sharedViewModel.getAllDiaries()
    }

    val action by sharedViewModel.action

    val allProducts by sharedViewModel.allProducts.collectAsState()
    val allergens by sharedViewModel.allegrenProducts.collectAsState()
    val allDiaries by sharedViewModel.allDiaries.collectAsState()


    val snackbarHostState = remember { SnackbarHostState() }

    DisplaySnackbar(
        handleDatabaseAction = { sharedViewModel.handleDatabaseActions(action = action) },
        snackbarHostState = snackbarHostState,
        action = action,
        context = LocalContext.current,
        onUndoClicked = {
            sharedViewModel.action.value = it
        }
    )

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
//        topBar = {
//            HomeAppBar()
//        },
        content = { innerPaddings ->
            HomeContent(
                diaries = allDiaries,
                products = allProducts,
                allergens = allergens,
                navigateToDiaryScreen = navigateToDiaryScreen,
                navigateToCategoryScreen = navigateToCategoryScreen,
                onAllegrenClickListener = navigateToProductScreen,
                onSwipeToDelete = { action, diary, product ->
                    sharedViewModel.action.value = action
                    sharedViewModel.updateDiaryFields(
                        selectedDiary = diary,
                        selectedProduct = product
                    )
                    snackbarHostState.currentSnackbarData?.dismiss()
                },
                paddings = innerPaddings
            )
        },
        floatingActionButton = {
            HomeFab(onFabClicked = navigateToDiaryScreen)
        }
    )
}

@Composable
fun HomeFab(
    onFabClicked: (diaryId: Int, productId: Int) -> Unit
) {
    FloatingActionButton(
        onClick = {
            onFabClicked(-1, -1)
        },
        containerColor = MaterialTheme.colorScheme.fabContentColor
    ) {
        Icon(
            imageVector = Icons.Rounded.Add,
            contentDescription = stringResource(id = R.string.add_button),
            tint = Color.White
        )
    }
}

@Composable
fun DisplaySnackbar(
    onUndoClicked: (Action) -> Unit,
    handleDatabaseAction: () -> Unit,
    snackbarHostState: SnackbarHostState,
    action: Action,
    context: Context
) {
    handleDatabaseAction()
    val scope = rememberCoroutineScope()
    LaunchedEffect(key1 = action) {
//        if (action == Action.DELETE_DIARY) {
//            scope.launch {
//                val snackBarResult = snackbarHostState.showSnackbar(
//                    message = setMessage(
//                        action = action,
//                        context = context
//                    ),
//                    actionLabel = setActionLabel(action)
//                )
//                undoDeleteDiary(
//                    action = action,
//                    snackBarResult = snackBarResult,
//                    onUndoClicked = onUndoClicked
//                )
//            }
//        }
    }
}


private fun setMessage(action: Action, context: Context): String {
    return when (action) {
        Action.DELETE_ALL_DIARIES -> "All diet diary entries deleted."
        Action.DELETE_DIARY -> context.getString(R.string.delete_diary)
        else -> "${action.name}"
    }
}

private fun setActionLabel(action: Action): String {
    return if (action == Action.DELETE_DIARY) "UNDO" else "OK"
}

private fun undoDeleteDiary(
    action: Action,
    snackBarResult: SnackbarResult,
    onUndoClicked: (Action) -> Unit
) {
    if (snackBarResult == SnackbarResult.ActionPerformed &&
        action == Action.DELETE_DIARY
    )
        onUndoClicked(Action.UNDO_DIARY)
}


//@Composable
//@Preview(showBackground = true)
//fun HomeScreenPreview() {
//    HomeScreen(
//        navigateToCategoryScreen = {},
//        navigateToDiaryScreen = {},
//        sharedViewModel = {}
//    )
//}