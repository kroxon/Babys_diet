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
import com.example.babysdiet.components.data.models.Evaluation
import com.example.babysdiet.components.data.models.Product
import com.example.babysdiet.ui.viewmodels.SharedViewModel
import com.example.babysdiet.util.Action
import kotlinx.coroutines.launch
import java.time.LocalDate

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun DiaryScreen(
    navigateToHomeScreen: (Action) -> Unit,
    sharedViewModel: SharedViewModel,
    selectedDiary: Diary?,
    selectedProduct: Product?
) {
    val context = LocalContext.current

    val evaluation: Evaluation by sharedViewModel.evaluationDiary
    val foodActivities: List<Boolean> by sharedViewModel.foodActivities
    val diarySympotomsOccured: Boolean by sharedViewModel.diarySympotomsOccured
    val diaryDescription: String by sharedViewModel.diaryDescription
    val selectedDiaryProduct: Product? by sharedViewModel.selectedDiaryProduct
    val selectedDate: Long by sharedViewModel.selectedDate

    LaunchedEffect(key1 = true) {
        sharedViewModel.getAllProducts()
    }
    LaunchedEffect(key1 = true) {
        sharedViewModel.getAllDiaries()
    }
    sharedViewModel.getSelectedProducts()


    val allProducts by sharedViewModel.allProducts.collectAsState()
    val allDiaries by sharedViewModel.allDiaries.collectAsState()
    val selectedProducts by sharedViewModel.selectedProducts.collectAsState()


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
                selectedDiary = selectedDiary
            )
        },
        content = {
            DiaryContent(
                selectedDiary = selectedDiary,
                evaluation = evaluation,
                foodActivities = foodActivities,
                selectedProduct = selectedDiaryProduct,
                selectedDate = selectedDate,
                selectedProducts = selectedProducts,
                diarySympotomsOccured = diarySympotomsOccured,
                onProductSelected = {
                    sharedViewModel.selectedDiaryProduct.value = it
                },
                onEvaluationSelected = { sharedViewModel.evaluationDiary.value = it },
                onActivitySelected = {
                    sharedViewModel.foodActivities.value = it
                },
                onSelectedSymptoms = { sharedViewModel.diarySympotomsOccured.value = it },
                diaryDescription = diaryDescription,
                onDescriptionChange = { sharedViewModel.diaryDescription.value = it },
                onDateSelected = { sharedViewModel.selectedDate.value = it },
                onButtonClickListener = {
                    sharedViewModel.categorySelection.value = it
                    sharedViewModel.getSelectedProducts()
                }
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

