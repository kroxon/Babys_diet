package com.example.babysdiet.ui.screens.diary

import android.app.DatePickerDialog
import android.widget.DatePicker
import android.widget.Toast
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.babysdiet.R
import com.example.babysdiet.components.EvaluationSelectingRow
import com.example.babysdiet.components.ProductItem
import com.example.babysdiet.components.data.models.Diary
import com.example.babysdiet.components.data.models.Evaluation
import com.example.babysdiet.components.data.models.Product
import com.example.babysdiet.ui.theme.FOOD_ACTIVITIES_HEIGHT
import com.example.babysdiet.ui.theme.LARGE_PADDING
import com.example.babysdiet.ui.theme.MediumGrey
import com.example.babysdiet.ui.theme.OUTLINEDBUTTON_HEIGHT
import com.example.babysdiet.ui.theme.SMALL_PADDING
import com.example.babysdiet.ui.theme.TOP_APP_BAR_HEIGHT
import com.example.babysdiet.ui.theme.VERY_SMALL_PADDING
import com.example.babysdiet.ui.theme.buttonBackgroumdColor
import com.example.babysdiet.ui.theme.topAppBarBackgroumdColor
import com.example.babysdiet.ui.viewmodels.SharedViewModel
import com.example.babysdiet.util.RequestState
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Calendar
import java.util.Locale

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DiaryContent(
    selectedDiary: Diary?,
    selectedProduct: Product?,
    allProducts: RequestState<List<Product>>,
    sharedViewModel: SharedViewModel,
    selectedProducts: RequestState<List<Product>>,
    onProductSelected: (Product) -> Unit,
    onEvaluationSelected: (Evaluation) -> Unit,
    onActivitySelected: (List<Boolean>) -> Unit,
    onSelectedSymptoms: (Boolean) -> Unit,
    onDescriptionChange: (String) -> Unit,
    diaryDescription: String,
    onDateSelected: (Long) -> Unit,
//    idProduct: Int,
//    nameProduct: String,
//    descriptionProduct: String,
//    isAllergenProduct: Boolean
) {
    var newSelectedProduct = selectedProduct

    // for calendar
    var currentDate by remember { mutableStateOf(LocalDate.now()) }
    val context = LocalContext.current
    val calendar = Calendar.getInstance()

    var selectedDateText by remember {
        mutableStateOf(currentDate.format(DateTimeFormatter.ofPattern("dd.MM.yyyy")))
    }
    // Fetching current year, month and day
    val year = calendar[Calendar.YEAR]
    val month = calendar[Calendar.MONTH]
    val dayOfMonth = calendar[Calendar.DAY_OF_MONTH]

    val datePicker = DatePickerDialog(
        context,
        { _: DatePicker, selectedYear: Int, selectedMonth: Int, selectedDayOfMonth: Int ->
            selectedDateText = "$selectedDayOfMonth/${selectedMonth + 1}/$selectedYear"
            currentDate = LocalDate.of(selectedYear, selectedMonth + 1, selectedDayOfMonth)
            onDateSelected(currentDate.toEpochDay())
        }, year, month, dayOfMonth

    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(LARGE_PADDING)
    ) {
        Spacer(modifier = Modifier.padding(top = TOP_APP_BAR_HEIGHT))

        SearchableExposedDropdownMenuBox(
            allProductsRequest = selectedProducts,
            onProductSelected = onProductSelected
        )

        // mutable list of selected category
        val categories: List<String> = stringArrayResource(id = R.array.categories_array).toList()
        var categorySelectedBtns = remember {
            mutableStateListOf<Boolean>().apply {
                addAll(List(categories.size) { true })
            }
        }
        FlowRowButtons(
            names = categories,
            completedList = categorySelectedBtns,
            sharedViewModel = sharedViewModel
        )

        if (newSelectedProduct != null) {
            TitleLabel(newSelectedProduct)

            EvaluationSelectingRow(onEvaluationSelected = onEvaluationSelected)

            FoodActivities(onActivitySelected = onActivitySelected)

            AllergySymptomsOccured(onSelectedSymptoms = onSelectedSymptoms)

            CalendarLabel(currentDate, datePicker)

            OutlinedTextField(
                value = diaryDescription,
                onValueChange = { onDescriptionChange(it) },
                modifier = Modifier
                    .fillMaxSize(),
                label = { Text(text = stringResource(id = R.string.description)) },
                maxLines = 1,
                textStyle = MaterialTheme.typography.bodyLarge,
                colors = TextFieldDefaults.outlinedTextFieldColors(
                    unfocusedBorderColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.34f)
                )
            )
        }

    }
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun SearchableExposedDropdownMenuBox(
    allProductsRequest: RequestState<List<Product>>,
    onProductSelected: (Product) -> Unit
) {
    var allProducts: List<Product> = emptyList()
    if (allProductsRequest is RequestState.Success)
        allProducts = allProductsRequest.data
    val context = LocalContext.current
    val coffeeDrinks = arrayOf("Americano", "Cappuccino", "Espresso", "Latte", "Mocha")
    var expanded by remember { mutableStateOf(false) }
    var selectedText by remember { mutableStateOf("") }
    val keyboardController = LocalSoftwareKeyboardController.current
    val focusManager = LocalFocusManager.current
    var isClearButtonVisible by remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = SMALL_PADDING, end = SMALL_PADDING, bottom = SMALL_PADDING)
    ) {
        ExposedDropdownMenuBox(
            modifier = Modifier.fillMaxWidth(),
            expanded = expanded,
            onExpandedChange = {
                expanded = !expanded
                if (!expanded) {
                    keyboardController?.hide() // Hide the keyboard to remove focus
                    focusManager.clearFocus()
                    isClearButtonVisible = false
                }
            }
        ) {
            OutlinedTextField(
                value = selectedText,
                onValueChange = {
                    selectedText = it
                    isClearButtonVisible = it.isNotEmpty()
                },
                label = {
                    Text(
                        text = stringResource(id = R.string.search),
                        modifier = Modifier.padding(start = SMALL_PADDING)
                    )
                },
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                modifier = Modifier
                    .menuAnchor()
                    .fillMaxWidth(),
                shape = RoundedCornerShape(24.dp),
                colors = OutlinedTextFieldDefaults.colors(
                    focusedBorderColor = MaterialTheme.colorScheme.topAppBarBackgroumdColor,
                    focusedTextColor = MaterialTheme.colorScheme.topAppBarBackgroumdColor,
                    focusedLabelColor = MaterialTheme.colorScheme.topAppBarBackgroumdColor,
                    unfocusedBorderColor = Color.DarkGray,
                    unfocusedLabelColor = Color.DarkGray,
                    unfocusedTextColor = Color.DarkGray
                )
            )
            if (isClearButtonVisible) {
                Box(
                    modifier = Modifier
                        .align(Alignment.CenterEnd)
                ) {
                    Icon(
                        imageVector = Icons.Default.Clear,
                        contentDescription = null,
                        modifier = Modifier
                            .padding(end = 48.dp, top = SMALL_PADDING)
                            .size(24.dp)
                            .clickable {
                                selectedText = ""
                            },
                        tint = Color.Gray,
                    )
                }
            }

            val filteredOptions =
                allProducts.filter { it.name.contains(selectedText, ignoreCase = true) }
            if (filteredOptions.isNotEmpty()) {
                ExposedDropdownMenu(
                    modifier = Modifier.fillMaxWidth(),
                    expanded = expanded,
                    onDismissRequest = {
                        // We shouldn't hide the menu when the user enters/removes any character
                        keyboardController?.hide() // Hide the keyboard to remove focus
                        focusManager.clearFocus()
                        isClearButtonVisible = false
                    }
                ) {
                    filteredOptions.forEach { item ->
                        DropdownMenuItem(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(36.dp),
                            text = {
                                item.name
                            },
                            leadingIcon = {
                                ProductItem(product = item)
                            },
                            onClick = {
                                selectedText = item.name
                                expanded = false
                                Toast.makeText(context, item.name, Toast.LENGTH_SHORT).show()
                                keyboardController?.hide() // Hide the keyboard to remove focus
                                focusManager.clearFocus()
                                isClearButtonVisible = false
                                onProductSelected(item)
                            }
                        )
                    }
                }
            }
        }
    }
}

