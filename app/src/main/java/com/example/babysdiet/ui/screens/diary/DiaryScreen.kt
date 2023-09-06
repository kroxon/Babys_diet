package com.example.babysdiet.ui.screens.diary

import android.annotation.SuppressLint
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import com.example.babysdiet.components.data.models.Diary
import com.example.babysdiet.components.data.models.Product
import com.example.babysdiet.ui.viewmodels.SharedViewModel
import com.example.babysdiet.util.Action
import com.example.babysdiet.util.RequestState

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun DiaryScreen(
    navigateToHomeScreen: (Action) -> Unit,
    sharedViewModel: SharedViewModel,
    selectedDiary: Diary?,
    selectedProduct: Product?
) {
    LaunchedEffect(key1 = true) {
        sharedViewModel.getAllProducts()
    }
    LaunchedEffect(key1 = true) {
        sharedViewModel.getAllDiaries()
    }
    sharedViewModel.getSelectedProducts()

    val allProducts by sharedViewModel.allProducts.collectAsState()
    val allDiaries by sharedViewModel.allDiaries.collectAsState()
    val selectedProducts by sharedViewModel.selectedProducts.collectAsState()

    // product
    val idProduct by sharedViewModel.idProduct
    val idCategoryProduct by sharedViewModel.idCategoryProduct
    val nameProduct by sharedViewModel.nameProduct
    val descriptionProduct by sharedViewModel.descriptionProduct
    val isAllergenProduct by sharedViewModel.isAllergenProduct

    Scaffold(
        topBar = {
            DiaryAppBar(
                navigateToHomeScreen = navigateToHomeScreen,
                selectedDiary = selectedDiary,
                selectedProduct = selectedProduct,
                sharedViewModel = sharedViewModel
            )
        },
        content = {
            DiaryContent(
                allProducts = allProducts,
                selectedDiary = selectedDiary,
                selectedProduct = selectedProduct,
                sharedViewModel = sharedViewModel,
                selectedProducts = selectedProducts,
                onProductSelected = {
                    sharedViewModel.idProduct.value = it.productId
                    sharedViewModel.nameProduct.value = it.name
                }
            )

        }
    )
}

