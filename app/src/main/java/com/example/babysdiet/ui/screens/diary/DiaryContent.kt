package com.example.babysdiet.ui.screens.diary

import android.widget.Toast
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.Checkbox
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.babysdiet.components.data.models.Diary
import com.example.babysdiet.components.data.models.Product
import com.example.babysdiet.ui.theme.OUTLINEDBUTTON_HEIGHT
import com.example.babysdiet.ui.theme.SMALL_PADDING
import com.example.babysdiet.ui.theme.TOP_APP_BAR_HEIGHT
import com.example.babysdiet.ui.theme.VERY_SMALL_PADDING
import com.example.babysdiet.ui.theme.buttonBackgroumdColor
import com.example.babysdiet.ui.theme.topAppBarBackgroumdColor

@Composable
fun DiaryContent(
    selectedDiary: Diary?,
    selectedProduct: Product?
) {
    Spacer(modifier = Modifier.padding(top = TOP_APP_BAR_HEIGHT))
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = SMALL_PADDING)
    ) {
        SearchableExposedDropdownMenuBox()
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchableExposedDropdownMenuBox() {
    val context = LocalContext.current
    val coffeeDrinks = arrayOf("Americano", "Cappuccino", "Espresso", "Latte", "Mocha")
    var expanded by remember { mutableStateOf(false) }
    var selectedText by remember { mutableStateOf("") }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(SMALL_PADDING)
    ) {
        ExposedDropdownMenuBox(
            modifier = Modifier.fillMaxWidth(),
            expanded = expanded,
            onExpandedChange = {
                expanded = !expanded
            }
        ) {
            OutlinedTextField(
                value = selectedText,
                onValueChange = { selectedText = it },
                label = { Text(text = "Search") },
                trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                modifier = Modifier
                    .menuAnchor()
                    .fillMaxWidth()
            )

            val filteredOptions =
                coffeeDrinks.filter { it.contains(selectedText, ignoreCase = true) }
            if (filteredOptions.isNotEmpty()) {
                ExposedDropdownMenu(
                    modifier = Modifier.fillMaxWidth(),
                    expanded = expanded,
                    onDismissRequest = {
                        // We shouldn't hide the menu when the user enters/removes any character
                    }
                ) {
                    filteredOptions.forEach { item ->
                        DropdownMenuItem(
                            modifier = Modifier.fillMaxWidth(),
                            text = { Text(text = item) },
                            onClick = {
                                selectedText = item
                                expanded = false
                                Toast.makeText(context, item, Toast.LENGTH_SHORT).show()
                            }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun PillButtonGrid(names: List<String>, completed: MutableList<Boolean>) {
    LazyVerticalGrid(
        columns = GridCells.Adaptive(minSize = 50.dp)
    ) {
        itemsIndexed(items = names) { index: Int, item: String ->
            PastilleButton(name = item, completedList = completed, index = index)
        }
    }
}


@Composable
fun PastilleButton(name: String, completedList: MutableList<Boolean>, index: Int) {
    var isSelected by rememberSaveable { mutableStateOf(completedList[index]) }

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
                completedList[index] = !isSelected
//                if (index == 0 && completedList.drop(1).all { it })
                if (index == 0 && isSelected)
                    completedList.map { true }
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
fun FlowRowButtons(names: List<String>, completedList: MutableList<Boolean>) {
    FlowRow(
        horizontalArrangement = Arrangement.Center,
    ) {
        repeat(names.size) { i ->
            PastilleButton(
                name = names.get(i),
                completedList = completedList,
                index = i
            )
        }
    }
}

//@Composable
//@Preview
//fun SearchableExposedDropdownMenuBoxPreview() {
//    SearchableExposedDropdownMenuBox()
//}

@Composable
@Preview
fun PastilleButtonPreview(
    completed: MutableList<Boolean> = MutableList(15) { false }
) {
    PastilleButton(name = "s", completedList = completed, index = 1)
}

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

@Composable
@Preview
fun FlowRowButtonsPreview(
    names: List<String> = listOf(
        "All", "Fruits", "Vegetables", "Dairy", "Meat", "Grains",
        "Sweets", "Beverages", "Snacks", "Seafood", "Condiments",
        "Desserts", "Pasta", "Canned Goods", "Frozen Foods", "Bakery",
        "Cereals", "Nuts", "Herbs and Spices", "Oils and Sauces", "Baby Food"
    ),
    completed: MutableState<List<Boolean>> = remember { mutableStateOf(List(names.size) { false }) }
) {
    FlowRowButtons(names = names, completedList = completed.value.toMutableList())
}