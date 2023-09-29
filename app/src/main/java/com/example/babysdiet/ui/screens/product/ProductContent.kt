package com.example.babysdiet.ui.screens.product

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.babysdiet.R
import com.example.babysdiet.components.data.models.Diary
import com.example.babysdiet.components.data.models.Product
import com.example.babysdiet.ui.screens.home.EmptyContent
import com.example.babysdiet.ui.theme.EVALUATOIN_INDICATOR_SIZE
import com.example.babysdiet.ui.theme.LARGE_PADDING
import com.example.babysdiet.ui.theme.LAZY_GRID_COLUMN_WIDTH
import com.example.babysdiet.ui.theme.SMALL_PADDING
import com.example.babysdiet.ui.theme.TOP_APP_BAR_HEIGHT
import com.example.babysdiet.ui.theme.ULTRA_LARGE_PADDING
import com.example.babysdiet.ui.theme.diaryItemTextColor
import com.example.babysdiet.util.RequestState
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductContent(
    name: String,
    description: String,
    isAllergen: Boolean,
    productId: Int,
    categoryId: Int,
    selectedProduct: Product?,
    allDiaries: RequestState<List<Diary>>,
    onTitleChange: (String) -> Unit,
    onDescriptionChange: (String) -> Unit,
    onIsAllergenChange: (Boolean) -> Unit,
    navigateToDiaryScreen: (diaryId: Int, productId: Int) -> Unit

) {

    var diaries: List<Diary> = emptyList()
    if (allDiaries is RequestState.Success)
        diaries = allDiaries.data

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(LARGE_PADDING)
    ) {
        Spacer(modifier = Modifier.padding(top = TOP_APP_BAR_HEIGHT))

        OutlinedTextField(
            value = name,
            onValueChange = { onTitleChange(it) },
            modifier = Modifier
                .fillMaxWidth(),
            label = { Text(text = stringResource(id = R.string.name)) },
            maxLines = 1,
            textStyle = MaterialTheme.typography.bodyLarge,
            colors = TextFieldDefaults.outlinedTextFieldColors(
                unfocusedBorderColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.34f)
            )
        )

        Row(modifier = Modifier.fillMaxWidth()) {
            Text(text = stringResource(id = R.string.name_as_allergen))
            Checkbox(checked = isAllergen, onCheckedChange = { newIsAllergen ->
                onIsAllergenChange(newIsAllergen)
            })
        }

        OutlinedTextField(
            value = description,
            onValueChange = { onDescriptionChange(it) },
            modifier = Modifier
                .fillMaxWidth()
                .height(LAZY_GRID_COLUMN_WIDTH),
            label = { Text(text = stringResource(id = R.string.description)) },
            maxLines = 1,
            textStyle = MaterialTheme.typography.bodyLarge,
            colors = TextFieldDefaults.outlinedTextFieldColors(
                unfocusedBorderColor = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.34f)
            )
        )

        if (diaries.isEmpty() || productId < 1) {
            EmptyContent()
        } else
            selectedProduct?.let {
                DisplayDiaries(
                    diaries = diaries.filter { it.productId == productId },
                    product = it,
                    navigateToDiaryScreen = navigateToDiaryScreen
                )
            }
    }
}

@Composable
fun DisplayDiaries(
    diaries: List<Diary>,
    product: Product,
    navigateToDiaryScreen: (diaryId: Int, productId: Int) -> Unit
) {
    Column(Modifier.fillMaxWidth()) {
        Row(Modifier.fillMaxWidth()) {
            Text(
                text = stringResource(id = R.string.history),
                modifier = Modifier.padding(bottom = 8.dp, start = 16.dp),
                fontSize = MaterialTheme.typography.titleLarge.fontSize,
            )
        }
    }
    LazyColumn {

        items(
            items = diaries,
            key = { diary ->
                diary.diaryId
            }
        ) { diary ->
            // Find a product by diary.productId
//            val product = products.find { it.productId == diary.productId }

//            DiaryItem(
//                diary = diary,
//                product = product!!,
//                navigateToDiaryScreen = navigateToDiaryScreen
//            )
            DiaryCard(
                diary = diary,
                product = product,
                navigateToDiaryScreen = navigateToDiaryScreen
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DiaryCard(
    diary: Diary,
    product: Product,
    navigateToDiaryScreen: (diaryId: Int, productId: Int) -> Unit

) {
    Card(
        //shape = MaterialTheme.shapes.medium,
        shape = RoundedCornerShape(8.dp),
        // modifier = modifier.size(280.dp, 240.dp)
        modifier = Modifier.padding(
            start = 10.dp,
            top = SMALL_PADDING,
            bottom = SMALL_PADDING,
            end = 10.dp
        ),
        //set card elevation of the card
        elevation = CardDefaults.cardElevation(
            defaultElevation = SMALL_PADDING,
        ),
        colors = CardDefaults.cardColors(
            containerColor = Color.White,
        ),
        onClick = {
            navigateToDiaryScreen(diary.diaryId, diary.productId)
        }
    ) {

        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(all = LARGE_PADDING)
                .fillMaxWidth(),
        ) {
            Text(
                text = product.name,
//                text = "product.name",
                fontSize = MaterialTheme.typography.titleMedium.fontSize,
                color = MaterialTheme.colorScheme.diaryItemTextColor,
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Bold,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(Modifier.weight(1f))
            val currentDate: LocalDate = LocalDate.ofEpochDay(diary.timeEating)
            val formattedDate: String =
                currentDate.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"))
            Text(
                text = formattedDate,
//                text = "formattedDate",
                fontSize = MaterialTheme.typography.bodyLarge.fontSize,
                color = MaterialTheme.colorScheme.diaryItemTextColor,
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Normal,
                maxLines = 1,
                modifier = Modifier.padding(end = ULTRA_LARGE_PADDING)
            )
            Box(
                modifier = Modifier
                    .padding(start = SMALL_PADDING)
                    .wrapContentWidth()
            ) {
                Canvas(
                    modifier = Modifier
                        .size(EVALUATOIN_INDICATOR_SIZE)
//                        .wrapContentWidth()
                ) {
                    drawCircle(
                        color = diary.evaluation.color
                    )
                }
            }
        }
    }
}