//@Composable
//fun PillButtonGrid(names: List<String>, sharedViewModel: SharedViewModel) {
//    LazyVerticalGrid(
//        columns = GridCells.Adaptive(minSize = 50.dp)
//    ) {
//        itemsIndexed(items = names) { index: Int, item: String ->
//            PastilleButton(name = item, completedList = completed, index = index)
//        }
//    }
//}


@Composable
fun PastilleButton(
    name: String,
    completedList: MutableList<Boolean>,
    index: Int,
    sharedViewModel: SharedViewModel
) {
//    var isSelected by rememberSaveable { mutableStateOf(completedList[index]) }
    var isSelected = completedList[index]

    val backgroundColor by animateColorAsState(
        if (isSelected)
            MaterialTheme.colorScheme.buttonBackgroumdColor
        else
            Color.White
    )
    val textColor by animateColorAsState(
        if (isSelected)
            Color.White
        else
            MaterialTheme.colorScheme.buttonBackgroumdColor
    )
    val outlineColor = MaterialTheme.colorScheme.buttonBackgroumdColor

    Box(
        Modifier
            .padding(VERY_SMALL_PADDING)
//            .wrapContentWidth()
    ) {


        OutlinedButton(
            onClick = {
                isSelected = !isSelected
                completedList[index] = isSelected
                if (index == 0 && isSelected) {
                    completedList.replaceAll { true }

                }
                if (index == 0 && !isSelected) {
                    completedList.replaceAll { false }
                }
                if (index != 0 && completedList.drop(1).all { it }) {
                    completedList.replaceAll { true }
                }
                if (index != 0 && !isSelected) {
                    completedList[0] = false
                }
                sharedViewModel.categorySelection.value = completedList
                sharedViewModel.getSelectedProducts()
            },
            contentPadding = PaddingValues(
                start = 8.dp,
                top = 0.dp,
                end = 8.dp,
                bottom = 0.dp,
            ),
            shape = RoundedCornerShape(8.dp),
//        modifier = Modifier.wrapContentWidth(),
            colors = ButtonDefaults.outlinedButtonColors(
                containerColor = backgroundColor,
                contentColor = textColor
            ),
            border = BorderStroke(
                width = 1.dp,
                color = outlineColor
            ),
            modifier = Modifier
                .height(OUTLINEDBUTTON_HEIGHT)
                .wrapContentWidth()
                .widthIn(min = 32.dp)
        ) {
            Text(
                text = name,
                fontSize = MaterialTheme.typography.labelMedium.fontSize,
                maxLines = 1,
                modifier = Modifier.wrapContentWidth()
            )
        }
    }

}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun FlowRowButtons(
    names: List<String>,
    completedList: MutableList<Boolean>,
    sharedViewModel: SharedViewModel
) {
    FlowRow(
        horizontalArrangement = Arrangement.Center,
    ) {
        repeat(names.size) { i ->
            PastilleButton(
                name = names.get(i),
                completedList = completedList,
                index = i,
                sharedViewModel = sharedViewModel
            )
        }
    }
}

