package com.diet.babysdiet.ui.screens.home

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.diet.babysdiet.R
import com.diet.babysdiet.ui.theme.topAppBarBackgroumdColor
import com.diet.babysdiet.ui.theme.topAppBarContentColor

@Composable
fun HomeAppBar(
) {
    DefaultHomeAppBar(
        onSearchClicked = {})
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DefaultHomeAppBar(
    onSearchClicked: () -> Unit
) {
    TopAppBar(
        title = {
            Text(
                text = stringResource(id = R.string.home),
                color = MaterialTheme.colorScheme.topAppBarContentColor
            )
        },
        actions = {
//            HomeAppBarActions(onSearchClicked = onSearchClicked)
        },
        colors = TopAppBarDefaults.mediumTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.topAppBarBackgroumdColor,
            titleContentColor = MaterialTheme.colorScheme.onPrimary,
            navigationIconContentColor = MaterialTheme.colorScheme.onPrimary,
            actionIconContentColor = MaterialTheme.colorScheme.onSecondary
        )
    )
}

@Composable
fun HomeAppBarActions(
    onSearchClicked: () -> Unit
) {
    SearchAction(onSearchClicked = onSearchClicked)
}


@Composable
fun SearchAction(
    onSearchClicked: () -> Unit
) {
    IconButton(
        onClick = { onSearchClicked() }
    ) {
        Icon(
            imageVector = Icons.Filled.Search,
            contentDescription = stringResource(id = R.string.search_action),
            tint = MaterialTheme.colorScheme.topAppBarContentColor
        )
    }
}

@Composable
@Preview
private fun DefaultHomeAppBarPreview() {
    DefaultHomeAppBar(
        onSearchClicked = {}
    )
}

//@Composable
//@Preview
//private fun SearchAppBarPreview() {
//    SearchAppBar(
//        text = "",
//        onTextChange = {},
//        onCloseClicked = { /*TODO*/ },
//        onSearchClicked = {}
//    )
//}