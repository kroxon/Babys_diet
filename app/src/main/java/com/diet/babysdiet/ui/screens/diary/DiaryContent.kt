package com.diet.babysdiet.ui.screens.diary

import android.app.DatePickerDialog
import android.widget.DatePicker
import android.widget.Toast
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
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
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.runtime.LaunchedEffect
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
import com.diet.babysdiet.R
import com.diet.babysdiet.components.DisplayAlertDialog
import com.diet.babysdiet.components.EvaluationSelectingRow
import com.diet.babysdiet.components.ProductItem
import com.diet.babysdiet.components.SearchableDropdownMenu
import com.diet.babysdiet.components.SpacerTopAppBar
import com.diet.babysdiet.components.SpacerTopSmall
import com.diet.babysdiet.components.data.models.Diary
import com.diet.babysdiet.components.data.models.Evaluation
import com.diet.babysdiet.components.data.models.Product
import com.diet.babysdiet.components.rememberImeState
import com.diet.babysdiet.ui.theme.Blue1
import com.diet.babysdiet.ui.theme.LARGE_PADDING
import com.diet.babysdiet.ui.theme.LAZY_GRID_COLUMN_WIDTH
import com.diet.babysdiet.ui.theme.MEDIUM_PADDING
import com.diet.babysdiet.ui.theme.OUTLINEDBUTTON_HEIGHT
import com.diet.babysdiet.ui.theme.OUTLINEDBUTTON_HEIGHT_SMALL
import com.diet.babysdiet.ui.theme.SMALL_PADDING
import com.diet.babysdiet.ui.theme.VERY_SMALL_PADDING
import com.diet.babysdiet.ui.theme.buttonBackgroumdColor
import com.diet.babysdiet.ui.theme.topAppBarBackgroumdColor
import com.diet.babysdiet.util.RequestState
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Calendar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DiaryContent(
    selectedDiary: Diary?,
    evaluation: Evaluation,
    foodActivities: List<Boolean>,
    selectedProduct: Product?,
    diarySympotomsOccured: Boolean,
    diaryDescription: String,
    selectedDate: Long,
    selectedProducts: RequestState<List<Product>>,
    onProductSelected: (Product) -> Unit,
    onEvaluationSelected: (Evaluation) -> Unit,
    onActivitySelected: (List<Boolean>) -> Unit,
    onSelectedSymptoms: (Boolean) -> Unit,
    onSaveAsAllergen: (Boolean) -> Unit,
    onDescriptionChange: (String) -> Unit,
    onDateSelected: (Long) -> Unit,
    onButtonClickListener: (MutableList<Boolean>) -> Unit

) {
    var newSelectedProduct = selectedProduct

    // for calendar
    var currentDate = LocalDate.ofEpochDay(selectedDate)
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
        R.style.DatePickerTheme,
        { _: DatePicker, selectedYear: Int, selectedMonth: Int, selectedDayOfMonth: Int ->
            selectedDateText = "$selectedDayOfMonth/${selectedMonth + 1}/$selectedYear"
            currentDate = LocalDate.of(selectedYear, selectedMonth + 1, selectedDayOfMonth)
            onDateSelected(currentDate.toEpochDay())
        }, year, month, dayOfMonth

    )

    val imeState = rememberImeState()
    val scrollState = rememberScrollState()

    LaunchedEffect(key1 = imeState.value) {
        if (imeState.value) {
            scrollState.animateScrollTo(scrollState.maxValue, tween(300))
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .background(MaterialTheme.colorScheme.background)
            .navigationBarsPadding()
            .imePadding()
            .padding(LARGE_PADDING)
    ) {

        Column(
            modifier = Modifier
                .fillMaxSize()
//                .verticalScroll(scrollState)
                .background(MaterialTheme.colorScheme.background)
//                .navigationBarsPadding()
//                .imePadding()
//                .padding(LARGE_PADDING)
        ) {
//        SpacerTopAppBar()
//
//        var allProductsList: List<Product> = emptyList()
//        if (selectedProducts is RequestState.Success)
//            allProductsList = selectedProducts.data

//        SearchableExposedDropdownMenuBox(
//            allProducts = allProductsList,
//            onProductSelected = onProductSelected
//        )

//        SearchableDropdownMenu(
//            products = allProductsList,
//            onProductSelected = onProductSelected
//        )

            SpacerTopAppBar()
//            SpacerTopAppBar()
            SpacerTopSmall()
//            SpacerTopAppBar()


            // mutable list of selected category
            val categories: List<String> =
                stringArrayResource(id = R.array.categories_array).toList()
            var categorySelectedBtns = remember {
                mutableStateListOf<Boolean>().apply {
                    addAll(List(categories.size) { true })
                }
            }
            Spacer(modifier = Modifier.height(SMALL_PADDING))
            FlowRowButtons(
                names = categories,
                completedList = categorySelectedBtns,
                onButtonClickListener = onButtonClickListener
            )

            if (newSelectedProduct != null) {
                TitleLabel(newSelectedProduct)

                EvaluationSelectingRow(
                    onEvaluationSelected = onEvaluationSelected,
                    evaluation = evaluation
//                diary = selectedDiary
                )

                FoodActivities(
                    onActivitySelected = onActivitySelected,
                    diary = selectedDiary,
                    foodActivities = foodActivities
                )

                AllergySymptomsOccured(
                    selectedSymptoms = diarySympotomsOccured,
                    selectedProduct = newSelectedProduct,
                    onSelectedSymptoms = onSelectedSymptoms,
                    onSaveAsAllergen = onSaveAsAllergen
                )

                CalendarLabel(currentDate, datePicker)

                OutlinedTextField(
                    value = diaryDescription,
                    onValueChange = {
                        onDescriptionChange(it.trim().take(200))
                    },
                    modifier = Modifier
                        .fillMaxWidth(),
                    label = { Text(text = stringResource(id = R.string.description)) },
                    textStyle = MaterialTheme.typography.bodyLarge,
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        unfocusedBorderColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.34f)
                    )
                )
            }

        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
//                .verticalScroll(scrollState)
                .background(MaterialTheme.colorScheme.background)
//                .padding(LARGE_PADDING)
        ) {
//            SpacerTopAppBar()
            SpacerTopAppBar()
//            SpacerTopSmall()


            var allProductsList: List<Product> = emptyList()
            if (selectedProducts is RequestState.Success)
                allProductsList = selectedProducts.data


            SearchableDropdownMenu(
                products = allProductsList,
                onProductSelected = onProductSelected
            )
        }
    }

}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
fun SearchableExposedDropdownMenuBox(
    allProducts: List<Product>,
    onProductSelected: (Product) -> Unit
) {
    val context = LocalContext.current
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
                shape = RoundedCornerShape(20.dp),
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
//                        keyboardController?.hide() // Hide the keyboard to remove focus
//                        focusManager.clearFocus()
                        isClearButtonVisible = false
                    }
                ) {
                    filteredOptions.forEach { item ->
                        DropdownMenuItem(
                            modifier = Modifier
                                .fillMaxWidth()
                                .height(36.dp),
                            text = {
                                Text(
                                    text = item.name,
                                    fontSize = MaterialTheme.typography.displayLarge.fontSize,
                                    modifier = Modifier.fillMaxSize()
                                )
                            },
                            leadingIcon = {
                                ProductItem(product = item)
                            },
                            onClick = {
                                onProductSelected(item)
                                selectedText = ""
                                expanded = false
                                Toast.makeText(context, item.name, Toast.LENGTH_SHORT).show()
                                keyboardController?.hide() // Hide the keyboard to remove focus
                                focusManager.clearFocus()
                                isClearButtonVisible = false
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
    onButtonClickListener: (MutableList<Boolean>) -> Unit
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
                onButtonClickListener(completedList)
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
                .height(OUTLINEDBUTTON_HEIGHT_SMALL)
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
    onButtonClickListener: (MutableList<Boolean>) -> Unit
) {
    FlowRow(
        horizontalArrangement = Arrangement.Center,
    ) {
        repeat(names.size) { i ->
            PastilleButton(
                name = names.get(i),
                completedList = completedList,
                index = i,
                onButtonClickListener = onButtonClickListener
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
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = LARGE_PADDING),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = product.name,
                style = TextStyle(
                    fontSize = MaterialTheme.typography.headlineLarge.fontSize,
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
                        .size(20.dp)
                )
            }
        }
    }
}

@Composable
fun FoodActivities(
    foodActivities: List<Boolean>,
    diary: Diary?,
    onActivitySelected: (List<Boolean>) -> Unit
) {
    var selectedActivities = foodActivities
//    if (diary != null) {
//        val newList = selectedActivities.toMutableList()
//        newList[0] = diary.touched
//        newList[1] = diary.sniffed
//        newList[2] = diary.licked
//        newList[3] = diary.attemptFirst
//        newList[4] = diary.attemptSecond
//        newList[5] = diary.attemptThird
//        selectedActivities = newList
//    }
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
            SMALL_PADDING
        ),
        horizontalArrangement = Arrangement.SpaceEvenly,
        modifier = Modifier
            .height(LAZY_GRID_COLUMN_WIDTH)
            .fillMaxWidth(),
        content = {
            items(6) { index ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Checkbox(
                        modifier = Modifier.height(SMALL_PADDING),
                        checked = selectedActivities[index],
                        onCheckedChange = {
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
                        color = Color.DarkGray
                    )
                }

            }
        }
    )
}

