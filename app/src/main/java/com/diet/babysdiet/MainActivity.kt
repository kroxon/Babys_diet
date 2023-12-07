package com.diet.babysdiet

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material3.MaterialTheme
import androidx.core.view.WindowCompat
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.diet.babysdiet.components.SetStatusBarColor
import com.diet.babysdiet.navigation.SetupNavGraph
import com.diet.babysdiet.ui.theme.BabysDietTheme
import com.diet.babysdiet.ui.theme.topAppBarBackgroumdColor
import com.diet.babysdiet.ui.viewmodels.SharedViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val sharedViewModel: SharedViewModel by viewModels()
    lateinit var navController: NavHostController

    // Turn off the decor fitting system windows

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContent {
            BabysDietTheme {
                SetStatusBarColor(color = MaterialTheme.colorScheme.topAppBarBackgroumdColor)
                navController = rememberNavController()
                SetupNavGraph(
                    navController = navController,
                    sharedViewModel = sharedViewModel
                )

            }
        }
    }
}

