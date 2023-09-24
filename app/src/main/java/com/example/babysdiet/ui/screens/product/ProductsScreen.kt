package com.example.babysdiet.ui.screens.product

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.example.babysdiet.R
import com.example.babysdiet.components.DisplayAlertDialog
import com.example.babysdiet.ui.screens.categories.DeleteAction
import com.example.babysdiet.ui.screens.categories.UpdateAction
import com.example.babysdiet.ui.theme.topAppBarBackgroumdColor
import com.example.babysdiet.ui.theme.topAppBarContentColor
import com.example.babysdiet.ui.viewmodels.SharedViewModel
import com.example.babysdiet.util.Action

@Composable
fun ProductsScreen(
    navigateToHomeScreen: (Action) -> Unit,
    navigateToCategoryScreen: (categoryId: Int, productId: Int) -> Unit,
    sharedViewModel: SharedViewModel,
    selectedProductId: Int,
    selectedCategoryId: Int
) {

    if (selectedProductId == -1) {
//        NewProductAppBar(navigateToCategoryScreen = navigateToCategoryScreen)
    } else {
        Text(text = "edit product")

    }

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Column(
            Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            Text(
                fontSize = MaterialTheme.typography.titleLarge.fontSize,
                text = selectedProductId.toString()
            )
            Text(
                fontSize = MaterialTheme.typography.titleLarge.fontSize,
                text = selectedCategoryId.toString()
            )


        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewProductAppBar(
    navigateToCategoryScreen: (Action) -> Unit
) {
    TopAppBar(
        navigationIcon = {
            BackAction(onBackClicked = navigateToCategoryScreen)
        },
        title = { Text(text = stringResource(id = R.string.add_diary)) },
        actions = {
            AddAction(onAddDiaryClicked = navigateToCategoryScreen)
        },
        colors = TopAppBarDefaults.mediumTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.topAppBarBackgroumdColor,
            titleContentColor = MaterialTheme.colorScheme.onPrimary,
            navigationIconContentColor = MaterialTheme.colorScheme.onPrimary,
            actionIconContentColor = MaterialTheme.colorScheme.onSecondary
        )
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExistindProductAppBar(
    navigateToHomeScreen: (Action) -> Unit
) {
    TopAppBar(
        navigationIcon = {
            BackAction(onBackClicked = navigateToHomeScreen)
        },
        title = { Text(text = stringResource(id = R.string.edit_diary)) },
        actions = {
            ExistigProductAppBarAction(
                navigateToHomeScreen = navigateToHomeScreen
            )
        },
        colors = TopAppBarDefaults.mediumTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.topAppBarBackgroumdColor,
            titleContentColor = MaterialTheme.colorScheme.onPrimary,
            navigationIconContentColor = MaterialTheme.colorScheme.onPrimary,
            actionIconContentColor = MaterialTheme.colorScheme.onSecondary
        )
    )
}

@Composable
fun BackAction(
    onBackClicked: (Action) -> Unit
) {
    IconButton(onClick = { onBackClicked(Action.NO_ACTION) }) {
        Icon(
            imageVector = Icons.Filled.ArrowBack,
            contentDescription = stringResource(id = R.string.arrow_back),
            tint = MaterialTheme.colorScheme.topAppBarContentColor
        )
    }
}

@Composable
fun AddAction(
    onAddDiaryClicked: (Action) -> Unit
) {
    IconButton(onClick = { onAddDiaryClicked(Action.ADD_DIARY) }) {
        Icon(
            imageVector = Icons.Filled.Check,
            contentDescription = stringResource(id = R.string.add_diary),
            tint = MaterialTheme.colorScheme.topAppBarContentColor
        )
    }
}

@Composable
fun ExistigProductAppBarAction(
    navigateToHomeScreen: (Action) -> Unit
) {
    var openDialog by remember {
        mutableStateOf(false)
    }
    DisplayAlertDialog(
        title = stringResource(id = R.string.delete_product_ask_title),
        message = stringResource(id = R.string.delete_product_ask),
        openDialog = openDialog,
        closeDialog = { openDialog = false },
        onYesClicked = { navigateToHomeScreen(Action.DELETE_PRODUCT) },
        onNoClicked = {})

    DeleteAction(onDeleteeClicked = {
        openDialog = true
    })
    UpdateAction(onUpdateClicked = navigateToHomeScreen)
}