@Composable
fun AllergySymptomsOccured(
    selectedSymptoms: Boolean,
    selectedProduct: Product,
    onSelectedSymptoms: (Boolean) -> Unit,
    onSaveAsAllergen: (Boolean) -> Unit
) {

    var openDialog by remember {
        mutableStateOf(false)
    }

    DisplayAlertDialog(
        title = stringResource(id = R.string.set_as_allergen_title),
        message = stringResource(id = R.string.set_as_allergen, selectedProduct.name),
        openDialog = openDialog,
        closeDialog = {
            openDialog = false
        },
        onYesClicked = { onSaveAsAllergen(true) },
        onNoClicked = { onSaveAsAllergen(false) })

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(MEDIUM_PADDING)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = SMALL_PADDING),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = stringResource(id = R.string.alergenSymptoms),
                fontSize = MaterialTheme.typography.labelLarge.fontSize
            )
        }
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(OUTLINEDBUTTON_HEIGHT),
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = stringResource(id = R.string.yes),
                fontSize = MaterialTheme.typography.labelLarge.fontSize
            )
            RadioButton(selected = selectedSymptoms, onClick = {
                onSelectedSymptoms(true)
                if (!selectedProduct.isAllergen)
                    openDialog = true
            })
            Text(
                text = stringResource(id = R.string.no),
                fontSize = MaterialTheme.typography.labelLarge.fontSize
            )
            RadioButton(selected = !selectedSymptoms, onClick = {
                onSelectedSymptoms(false)
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
                .padding(top = MEDIUM_PADDING)
                .clickable { datePicker.show() },
            horizontalArrangement = Arrangement.Center,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_calendar),
                contentDescription = stringResource(id = R.string.description),
                modifier = Modifier.size(35.dp),
                tint = Blue1
            )
            val today = LocalDate.now()
            val yesterday = LocalDate.now().minusDays(1)
            val tomorrow = LocalDate.now().plusDays(1)

            val formattedDate = when {
                currentDate == today -> stringResource(id = R.string.today)
                currentDate == yesterday -> stringResource(id = R.string.yesterday)
                currentDate == tomorrow -> stringResource(id = R.string.tomorrow)
                else -> currentDate.format(DateTimeFormatter.ofPattern("EEEE"))


            }
            Text(
                text = formattedDate + ", " + currentDate.format(DateTimeFormatter.ofPattern("dd.MM.yyyy")),
                fontSize = MaterialTheme.typography.bodyLarge.fontSize
            )
        }
    }
}


