package com.diet.babysdiet.ui.screens.categories

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.diet.babysdiet.components.ProductAdvanceItem
import com.diet.babysdiet.components.data.models.Diary
import com.diet.babysdiet.components.data.models.Evaluation
import com.diet.babysdiet.components.data.models.Product
import com.diet.babysdiet.ui.theme.SMALL_PADDING
import com.diet.babysdiet.util.RequestState

@Composable
fun CategoriesContent(
    selectedProducts: RequestState<List<Product>>,
    allDiaries: RequestState<List<Diary>>,
    navigateToProductScreen: (productId: Int) -> Unit,
    selectedCategoryId: Int,
    selectedProductId: Int,
    paddings: PaddingValues
) {

    var allProducts: List<Product> = emptyList()
    if (selectedProducts is RequestState.Success) allProducts = selectedProducts.data

    var diaries: List<Diary> = emptyList()
    if (allDiaries is RequestState.Success) diaries = allDiaries.data

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(paddings)
    ) {
//        SpacerTopAppBar()
//        Card(
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(top = SMALL_PADDING, start = SMALL_PADDING, end = SMALL_PADDING)
//                .shadow(ambientColor = Color.Blue, elevation = 15.dp)
//        ) {

        LazyColumn(
            modifier = Modifier
                .fillMaxWidth()
//                .padding(top = SMALL_PADDING, start = SMALL_PADDING, end = SMALL_PADDING)
//                .shadow(ambientColor = Color.Blue, elevation = 15.dp)
                .padding(SMALL_PADDING)
        ) {
            items(allProducts) { product ->
                ProductAdvanceItem(
                    product = product,
                    diaries = diaries.filter { it.productId == product.productId },
                    navigateToProductScreen = navigateToProductScreen
                )
            }
        }
//        }
    }
}

@Composable
@Preview
fun CategoriesContentPreview() {
    CategoriesContent(
        selectedProducts = RequestState.Success(
            listOf(
                Product(
                    name = "milk",
                    categoryId = 1,
                    isAllergen = false,
                    description = "description",
                    productId = 1
                ),
                Product(
                    name = "milk2",
                    categoryId = 1,
                    isAllergen = false,
                    description = "description",
                    productId = 2
                ),
                Product(
                    name = "milk3",
                    categoryId = 1,
                    isAllergen = false,
                    description = "description",
                    productId = 3
                ),
                Product(
                    name = "milk",
                    categoryId = 1,
                    isAllergen = false,
                    description = "description",
                    productId = 1
                ),
                Product(
                    name = "milk2",
                    categoryId = 1,
                    isAllergen = false,
                    description = "description",
                    productId = 2
                ),                Product(
                    name = "milk",
                    categoryId = 1,
                    isAllergen = false,
                    description = "description",
                    productId = 1
                ),
                Product(
                    name = "milk2",
                    categoryId = 1,
                    isAllergen = false,
                    description = "description",
                    productId = 2
                ),                Product(
                    name = "milk",
                    categoryId = 1,
                    isAllergen = false,
                    description = "description",
                    productId = 1
                ),
                Product(
                    name = "milk2",
                    categoryId = 1,
                    isAllergen = false,
                    description = "description",
                    productId = 2
                )
            )
        ),
        allDiaries = RequestState.Success(
            listOf(
                Diary(
                    0,
                    1700652588,
                    2,
                    true,
                    "desc",
                    Evaluation.EXCELLENT,
                    true,
                    true,
                    true,
                    true,
                    true,
                    true
                ),
                Diary(
                    0,
                    1700652588,
                    2,
                    true,
                    "desc",
                    Evaluation.EXCELLENT,
                    true,
                    true,
                    true,
                    true,
                    true,
                    true
                ),
                Diary(
                    0,
                    1700652588,
                    2,
                    true,
                    "desc",
                    Evaluation.EXCELLENT,
                    true,
                    true,
                    true,
                    true,
                    true,
                    true
                ),
                Diary(
                    0,
                    1700652588,
                    2,
                    true,
                    "desc",
                    Evaluation.EXCELLENT,
                    true,
                    true,
                    true,
                    true,
                    true,
                    true
                ),
                Diary(
                    0,
                    1700652588,
                    2,
                    true,
                    "desc",
                    Evaluation.EXCELLENT,
                    true,
                    true,
                    true,
                    true,
                    true,
                    true
                ),
                Diary(
                    0,
                    1700652589,
                    2,
                    true,
                    "desc",
                    Evaluation.NEUTRAL,
                    true,
                    true,
                    true,
                    true,
                    true,
                    true
                ),
                Diary(
                    0,
                    1700652587,
                    2,
                    true,
                    "desc",
                    Evaluation.BAD,
                    true,
                    true,
                    true,
                    true,
                    true,
                    true
                )
            )
        ),
        navigateToProductScreen = {},
        selectedCategoryId = 1,
        selectedProductId = 1,
        paddings = PaddingValues()
    )
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

