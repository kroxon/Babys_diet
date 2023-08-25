package com.example.babysdiet.ui.screens.diary

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.babysdiet.R
import com.example.babysdiet.ui.theme.fabContentColor
import com.example.babysdiet.ui.viewmodels.SharedViewModel
import com.example.babysdiet.util.Action

//@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun DiaryScreen(
    navigateToHomeScreen: (Action) -> Unit,
    sharedViewModel: SharedViewModel
) {
    LaunchedEffect(key1 = true) {
        sharedViewModel.getAllProducts()
    }
    LaunchedEffect(key1 = true) {
        sharedViewModel.getAllDiaries()
    }
    val allProducts by sharedViewModel.allProducts.collectAsState()
    val allDiaries by sharedViewModel.allDiaries.collectAsState()

    Text(
        modifier = Modifier.fillMaxSize(),
        text = "Diary"
    )

}

