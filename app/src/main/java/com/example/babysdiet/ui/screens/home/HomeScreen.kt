package com.example.babysdiet.ui.screens.home

import android.annotation.SuppressLint
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import com.example.babysdiet.R
import com.example.babysdiet.ui.screens.diary.DisplaySnackbar
import com.example.babysdiet.ui.theme.fabContentColor
import com.example.babysdiet.ui.viewmodels.SharedViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun HomeScreen(
    navigateToCategoryScreen: (categoryId: Int, productId: Int) -> Unit,
    navigateToDiaryScreen: (diaryId: Int, productId: Int) -> Unit,
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
                onAllegrenClickListener = {}
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

//@Composable
//@Preview(showBackground = true)
//fun HomeScreenPreview() {
//    HomeScreen(
//        navigateToCategoryScreen = {},
//        navigateToDiaryScreen = {},
//        sharedViewModel = {}
//    )
//}