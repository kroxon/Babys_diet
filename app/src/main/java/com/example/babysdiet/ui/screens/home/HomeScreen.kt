package com.example.babysdiet.ui.screens.home

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import com.example.babysdiet.R
import com.example.babysdiet.components.data.models.Product
import com.example.babysdiet.ui.theme.fabContentColor
import com.example.babysdiet.ui.viewmodels.SharedViewModel
import com.example.babysdiet.util.RequestState

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun HomeScreen(
    navigateToCategoryScreen: (categoryId: Int) -> Unit,
    navigateToDiaryScreen: (diaryId: Int, productId: Int) -> Unit,
    sharedViewModel: SharedViewModel
) {
    LaunchedEffect(key1 = true) {
        sharedViewModel.getAllProducts()
    }
    LaunchedEffect(key1 = true) {
        sharedViewModel.getAllDiaries()
    }
    val allProducts by sharedViewModel.allProducts.collectAsState()
    val allDiaries by sharedViewModel.allDiaries.collectAsState()

    val productsList = sharedViewModel.vegetables
    for (p in productsList){
        Log.d("vegetables: ", p)
    }

//    if ((allProducts as RequestState.Success<List<Product>>).data.isEmpty()) {
//        val context = LocalContext.current
//        val vegetables = context.resources.getStringArray(R.array.vegetables)
//        val fruits = context.resources.getStringArray(R.array.fruits)
//        val dried_fruits =
//            context.resources.getStringArray(R.array.dried_fruits)
//        val dairy_and_eggs =
//            context.resources.getStringArray(R.array.dairy_and_eggs)
//        val spieces = context.resources.getStringArray(R.array.spices)
//        val legumes = context.resources.getStringArray(R.array.legumes)
//        val meats = context.resources.getStringArray(R.array.meat)
//        val fishes = context.resources.getStringArray(R.array.fish_and_seafood)
//        val grains = context.resources.getStringArray(R.array.cereal_products)
//        val mushrooms = context.resources.getStringArray(R.array.mushrooms)
//        val others = context.resources.getStringArray(R.array.other)
//
//    }

    Scaffold(
        topBar = {
            HomeAppBar()
        },
        content = {
            HomeContent(
                diaries = allDiaries,
                products = allProducts,
                navigateToDiaryScreen = navigateToDiaryScreen
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