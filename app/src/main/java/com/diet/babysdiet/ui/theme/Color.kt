package com.diet.babysdiet.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

val Purple80 = Color(0xFFD0BCFF)
val PurpleGrey80 = Color(0xFFCCC2DC)
val Pink80 = Color(0xFFEFB8C8)

val Purple40 = Color(0xFF6650a4)
val PurpleGrey40 = Color(0xFF625b71)
val Pink40 = Color(0xFF7D5260)

val Blue1 = Color(0XFF1565C0)
val Blue2 = Color(0xFF698FBB)

val ExcellentEvaluationColor = Color(0xFF078107)
val FineEvaluationColor = Color(0xFF66FF33)
val NeutralEvaluationColor = Color(0xFFFFFF00)
val BadEvaluationColor = Color(0xFFFF6600)
val VeryBadEvaluationColor = Color(0xFFFF0000)

val LightGrey = Color(0XFFFCFCFC)
val MediumGrey = Color(0XFF9C9C9C)
val DarkGrey = Color(0xFF2E2E2E)

val ColorScheme.diaryItemTextColor: Color
    @Composable
    get() = if (isSystemInDarkTheme()) Color.LightGray else Color.DarkGray

val ColorScheme.diaryItembackgroudColor: Color
    @Composable
    get() = if (isSystemInDarkTheme()) Color.DarkGray else Color.White

val ColorScheme.fabContentColor: Color
    @Composable
    get() = if (isSystemInDarkTheme()) Color.LightGray else Blue1

val ColorScheme.topAppBarContentColor: Color
    @Composable
    get() = if (isSystemInDarkTheme()) Color.LightGray else Color.White

val ColorScheme.topAppBarBackgroumdColor: Color
    @Composable
    get() = if (isSystemInDarkTheme()) Color.Black else Blue1

val ColorScheme.buttonBackgroumdColor: Color
    @Composable
    get() = if (isSystemInDarkTheme()) Color.Black else Blue1