@Composable
fun TitleLabel(
    product: Product
) {
    Column(Modifier.fillMaxWidth()) {
        Row(
            Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Text(
                text = product.name,
                style = TextStyle(
                    fontSize = MaterialTheme.typography.displaySmall.fontSize,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                ),
                modifier = Modifier.align(Alignment.CenterVertically)
            )
            if (product.isAllergen) {
                Image(
                    painter = painterResource(id = R.drawable.ic_alert),
                    contentDescription = "allergen alert",
                    modifier = Modifier
                        .padding(
                            top = SMALL_PADDING,
                            start = SMALL_PADDING
                        )
                        .size(36.dp)
                )
            }
        }
    }
}

@Composable
fun FoodActivities(
    onActivitySelected: (List<Boolean>) -> Unit
) {
    var selectedActivities by remember { mutableStateOf<List<Boolean>>(List(6) { false }) }
    val strings = listOf(
        stringResource(id = R.string.touched),
        stringResource(id = R.string.sniffed),
        stringResource(id = R.string.licked),
        stringResource(id = R.string.firstAttempt),
        stringResource(id = R.string.secondAttempt),
        stringResource(id = R.string.thirdAttempt)
    )

    LazyHorizontalGrid(
        rows = GridCells.Fixed(3),
        // content padding
        contentPadding = PaddingValues(
            start = 24.dp,
            top = 16.dp,
            end = 24.dp,
            bottom = 16.dp
        ),
        horizontalArrangement = Arrangement.SpaceEvenly,
        modifier = Modifier
            .height(FOOD_ACTIVITIES_HEIGHT)
            .fillMaxWidth(),
        content = {
            items(6) { index ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Checkbox(checked = selectedActivities[index], onCheckedChange = {
                        selectedActivities = selectedActivities
                            .toMutableList()
                            .apply {
                                this[index] = !this[index]
                            }
                        onActivitySelected(selectedActivities)
                    })
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = strings[index],
                        color = Color.LightGray
                    )
                }

            }
        }
    )
}