@Composable
@Preview
fun SearchableExposedDropdownMenuBoxPreview() {
    SearchableExposedDropdownMenuBox(
        allProducts = arrayOf(
            Product(
                name = "milk",
                categoryId = 1,
                isAllergen = false,
                description = "description",
                productId = 1
            ),
            Product(
                name = "milk",
                categoryId = 1,
                isAllergen = false,
                description = "description",
                productId = 1
            ),
            Product(
                name = "milk",
                categoryId = 1,
                isAllergen = false,
                description = "description",
                productId = 1
            ),
            Product(
                name = "milk",
                categoryId = 1,
                isAllergen = false,
                description = "description",
                productId = 1
            ),
            Product(
                name = "milk",
                categoryId = 1,
                isAllergen = false,
                description = "description",
                productId = 1
            ),
        ).toList(),
        onProductSelected = {}
    )
}

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

//@Composable
//@Preview
//fun TitleLabelPreview() {
//    TitleLabel(
//        product = Product(
//            name = "milk",
//            categoryId = 1,
//            description = "",
//            isAllergen = true
//        )
//    )
//}
//
//@Composable
//@Preview
//fun AllergySymptomsOccuredPreview() {
//    AllergySymptomsOccured(
//        onSelectedSymptoms = {},
//        selectedSymptoms = true,
//        selectedProduct = Product(0, "name", 1, "desc", true),
//        onSaveAsAllergen = {}
//    )
//}

//@Composable
//@Preview
//fun CalendarLabelPreview() {
//    CalendarLabel(LocalDate.now(), )
//}

//@Composable
//@Preview
//fun DiaryContentPreview() {
//    DiaryContent(
//        selectedDiary = null,
//        evaluation = Evaluation.BAD,
//        foodActivities = List(6) { true },
//        selectedProduct = Product(
//            productId = 887,
//            name = "gg",
//            categoryId = 1,
//            description = "",
//            isAllergen = true
//        ),
//        diarySympotomsOccured = true,
//        diaryDescription = "feuiw fhewiu  fewui hfehwiu ffewui iff ewuif hfiewu fhfhuie",
//        selectedDate = 473248,
//        selectedProducts = RequestState.Success(emptyList()),
//        onProductSelected = {},
//        onEvaluationSelected = {},
//        onActivitySelected = {},
//        onSelectedSymptoms = {},
//        onSaveAsAllergen = {},
//        onDescriptionChange = {},
//        onDateSelected = {},
//        onButtonClickListener = {}
//    )
//}
