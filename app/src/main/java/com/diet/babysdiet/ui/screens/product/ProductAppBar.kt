package com.diet.babysdiet.ui.screens.product

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
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
import androidx.compose.ui.res.stringResource
import com.diet.babysdiet.R
import com.diet.babysdiet.components.DisplayAlertDialog
import com.diet.babysdiet.ui.theme.topAppBarBackgroumdColor
import com.diet.babysdiet.ui.theme.topAppBarContentColor
import com.diet.babysdiet.util.Action

@Composable
fun ProductAppBar(
    selectedProductId: Int,
    onAddProductClicked: (Action) -> Unit,
    onBackNewProductClicked: (Action) -> Unit,
    onBackClicked: (Action) -> Unit,
    onDeleteClicked: (Action) -> Unit,
    onUpdateClicked: (Action) -> Unit
) {
    if (selectedProductId == -1) {
        NewProductAppBar(
            onAddProductClicked = onAddProductClicked,
            onBackNewProductClicked = onBackNewProductClicked
        )
    } else {
        ExistindProductAppBar(
            onDeleteClicked = onDeleteClicked,
            onUpdateClicked = onUpdateClicked,
            onBackClicked = onBackClicked
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewProductAppBar(
    onAddProductClicked: (Action) -> Unit,
    onBackNewProductClicked: (Action) -> Unit
) {
    TopAppBar(
        navigationIcon = {
            BackActionNewProduct(
                onBackNewProductClicked = onBackNewProductClicked
            )
        },
        title = { Text(text = stringResource(id = R.string.add_diary)) },
        actions = {
            AddActionNewProduct(onAddProductClicked = onAddProductClicked)
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
    onBackClicked: (Action) -> Unit,
    onDeleteClicked: (Action) -> Unit,
    onUpdateClicked: (Action) -> Unit
) {
    TopAppBar(
        navigationIcon = {
            BackAction(onBackClicked = onBackClicked)
        },
        title = { Text(text = stringResource(id = R.string.edit_product)) },
        actions = {
            ExistigProductAppBarAction(
                onDeleteClicked = onDeleteClicked,
                onUpdateClicked = onUpdateClicked
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
fun BackActionNewProduct(
    onBackNewProductClicked: (Action) -> Unit
) {
    IconButton(onClick = { onBackNewProductClicked(Action.NO_ACTION) }) {
        Icon(
            imageVector = Icons.Filled.ArrowBack,
            contentDescription = stringResource(id = R.string.arrow_back),
            tint = MaterialTheme.colorScheme.topAppBarContentColor
        )
    }
}

//@Composable
//fun AddAction(
//    onAddProductClicked: (Action) -> Unit
//) {
//    IconButton(onClick = { onAddProductClicked(Action.ADD_PRODUCT) }) {
//        Icon(
//            imageVector = Icons.Filled.Check,
//            contentDescription = stringResource(id = R.string.add_product),
//            tint = MaterialTheme.colorScheme.topAppBarContentColor
//        )
//    }
//}

@Composable
fun AddActionNewProduct(
    onAddProductClicked: (Action) -> Unit
) {
    IconButton(onClick = { onAddProductClicked(Action.ADD_PRODUCT) }) {
        Icon(
            imageVector = Icons.Filled.Check,
            contentDescription = stringResource(id = R.string.add_product),
            tint = MaterialTheme.colorScheme.topAppBarContentColor
        )
    }
}

@Composable
fun ExistigProductAppBarAction(
    onDeleteClicked: (Action) -> Unit,
    onUpdateClicked: (Action) -> Unit
) {
    var openDialog by remember {
        mutableStateOf(false)
    }
    DisplayAlertDialog(
        title = stringResource(id = R.string.delete_product_ask_title),
        message = stringResource(id = R.string.delete_product_ask),
        openDialog = openDialog,
        closeDialog = { openDialog = false },
        onYesClicked = { onDeleteClicked(Action.DELETE_PRODUCT) },
        onNoClicked = { openDialog = false })

    DeleteAction(onDeleteClicked = {
        openDialog = true
    })
    UpdateAction(onUpdateClicked = {
        onUpdateClicked(Action.UPDATE_PRODUCT)
    })
}

//@Composable
//fun AddNewProduct(
//    categoryId: Int,
//    onAddProductClicked: (categoryId: Int, productId: Int) -> Unit
//) {
//    IconButton(onClick = { onAddProductClicked(categoryId, -1) }) {
//        Icon(
//            imageVector = Icons.Filled.Add,
//            contentDescription = stringResource(id = R.string.add_product),
//            tint = MaterialTheme.colorScheme.topAppBarContentColor
//        )
//    }
//}

@Composable
fun UpdateAction(
    onUpdateClicked: (Action) -> Unit
) {
    IconButton(onClick = { onUpdateClicked(Action.UPDATE_PRODUCT) }) {
        Icon(
            imageVector = Icons.Filled.Check,
            contentDescription = stringResource(id = R.string.update_product),
            tint = MaterialTheme.colorScheme.topAppBarContentColor
        )
    }
}

@Composable
fun CloseAction(
    onCloseClicked: (Action) -> Unit
) {
    IconButton(onClick = { onCloseClicked(Action.NO_ACTION) }) {
        Icon(
            imageVector = Icons.Filled.Close,
            contentDescription = stringResource(id = R.string.close_product),
            tint = MaterialTheme.colorScheme.topAppBarContentColor
        )
    }
}

@Composable
fun DeleteAction(
    onDeleteClicked: (Action) -> Unit
) {
    IconButton(onClick = { onDeleteClicked(Action.DELETE_PRODUCT) }) {
        Icon(
            imageVector = Icons.Filled.Delete,
            contentDescription = stringResource(id = R.string.delete_product),
            tint = MaterialTheme.colorScheme.topAppBarContentColor
        )
    }
}