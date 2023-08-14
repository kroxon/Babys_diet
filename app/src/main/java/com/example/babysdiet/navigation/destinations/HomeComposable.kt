package com.example.babysdiet.navigation.destinations

import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.babysdiet.util.Constants.HOME_SCREEN


fun NavGraphBuilder.listComposable(
    navigateToTaskScreen: (taskId: Int) -> Unit,
) {
    composable(
        route = HOME_SCREEN,
    ) { navBAckStackEntry ->
//        val action =navBAckStackEntry.arguments?.getString(LIST_ARGUMENT_KEY).toAction()
        
//        LaunchedEffect(key1 = action){
//            sharedViewModel.action.value = action
//        }

//        ListScreen(
//            navigateToTaskScreen = navigateToTaskScreen,
//            sharedViewModel = sharedViewModel
//        )
    }
}

