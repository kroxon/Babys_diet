package com.example.babysdiet.components

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.babysdiet.ui.theme.BIG_TOP_APP_BAR_HEIGHT_LARGE

@Composable
fun SpacerTopAppBar() {
    Spacer(modifier = Modifier.height(BIG_TOP_APP_BAR_HEIGHT_LARGE))
}