@Composable
fun AllergySymptomsOccured(
    onSelectedSymptoms: (Boolean) -> Unit
) {
    var selectedSymptoms by remember { mutableStateOf(false) }
    Column(modifier = Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = stringResource(id = R.string.alergenSymptoms),
                fontSize = MaterialTheme.typography.labelLarge.fontSize
            )
        }
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = stringResource(id = R.string.yes),
                fontSize = MaterialTheme.typography.labelLarge.fontSize
            )
            RadioButton(selected = selectedSymptoms, onClick = {
                selectedSymptoms = !selectedSymptoms
                onSelectedSymptoms(selectedSymptoms)
            })
            Text(
                text = stringResource(id = R.string.no),
                fontSize = MaterialTheme.typography.labelLarge.fontSize
            )
            RadioButton(selected = !selectedSymptoms, onClick = {
                selectedSymptoms = !selectedSymptoms
                onSelectedSymptoms(selectedSymptoms)
            })
        }
    }
}

@Composable
fun CalendarLabel(
    currentDate: LocalDate,
    datePicker: DatePickerDialog
) {

    Column(Modifier.fillMaxWidth()) {
        Row(
            Modifier
                .fillMaxWidth()
                .clickable { datePicker.show() },
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_calendar),
                contentDescription = stringResource(id = R.string.description),
                modifier = Modifier.size(24.dp),
                tint = MediumGrey
            )
            Text(
                text = currentDate.format(DateTimeFormatter.ofPattern("dd.MM.yyyy")),
                fontSize = MaterialTheme.typography.bodyLarge.fontSize
            )
        }
    }
}


//@Composable
//@Preview
//fun SearchableExposedDropdownMenuBoxPreview() {
//    SearchableExposedDropdownMenuBox(
//        allProductsRequest = arrayOf(
//            Product(
//                name = "milk",
//                categoryId = 1,
//                isAllergen = false,
//                description = "description",
//                productId = 1
//            )
//        ).toList()
//    )
//}

//@Composable
//@Preview
//fun PastilleButtonPreview(
//    completed: MutableList<Boolean> = remember {
//        mutableStateListOf<Boolean>().apply {
//            addAll(List(21) { true })
//        }
//    }
//) {
//    PastilleButton(name = "s", completedList = completed, index = 1)
//}

//@Composable
//@Preview
//fun PillButtonGridPreview(
//    names: List<String> = listOf(
//        "Fruits", "Vegetables", "Dairy", "Meat", "s", "s", "s", "s", "s", "Grains",
//        "Sweets", "Beverages", "Snacks", "Seafood", "Condiments"
//    ),
//    completed: MutableList<Boolean> = MutableList(names.size) { false }
//) {
//    PillButtonGrid(names = names, completed = completed)
//}

//@Composable
//@Preview
//fun FlowRowButtonsPreview(
//    names: List<String> = listOf(
//        "All", "Fruits", "Vegetables", "Dairy", "Meat", "Grains",
//        "Sweets", "Beverages", "Snacks", "Seafood", "Condiments",
//        "Desserts", "Pasta", "Canned Goods", "Frozen Foods", "Bakery",
//        "Cereals", "Nuts", "Herbs and Spices", "Oils and Sauces", "Baby Food"
//    ),
//    completed: MutableList<Boolean> = remember {
//        mutableStateListOf<Boolean>().apply {
//            addAll(List(21) { true })
//        }
//    }
//) {
//    FlowRowButtons(names = names, completedList = completed)
//}

@Composable
@Preview
fun TitleLabelPreview() {
    TitleLabel(
        product = Product(
            name = "milk",
            categoryId = 1,
            description = "",
            isAllergen = true
        )
    )
}

@Composable
@Preview
fun AllergySymptomsOccuredPreview() {
    AllergySymptomsOccured(onSelectedSymptoms = {})
}

//@Composable
//@Preview
//fun CalendarLabelPreview() {
//    CalendarLabel(LocalDate.now(), )
//}

