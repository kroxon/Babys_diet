package com.example.babysdiet.navigation.destinations

import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.babysdiet.components.data.models.Product
import com.example.babysdiet.ui.screens.diary.DiaryScreen
import com.example.babysdiet.ui.viewmodels.SharedViewModel
import com.example.babysdiet.util.Action
import com.example.babysdiet.util.Constants.DIARY_ARGUMENT_KEY
import com.example.babysdiet.util.Constants.DIARY_ARGUMENT_KEY_2
import com.example.babysdiet.util.Constants.DIARY_SCREEN


fun NavGraphBuilder.diaryComposable(
    navigateToHomeScreen: (Action) -> Unit,
    sharedViewModel: SharedViewModel
) {
    composable(
        route = DIARY_SCREEN,
        arguments = listOf(
            navArgument(DIARY_ARGUMENT_KEY) {
                type = NavType.IntType
            },
            navArgument(DIARY_ARGUMENT_KEY_2) {
                type = NavType.IntType
            },
        )
    ) { navBackStackEntry ->
        val diaryId = navBackStackEntry.arguments!!.getInt(DIARY_ARGUMENT_KEY)
        val productId = navBackStackEntry.arguments!!.getInt(DIARY_ARGUMENT_KEY_2)

        sharedViewModel.getSelectedDiary(diaryId = diaryId)
        val selectedDiary by sharedViewModel.selectedDiary.collectAsState()

        sharedViewModel.getSelectedProduct(productId = productId)
        val selectedProduct by sharedViewModel.selectedProduct.collectAsState()

        DiaryScreen(
            navigateToHomeScreen = navigateToHomeScreen,
            sharedViewModel = sharedViewModel,
            selectedDiary = selectedDiary,
            selectedProduct = selectedProduct
        )
    }
}

