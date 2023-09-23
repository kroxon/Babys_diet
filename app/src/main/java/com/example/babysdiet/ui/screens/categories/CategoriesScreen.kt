package com.example.babysdiet.ui.screens.categories

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.babysdiet.R
import com.example.babysdiet.components.ProductItem
import com.example.babysdiet.components.data.models.Product
import com.example.babysdiet.ui.screens.Screen
import com.example.babysdiet.ui.screens.diary.DiaryAppBar
import com.example.babysdiet.ui.screens.diary.DiaryContent
import com.example.babysdiet.ui.screens.diary.displayToast
import com.example.babysdiet.ui.theme.SMALL_PADDING
import com.example.babysdiet.ui.theme.topAppBarBackgroumdColor
import com.example.babysdiet.ui.viewmodels.SharedViewModel
import com.example.babysdiet.util.Action
import com.example.babysdiet.util.RequestState

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun CategoriesScreen(
    navigateToHomeScreen: (Action) -> Unit,
    sharedViewModel: SharedViewModel,
    selectedProductId: Int,
    selectedCategoryId: Int
) {
    LaunchedEffect(key1 = true) {
        sharedViewModel.categorySelection.value = MutableList(12) { false }
        (sharedViewModel.categorySelection.value as MutableList<Boolean>)[selectedCategoryId] = true
        sharedViewModel.getSelectedProducts()
    }

    val selectedProducts by sharedViewModel.selectedProducts.collectAsState()

    Scaffold(
        topBar = {
//            CategoriesAppBar(
//                navigateToHomeScreen = { action ->
//                    if (action == Action.NO_ACTION)
//                        navigateToHomeScreen(action)
//                    else {
//                        if (sharedViewModel.validateDiaryFields())
//                            navigateToHomeScreen(action)
//                        else
//                            displayToast(context = context)
//                    }
//                },
//                selectedDiary = selectedDiary
//            )
        },
        content = {
            CategoriesContent(
                selectedProducts = selectedProducts,
                onProductSelected = {
//                    sharedViewModel.selectedProduct.value = it
                },
                selectedCategoryId = selectedCategoryId,
                selectedProductId = selectedProductId
            )

        }
    )
}