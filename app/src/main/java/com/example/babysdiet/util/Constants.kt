package com.example.babysdiet.util

import androidx.compose.ui.unit.dp

object Constants {
    const val DATABASE_TABLE_PRODUCT = "product_table"
    const val DATABASE_TABLE_DIARY = "diary_table"
    const val DATABASE_NAME = "diet_database"

    const val HOME_SCREEN = "home_screen/{action}"
    const val CATEGORY_SCREEN = "categories_screen/{categoryId}"
    const val PRODUCT_SCREEN = "product_screen/{productId}"
    const val DIARY_SCREEN = "diary_screen/{diaryId}"

    const val HOME_ARGUMENT_KEY = "action"
    const val CATEGORY_ARGUMENT_KEY = "categoryId"
    const val PRODUCT_ARGUMENT_KEY = "productId"
    const val DIARY_ARGUMENT_KEY = "diaryId"

    const val MAX_TITLE_LENGHT = 20
    const val SPLASH_SCREEN_DELAY = 3000L


}