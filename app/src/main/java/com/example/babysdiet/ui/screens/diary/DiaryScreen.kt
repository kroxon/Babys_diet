package com.example.babysdiet.ui.screens.diary

import android.annotation.SuppressLint
import android.content.Context
import android.widget.Toast
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import com.example.babysdiet.R
import com.example.babysdiet.components.data.models.Diary
import com.example.babysdiet.ui.viewmodels.SharedViewModel
import com.example.babysdiet.util.Action
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun DiaryScreen(
    navigateToHomeScreen: (Action) -> Unit,
    sharedViewModel: SharedViewModel,
    selectedDiary: Diary?,
) {
    val context = LocalContext.current

    LaunchedEffect(key1 = true) {
        sharedViewModel.getAllProducts()
    }
    LaunchedEffect(key1 = true) {
        sharedViewModel.getAllDiaries()
    }
    sharedViewModel.getSelectedProducts()

    val action by sharedViewModel.action

    val allProducts by sharedViewModel.allProducts.collectAsState()
    val allDiaries by sharedViewModel.allDiaries.collectAsState()
    val selectedProducts by sharedViewModel.selectedProducts.collectAsState()

    val selectedDiaryProduct by sharedViewModel.selectedDiaryProduct

    // product
//    val idProduct by sharedViewModel.idProduct
//    val idCategoryProduct by sharedViewModel.idCategoryProduct
//    val nameProduct by sharedViewModel.nameProduct
//    val descriptionProduct by sharedViewModel.descriptionProduct
//    val isAllergenProduct by sharedViewModel.isAllergenProduct
    val diaryDescription by sharedViewModel.diaryDescription

    // evaluation
    val evaluation by sharedViewModel.evaluationDiary

    // food activities
//    val foodActivitiesList by sharedViewModel.foodActivities

    val snackbarHostState = remember { SnackbarHostState() }

    DisplaySnackbar(
        handleDatabaseAction = { sharedViewModel.handleDatabaseActions(action = action) },
        snackbarHostState = snackbarHostState,
        action = action,
        onUndoClicked = {
            sharedViewModel.action.value = it
        }
    )

    Scaffold(
        topBar = {
            DiaryAppBar(
                navigateToHomeScreen = { action ->
                    if (action == Action.NO_ACTION)
                        navigateToHomeScreen(action)
                    else {
                        if (sharedViewModel.validateDiaryFields())
                            navigateToHomeScreen(action)
                        else
                            displayToast(context = context)
                    }
                },
                selectedDiary = selectedDiary,
                selectedProduct = selectedDiaryProduct,
                sharedViewModel = sharedViewModel
            )
        },
        content = {
            DiaryContent(
                allProducts = allProducts,
                selectedDiary = selectedDiary,
                selectedProduct = selectedDiaryProduct,
                sharedViewModel = sharedViewModel,
                selectedProducts = selectedProducts,
                onProductSelected = {
//                    sharedViewModel.idProduct.value = it.productId
//                    sharedViewModel.idCategoryProduct.value = it.categoryId
//                    sharedViewModel.nameProduct.value = it.name
//                    sharedViewModel.descriptionProduct.value = it.description
//                    sharedViewModel.isAllergenProduct.value = it.isAllergen

                    sharedViewModel.selectedDiaryProduct.value = it
                },
//                nameProduct = selectedProduct,
                onEvaluationSelected = { sharedViewModel.evaluationDiary.value = it },
                onActivitySelected = {
                    sharedViewModel.foodActivities.value = it
                },
                onSelectedSymptoms = { sharedViewModel.diarySympotomsOccured.value = it },
                diaryDescription = diaryDescription,
                onDescriptionChange = { sharedViewModel.diaryDescription.value = it },
                onDateSelected = { sharedViewModel.selectedDate.value = it }
            )

        }
    )
}

fun displayToast(context: Context) {
    Toast.makeText(
        context, context.getString(R.string.selectProduct),
        Toast.LENGTH_SHORT
    ).show()
}

@Composable
fun DisplaySnackbar(
    onUndoClicked: (Action) -> Unit,
    handleDatabaseAction: () -> Unit,
    snackbarHostState: SnackbarHostState,
    action: Action
) {
    handleDatabaseAction()
    val scope = rememberCoroutineScope()
    LaunchedEffect(key1 = action) {
        if (action != Action.NO_ACTION) {
            scope.launch {
                val snackBarResult = snackbarHostState.showSnackbar(
                    message = setMessage(
                        action = action
                    ),
                    actionLabel = setActionLabel(action)
                )
                undoDeleteDiary(
                    action = action,
                    snackBarResult = snackBarResult,
                    onUndoClicked = onUndoClicked
                )
            }
        }
    }
}

private fun setMessage(action: Action): String {
    return when (action) {
        Action.DELETE_ALL_DIARIES -> "All diet diary entries deleted."
        else -> "${action.name}"
    }
}

private fun setActionLabel(action: Action): String {
    return if (action == Action.DELETE_DIARY) "UNDO" else "OK"
}

private fun undoDeleteDiary(
    action: Action,
    snackBarResult: SnackbarResult,
    onUndoClicked: (Action) -> Unit
) {
    if (snackBarResult == SnackbarResult.ActionPerformed &&
        action == Action.DELETE_DIARY
    )
        onUndoClicked(Action.UNDO_DIARY)
}

