package com.example.babysdiet.ui.screens.categories

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.babysdiet.R
import com.example.babysdiet.components.ProductAdvanceItem
import com.example.babysdiet.components.ProductItem
import com.example.babysdiet.components.data.models.Diary
import com.example.babysdiet.components.data.models.Product
import com.example.babysdiet.ui.theme.LARGE_PADDING
import com.example.babysdiet.ui.theme.SMALL_PADDING
import com.example.babysdiet.ui.theme.TOP_APP_BAR_HEIGHT
import com.example.babysdiet.ui.theme.topAppBarBackgroumdColor
import com.example.babysdiet.util.RequestState

@Composable
fun CategoriesContent(
    selectedProducts: RequestState<List<Product>>,
    allDiaries: RequestState<List<Diary>>,
    onProductSelected: (Product) -> Unit,
    selectedCategoryId: Int,
    selectedProductId: Int
) {

    var allProducts: List<Product> = emptyList()
    if (selectedProducts is RequestState.Success)
        allProducts = selectedProducts.data
    var diaries: List<Diary> = emptyList()
    if (allDiaries is RequestState.Success)
        diaries = allDiaries.data

    Spacer(modifier = Modifier.padding(top = TOP_APP_BAR_HEIGHT))

    LazyColumn(
        modifier = Modifier.fillMaxWidth()
    ) {
        items(allProducts) { product ->
            ProductAdvanceItem(
                product = product,
                diaries = diaries.filter { it.productId == product.productId })
        }
    }

}

//@OptIn(ExperimentalComposeUiApi::class, ExperimentalMaterial3Api::class)
//@Composable
//fun SearchableExposedDropdownMenuBox(
//    allProductsRequest: RequestState<List<Product>>,
//    onProductSelected: (Product) -> Unit
//) {
//    var allProducts: List<Product> = emptyList()
//    if (allProductsRequest is RequestState.Success)
//        allProducts = allProductsRequest.data
//    val context = LocalContext.current
//    var expanded by remember { mutableStateOf(false) }
//    var selectedText by remember { mutableStateOf("") }
//    val keyboardController = LocalSoftwareKeyboardController.current
//    val focusManager = LocalFocusManager.current
//    var isClearButtonVisible by remember { mutableStateOf(false) }
//
//    Box(
//        modifier = Modifier
//            .fillMaxWidth()
//            .padding(start = SMALL_PADDING, end = SMALL_PADDING, bottom = SMALL_PADDING)
//    ) {
//        ExposedDropdownMenuBox(
//            modifier = Modifier.fillMaxWidth(),
//            expanded = expanded,
//            onExpandedChange = {
//                expanded = !expanded
//                if (!expanded) {
//                    keyboardController?.hide() // Hide the keyboard to remove focus
//                    focusManager.clearFocus()
//                    isClearButtonVisible = false
//                }
//            }
//        ) {
//            OutlinedTextField(
//                value = selectedText,
//                onValueChange = {
//                    selectedText = it
//                    isClearButtonVisible = it.isNotEmpty()
//                },
//                label = {
//                    Text(
//                        text = stringResource(id = R.string.search),
//                        modifier = Modifier.padding(start = SMALL_PADDING)
//                    )
//                },
//                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
//                modifier = Modifier
//                    .menuAnchor()
//                    .fillMaxWidth(),
//                shape = RoundedCornerShape(24.dp),
//                colors = OutlinedTextFieldDefaults.colors(
//                    focusedBorderColor = MaterialTheme.colorScheme.topAppBarBackgroumdColor,
//                    focusedTextColor = MaterialTheme.colorScheme.topAppBarBackgroumdColor,
//                    focusedLabelColor = MaterialTheme.colorScheme.topAppBarBackgroumdColor,
//                    unfocusedBorderColor = Color.DarkGray,
//                    unfocusedLabelColor = Color.DarkGray,
//                    unfocusedTextColor = Color.DarkGray
//                )
//            )
//            if (isClearButtonVisible) {
//                Box(
//                    modifier = Modifier
//                        .align(Alignment.CenterEnd)
//                ) {
//                    Icon(
//                        imageVector = Icons.Default.Clear,
//                        contentDescription = null,
//                        modifier = Modifier
//                            .padding(end = 48.dp, top = SMALL_PADDING)
//                            .size(24.dp)
//                            .clickable {
//                                selectedText = ""
//                            },
//                        tint = Color.Gray,
//                    )
//                }
//            }
//
//            val filteredOptions =
//                allProducts.filter { it.name.contains(selectedText, ignoreCase = true) }
//            if (filteredOptions.isNotEmpty()) {
//                ExposedDropdownMenu(
//                    modifier = Modifier.fillMaxWidth(),
//                    expanded = expanded,
//                    onDismissRequest = {
//                        // We shouldn't hide the menu when the user enters/removes any character
////                        keyboardController?.hide() // Hide the keyboard to remove focus
////                        focusManager.clearFocus()
//                        isClearButtonVisible = false
//                    }
//                ) {
//                    filteredOptions.forEach { item ->
//                        DropdownMenuItem(
//                            modifier = Modifier
//                                .fillMaxWidth()
//                                .height(36.dp),
//                            text = {
//                                item.name
//                            },
//                            leadingIcon = {
//                                ProductItem(product = item)
//                            },
//                            onClick = {
//                                onProductSelected(item)
//                                selectedText = ""
//                                expanded = false
//                                Toast.makeText(context, item.name, Toast.LENGTH_SHORT).show()
//                                keyboardController?.hide() // Hide the keyboard to remove focus
//                                focusManager.clearFocus()
//                                isClearButtonVisible = false
//                            }
//                        )
//                    }
//                }
//            }
//        }
//    }
//}

