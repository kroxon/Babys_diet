package com.example.babysdiet.navigation.destinations

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.babysdiet.ui.screens.home.HomeScreen
import com.example.babysdiet.ui.viewmodels.SharedViewModel
import com.example.babysdiet.util.Constants.HOME_ARGUMENT_KEY
import com.example.babysdiet.util.Constants.HOME_SCREEN


fun NavGraphBuilder.homeComposable(
    navigateToCategoryScreen: (categoryId: Int) -> Unit,
    navigateToDiaryScreen: (diaryId: Int) -> Unit,
    sharedViewModel: SharedViewModel
) {
    composable(
        route = HOME_SCREEN,
        arguments = listOf(navArgument(HOME_ARGUMENT_KEY) {
            type = NavType.StringType
        })
    ) {
        HomeScreen(
            navigateToCategoryScreen = navigateToCategoryScreen,
            navigateToDiaryScreen = navigateToDiaryScreen,
            sharedViewModel = sharedViewModel
        )
    }
}

