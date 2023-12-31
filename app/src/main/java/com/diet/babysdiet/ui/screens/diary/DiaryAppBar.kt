package com.diet.babysdiet.ui.screens.diary

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.diet.babysdiet.R
import com.diet.babysdiet.components.DisplayAlertDialog
import com.diet.babysdiet.components.data.models.Diary
import com.diet.babysdiet.ui.theme.topAppBarBackgroumdColor
import com.diet.babysdiet.ui.theme.topAppBarContentColor
import com.diet.babysdiet.util.Action

@Composable
fun DiaryAppBar(
    selectedDiary: Diary?,
    navigateToHomeScreen: (Action) -> Unit
) {
    if (selectedDiary == null) {
        NewDiaryAppBar(navigateToHomeScreen = navigateToHomeScreen)
    } else {
        ExistindDiaryAppBar(
            navigateToHomeScreen = navigateToHomeScreen
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewDiaryAppBar(
    navigateToHomeScreen: (Action) -> Unit
) {
    TopAppBar(
        navigationIcon = {
            BackAction(onBackClicked = navigateToHomeScreen)
        },
        title = { Text(text = stringResource(id = R.string.add_diary)) },
        actions = {
            AddAction(onAddDiaryClicked = navigateToHomeScreen)
        },
        colors = TopAppBarDefaults.mediumTopAppBarColors(
            containerColor = MaterialTheme.colorScheme.topAppBarBackgroumdColor,
            titleContentColor = MaterialTheme.colorScheme.onPrimary,
            navigationIconContentColor = MaterialTheme.colorScheme.onPrimary,
            actionIconContentColor = MaterialTheme.colorScheme.onSecondary
        )
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ExistindDiaryAppBar(
    navigateToHomeScreen: (Action) -> Unit
) {
    TopAppBar(
        navigationIcon = {
            BackAction(onBackClicked = navigateToHomeScreen)
        },
        title = { Text(text = stringResource(id = R.string.edit_diary)) },
        actions = {
            ExistinDiaryAppBarAction(
                navigateToHomeScreen = navigateToHomeScreen
            )
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
fun ExistinDiaryAppBarAction(
    navigateToHomeScreen: (Action) -> Unit
) {
    var openDialog by remember {
        mutableStateOf(false)
    }
    DisplayAlertDialog(
        title = stringResource(id = R.string.delete_diary_ask_title),
        message = stringResource(id = R.string.delete_diary_ask),
        openDialog = openDialog,
        closeDialog = { openDialog = false },
        onYesClicked = { navigateToHomeScreen(Action.DELETE_DIARY) },
        onNoClicked = {})

    DeleteAction(onDeleteeClicked = {
        openDialog = true
    })
    UpdateAction(onUpdateClicked = navigateToHomeScreen)
}

@Composable
fun BackAction(
    onBackClicked: (Action) -> Unit
) {
    IconButton(onClick = { onBackClicked(Action.NO_ACTION) }) {
        Icon(
            imageVector = Icons.Filled.ArrowBack,
            contentDescription = stringResource(id = R.string.arrow_back),
            tint = MaterialTheme.colorScheme.topAppBarContentColor
        )
    }
}

@Composable
fun AddAction(
    onAddDiaryClicked: (Action) -> Unit
) {
    IconButton(onClick = { onAddDiaryClicked(Action.ADD_DIARY) }) {
        Icon(
            imageVector = Icons.Filled.Check,
            contentDescription = stringResource(id = R.string.add_diary),
            tint = MaterialTheme.colorScheme.topAppBarContentColor
        )
    }
}

@Composable
fun UpdateAction(
    onUpdateClicked: (Action) -> Unit
) {
    IconButton(onClick = { onUpdateClicked(Action.UPDATE_DIARY) }) {
        Icon(
            imageVector = Icons.Filled.Check,
            contentDescription = stringResource(id = R.string.update_diary),
            tint = MaterialTheme.colorScheme.topAppBarContentColor
        )
    }
}

@Composable
fun CloseeAction(
    onCloseClicked: (Action) -> Unit
) {
    IconButton(onClick = { onCloseClicked(Action.NO_ACTION) }) {
        Icon(
            imageVector = Icons.Filled.Close,
            contentDescription = stringResource(id = R.string.close_diary),
            tint = MaterialTheme.colorScheme.topAppBarContentColor
        )
    }
}

@Composable
fun DeleteAction(
    onDeleteeClicked: (Action) -> Unit
) {
    IconButton(onClick = { onDeleteeClicked(Action.DELETE_DIARY) }) {
        Icon(
            imageVector = Icons.Filled.Delete,
            contentDescription = stringResource(id = R.string.delete_diary),
            tint = MaterialTheme.colorScheme.topAppBarContentColor
        )
    }
}

@Composable
@Preview
fun NewDiaryAppBarPrewiew() {
    NewDiaryAppBar(navigateToHomeScreen = {})
}

//@Composable
//@Preview
//fun ExistindDiaryAppBarPrewiew() {
//    ExistindDiaryAppBar(
//        navigateToHomeScreen = {},
//        selectedProduct = Product(
//            productId = 0,
//            name = "Milk",
//            categoryId = 1,
//            description = "description",
//            isAllergen = true
//        )
//    )
//}