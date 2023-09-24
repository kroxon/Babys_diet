package com.example.babysdiet.ui.screens.categories

import android.annotation.SuppressLint
import android.content.Context
import android.widget.Toast
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringArrayResource
import com.example.babysdiet.R
import com.example.babysdiet.ui.viewmodels.SharedViewModel
import com.example.babysdiet.util.Action

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


    LaunchedEffect(key1 = true) {
        sharedViewModel.categorySelection.value = MutableList(12) { false }
        (sharedViewModel.categorySelection.value as MutableList<Boolean>)[selectedCategoryId] = true
        sharedViewModel.getSelectedProducts()
    }


    val selectedProducts by sharedViewModel.selectedProducts.collectAsState()
    val allDiaries by sharedViewModel.allDiaries.collectAsState()

    Scaffold(
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
                navigateToProductScreen = {
//                    sharedViewModel.selectedProduct.value = it
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