package com.example.babysdiet.ui.screens

import com.example.babysdiet.util.Constants.DIARY_ARGUMENT_KEY
import com.example.babysdiet.util.Constants.PRODUCT_ARGUMENT_KEY


sealed class Screen(val route: String) {
    object Home : Screen(route = "home_screen")
    object Categories : Screen(route = "categories_screen")
//    object Categories : Screen(
//        route = "categories_screen/{$CATEGORY_ARGUMENT_KEY}/{$CATEGORY_ARGUMENT_KEY2}") {

////        for one argument
//        fun passCategoryId(id: Int): String {
//            return "categories_screen/$id"
//        }

////        for two arguments
//        fun passCategoryIdAndName(
//            id: Int,
//            name: String
//        ): String {
//            return "categories_screen/$id/$name"
//        }
//    }
    object Products: Screen(
        route = "product_screen/{$PRODUCT_ARGUMENT_KEY}") {
//                for one argument
        fun passProductId(id: Int): String {
            return "product_screen/$id"
        }
    }

    object Diary: Screen(
        route = "history_screen/{$DIARY_ARGUMENT_KEY}"){
            fun passHistoryId(id: Int): String {
                return "history_screen/$id"
            }
        }
}
