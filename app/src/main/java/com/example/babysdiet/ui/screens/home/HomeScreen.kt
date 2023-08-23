package com.example.babysdiet.ui.screens.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.babysdiet.ui.screens.Screen
import com.example.babysdiet.ui.viewmodels.SharedViewModel

@Composable
fun HomeScreen(
    navigateToCategoryScreen: (categoryId: Int) -> Unit,
    navigateToDiaryScreen: (diaryId: Int) -> Unit
//    ,
//    sharedViewModel: SharedViewModel
) {


}

@Composable
@Preview(showBackground = true)
fun HomeScreenPreview() {
    HomeScreen(
        navigateToCategoryScreen = {},
        navigateToDiaryScreen = {}
//        ,
//        sharedViewModel = sharedViewModel
    )
}