package com.example.babysdiet.ui.screens.product

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.babysdiet.ui.screens.Screen

@Composable
fun ProductsScreen(
    navController: NavController
) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Text(
            modifier = Modifier.clickable {
                navController.navigate(Screen.Categories.route) {
                    popUpTo(Screen.Categories.route){
                        inclusive = true
                    }
                }
            },
            text = "Products"
        )
    }
}

@Composable
@Preview(showBackground = true)
fun ProductsScreenPreview() {
    ProductsScreen(navController = rememberNavController())
}