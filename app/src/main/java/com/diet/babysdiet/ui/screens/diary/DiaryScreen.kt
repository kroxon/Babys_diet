package com.diet.babysdiet.ui.screens.diary

import android.annotation.SuppressLint
import android.content.Context
import android.widget.Toast
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import com.diet.babysdiet.R
import com.diet.babysdiet.components.data.models.Diary
import com.diet.babysdiet.components.data.models.Evaluation
import com.diet.babysdiet.components.data.models.Product
import com.diet.babysdiet.ui.viewmodels.SharedViewModel
import com.diet.babysdiet.util.Action

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
//    val selectedCategories by sharedViewModel.categorySelection.collectAsState()


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
                onSelectedSymptoms = {
                    sharedViewModel.diarySympotomsOccured.value = it },
                diaryDescription = diaryDescription,
                onDescriptionChange = { sharedViewModel.diaryDescription.value = it },
                onDateSelected = { sharedViewModel.selectedDate.value = it },
                onButtonClickListener = {
                    sharedViewModel.categorySelection.value = it
                    sharedViewModel.getSelectedProducts()
                },
                onSaveAsAllergen = {sharedViewModel.saveProductAsAllergen.value = it}
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




