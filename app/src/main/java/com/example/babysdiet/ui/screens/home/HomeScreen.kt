package com.example.babysdiet.ui.screens.home

import android.annotation.SuppressLint
import androidx.activity.OnBackPressedCallback
import androidx.activity.OnBackPressedDispatcher
import androidx.activity.compose.LocalOnBackPressedDispatcherOwner
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
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import com.example.babysdiet.R
import com.example.babysdiet.ui.theme.fabContentColor
import com.example.babysdiet.ui.viewmodels.SharedViewModel
import com.example.babysdiet.util.Action
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun HomeScreen(
    navigateToCategoryScreen: (categoryId: Int, productId: Int, action: Action) -> Unit,
    navigateToDiaryScreen: (diaryId: Int, productId: Int) -> Unit,
    navigateToProductScreen:  (productId: Int, categoryId: Int) -> Unit,
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
        onUndoClicked = {
            sharedViewModel.action.value = it
        }
    )

    Scaffold(
        snackbarHost = { SnackbarHost(hostState = snackbarHostState) },
        topBar = {
            HomeAppBar()
        },
        content = {
            HomeContent(
                diaries = allDiaries,
                products = allProducts,
                allergens = allergens,
                navigateToDiaryScreen = navigateToDiaryScreen,
                navigateToCategoryScreen = navigateToCategoryScreen,
                onAllegrenClickListener = navigateToProductScreen
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
                undoDeleteDiary(
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
        Action.DELETE_ALL_DIARIES -> "All diet diary entries deleted."
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