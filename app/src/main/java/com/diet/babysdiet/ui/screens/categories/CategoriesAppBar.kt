package com.diet.babysdiet.ui.screens.categories

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import com.diet.babysdiet.R
import com.diet.babysdiet.ui.theme.topAppBarBackgroumdColor
import com.diet.babysdiet.ui.theme.topAppBarContentColor
import com.diet.babysdiet.util.Action

@Composable
fun CategoriesAppBar(
    categoryId: Int,
    navigateToHomeScreen: (Action) -> Unit,
    navigateToProductScreen: (categoryId: Int, productId: Int) -> Unit
) {

    CategoriesTopAppBar(
        navigateToHomeScreen = navigateToHomeScreen,
        navigateToProductScreen = navigateToProductScreen,
        categoryId = categoryId
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CategoriesTopAppBar(
    navigateToHomeScreen: (Action) -> Unit,
    navigateToProductScreen: (categoryId: Int, productId: Int) -> Unit,
    categoryId: Int
) {
    val categories = stringArrayResource(id = R.array.categories_array)

    TopAppBar(
        navigationIcon = {
            BackAction(onBackClicked = navigateToHomeScreen)
        },
        title = {
            Text(
                text = categories[categoryId],
                modifier = Modifier.fillMaxWidth(1f),
                textAlign = TextAlign.Center
            )
        },
        actions = {
            AddNewProduct(
                categoryId = categoryId,
                onAddProductClicked = navigateToProductScreen
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


//@Composable
//fun ExistigCategoriesAppBarAction(
//    navigateToHomeScreen: (Action) -> Unit
//) {
//    var openDialog by remember {
//        mutableStateOf(false)
//    }
//    DisplayAlertDialog(
//        title = stringResource(id = R.string.delete_product_ask_title),
//        message = stringResource(id = R.string.delete_product_ask),
//        openDialog = openDialog,
//        closeDialog = { openDialog = false },
//        onYesClicked = { navigateToHomeScreen(Action.DELETE_PRODUCT) },
//        onNoClicked = {})
//
//    DeleteAction(onDeleteeClicked = {
//        openDialog = true
//    })
//    UpdateAction(onUpdateClicked = navigateToHomeScreen)
//}

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
fun AddNewProduct(
    categoryId: Int,
    onAddProductClicked: (categoryId: Int, productId: Int) -> Unit
) {
    IconButton(onClick = { onAddProductClicked(categoryId, -1) }) {
        Icon(
            imageVector = Icons.Filled.Add,
            contentDescription = stringResource(id = R.string.add_product),
            tint = MaterialTheme.colorScheme.topAppBarContentColor
        )
    }
}

//@Composable
//fun UpdateAction(
//    onUpdateClicked: (Action) -> Unit
//) {
//    IconButton(onClick = { onUpdateClicked(Action.UPDATE_PRODUCT) }) {
//        Icon(
//            imageVector = Icons.Filled.Check,
//            contentDescription = stringResource(id = R.string.update_product),
//            tint = MaterialTheme.colorScheme.topAppBarContentColor
//        )
//    }
//}
//
//@Composable
//fun CloseeAction(
//    onCloseClicked: (Action) -> Unit
//) {
//    IconButton(onClick = { onCloseClicked(Action.NO_ACTION) }) {
//        Icon(
//            imageVector = Icons.Filled.Close,
//            contentDescription = stringResource(id = R.string.close_product),
//            tint = MaterialTheme.colorScheme.topAppBarContentColor
//        )
//    }
//}
//
//@Composable
//fun DeleteAction(
//    onDeleteeClicked: (Action) -> Unit
//) {
//    IconButton(onClick = { onDeleteeClicked(Action.DELETE_PRODUCT) }) {
//        Icon(
//            imageVector = Icons.Filled.Delete,
//            contentDescription = stringResource(id = R.string.delete_product),
//            tint = MaterialTheme.colorScheme.topAppBarContentColor
//        )
//    }
//}
//
//@Composable
//@Preview
//fun NewProductAppBarPrewiew() {
//    CategoriesAppBar(
//        navigateToHomeScreen = {},
//        navigateToProductScreen = { _, _ -> },
//        categoryId = 1
//    )
//}

//@Composable
//@Preview
//fun ExistindDiaryAppBarPrewiew() {
//    ExistindDiaryAppBar(
//        navigateToHomeScreen = {},
//        selectedProduct = Product(
//            productId = 0,
//            name = "Milk",
//            categoryId = 1,
//            description = "description",
//            isAllergen = true
//        )
//    )
//}