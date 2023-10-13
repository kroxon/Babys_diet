package com.example.babysdiet.navigation.destinations

import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
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
        ),
        enterTransition = {
            slideInHorizontally(
                animationSpec = tween(
                    durationMillis = 300
                ),
                initialOffsetX = { fullWidth -> fullWidth }
            )
        }
    ) { navBackStackEntry ->
        val diaryId = navBackStackEntry.arguments!!.getInt(DIARY_ARGUMENT_KEY)
        val productId = navBackStackEntry.arguments!!.getInt(DIARY_ARGUMENT_KEY_2)

        LaunchedEffect(key1 = diaryId) {
            sharedViewModel.getSelectedDiary(diaryId = diaryId)
            sharedViewModel.categorySelection.value = List(12) { true }
        }
        val selectedDiary by sharedViewModel.selectedDiary.collectAsState()

        LaunchedEffect(key1 = productId) {
            sharedViewModel.getSelectedProduct(productId = productId)
        }
        val selectedProduct by remember { sharedViewModel.selectedProduct }.collectAsState()
//        sharedViewModel.selectedDiaryProduct.value = selectedProduct

        LaunchedEffect(key1 = selectedDiary) {
            if (selectedDiary != null || diaryId == -1)
                sharedViewModel.updateDiaryFields(
                    selectedDiary = selectedDiary,
                    selectedProduct = selectedProduct
                )
        }

        DiaryScreen(
            navigateToHomeScreen = navigateToHomeScreen,
            sharedViewModel = sharedViewModel,
            selectedDiary = selectedDiary,
            selectedProduct = selectedProduct
        )
    }
}

