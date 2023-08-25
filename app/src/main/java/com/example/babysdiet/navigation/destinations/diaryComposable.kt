package com.example.babysdiet.navigation.destinations

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.babysdiet.ui.screens.home.HomeScreen
import com.example.babysdiet.ui.viewmodels.SharedViewModel
import com.example.babysdiet.util.Action
import com.example.babysdiet.util.Constants.DIARY_ARGUMENT_KEY
import com.example.babysdiet.util.Constants.DIARY_SCREEN
import com.example.babysdiet.util.Constants.HOME_ARGUMENT_KEY
import com.example.babysdiet.util.Constants.HOME_SCREEN


fun NavGraphBuilder.diaryComposable(
    navigateToHomeScreen: (Action) -> Unit,
    sharedViewModel: SharedViewModel
) {
    composable(
        route = DIARY_SCREEN,
        arguments = listOf(navArgument(DIARY_ARGUMENT_KEY) {
            type = NavType.IntType
        })
    ) {
        HomeScreen(
            navigateToCategoryScreen = {},
            navigateToDiaryScreen = {}
            ,
            sharedViewModel = sharedViewModel
        )
    }
}

