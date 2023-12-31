package com.diet.babysdiet.navigation.destinations

import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.diet.babysdiet.ui.screens.home.HomeScreen
import com.diet.babysdiet.ui.viewmodels.SharedViewModel
import com.diet.babysdiet.util.Action
import com.diet.babysdiet.util.Constants.HOME_ARGUMENT_KEY
import com.diet.babysdiet.util.Constants.HOME_SCREEN
import com.diet.babysdiet.util.toAction


fun NavGraphBuilder.homeComposable(
    navigateToCategoryScreen: (categoryId: Int, productId: Int, action: Action) -> Unit,
    navigateToDiaryScreen: (diaryId: Int, productId: Int) -> Unit,
    navigateToProductScreen: (categoryId: Int, productId: Int) -> Unit,
    sharedViewModel: SharedViewModel
) {
    composable(
        route = HOME_SCREEN,
        arguments = listOf(navArgument(HOME_ARGUMENT_KEY) {
            type = NavType.StringType
        })
    ) { navBAckStackEntry ->
        val action =navBAckStackEntry.arguments?.getString(HOME_ARGUMENT_KEY).toAction()

        LaunchedEffect(key1 = action){
            sharedViewModel.action.value = action
        }
        HomeScreen(
            navigateToCategoryScreen = navigateToCategoryScreen,
            navigateToDiaryScreen = navigateToDiaryScreen,
            navigateToProductScreen = navigateToProductScreen,
            sharedViewModel = sharedViewModel
        )
    }
}

