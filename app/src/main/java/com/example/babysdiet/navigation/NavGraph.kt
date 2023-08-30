package com.example.babysdiet.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.compose.NavHost
import androidx.navigation.NavHostController

import com.example.babysdiet.navigation.destinations.categoriesComposable
import com.example.babysdiet.navigation.destinations.diaryComposable
import com.example.babysdiet.navigation.destinations.homeComposable
//import com.example.babysdiet.navigation.destinations.productComposable
import com.example.babysdiet.ui.viewmodels.SharedViewModel
import com.example.babysdiet.util.Constants.HOME_SCREEN

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun SetupNavGraph(
    navController: NavHostController,
    sharedViewModel: SharedViewModel
) {
    val screen = remember(navController) {
        Screens(navController = navController)
    }

    NavHost(
        navController = navController,
        startDestination = HOME_SCREEN
    ) {
        homeComposable(
            navigateToCategoryScreen = screen.categories,
            navigateToDiaryScreen = screen.diary,
            sharedViewModel = sharedViewModel
        )
        diaryComposable(
            navigateToHomeScreen = screen.home,
            sharedViewModel = sharedViewModel
        )
        categoriesComposable(
            navigateToHomeScreen = screen.home,
//            navigateToProductScreen = screen.product,
            sharedViewModel = sharedViewModel
        )
//        productComposable(
////            navigateToCategoryScreen = screen.categories,
//            sharedViewModel = sharedViewModel
//        )
    }

//    AnimatedNavHost(
//        navController = navController,
//        startDestination = Screen.Home.route
//    ) {
//        composable(
//            route = Screen.Home.route
//        ) {
//            HomeScreen(navController)
//        }
//        composable(
//            route = Screen.Categories.route
////            ,
////            arguments = listOf(
////                navArgument(CATEGORY_ARGUMENT_KEY) {
////                    type = NavType.IntType
////                },
////                navArgument(CATEGORY_ARGUMENT_KEY2) {
////                    type = NavType.StringType
////                })
//        ) {
//            CategoriesScreen(navController)
//        }
//        composable(
//            route = Screen.Products.route,
//            arguments = listOf(
//                navArgument(PRODUCT_ARGUMENT_KEY) {
//                    type = NavType.IntType
//                })
//        ) {
//            Log.d("Arguments", it.arguments?.getInt(PRODUCT_ARGUMENT_KEY).toString())
//            ProductsScreen(navController)
//        }
//
//        composable(
//            route = Screen.Diary.route,
//            arguments = listOf(
//                navArgument(DIARY_ARGUMENT_KEY) {
//                    type = NavType.IntType
//                })
//        ) {
//            Log.d("Arguments", it.arguments?.getInt(DIARY_ARGUMENT_KEY).toString())
//            HistoryScreen(navController)
//        }
//    }
}