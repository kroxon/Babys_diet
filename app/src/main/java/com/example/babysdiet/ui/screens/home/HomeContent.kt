package com.example.babysdiet.ui.screens.home

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.babysdiet.R
import com.example.babysdiet.components.data.models.Diary
import com.example.babysdiet.components.data.models.Evaluation
import com.example.babysdiet.components.data.models.Product
import com.example.babysdiet.ui.theme.DIARY_ITEM_ELEVATION
import com.example.babysdiet.ui.theme.EVALUATOIN_INDICATOR_SIZE
import com.example.babysdiet.ui.theme.LAZY_GRID_COLUMN_WIDTH
import com.example.babysdiet.ui.theme.LAZY_GRID_HEIGHT
import com.example.babysdiet.ui.theme.MEDIUM_PADDING
import com.example.babysdiet.ui.theme.OUTLINEDBUTTON_HEIGHT
import com.example.babysdiet.ui.theme.SMALL_PADDING
import com.example.babysdiet.ui.theme.TOP_APP_BAR_HEIGHT
import com.example.babysdiet.ui.theme.VERY_SMALL_PADDING
import com.example.babysdiet.ui.theme.buttonBackgroumdColor
import com.example.babysdiet.ui.theme.diaryItemTextColor
import com.example.babysdiet.ui.theme.diaryItembackgroudColor
import com.example.babysdiet.util.RequestState
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Composable
fun HomeContent(
    diaries: RequestState<List<Diary>>,
    products: RequestState<List<Product>>,
    allergens: RequestState<List<Product>>,
    navigateToDiaryScreen: (diaryId: Int, productId: Int) -> Unit,
    onCategoryClickListener: (Int) -> Unit,
    onAllegrenClickListener: (Int) -> Unit
) {

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = TOP_APP_BAR_HEIGHT)
    ) {

        DisplayCategories(
            names = (stringArrayResource(id = R.array.categories_array)).toList(),
            onCategoryClickListener = onCategoryClickListener
        )

        if (allergens is RequestState.Success) {
            DisplayAllergens(
                allergens = allergens.data,
                onAllegrenClickListener = onAllegrenClickListener
            )
        }

        if (diaries is RequestState.Success && products is RequestState.Success) {
            if (diaries.data.isEmpty()) {
                EmptyContent()
            } else
                DisplayDiaries(
                    diaries = diaries.data,
                    products = products.data,
                    navigateToDiaryScreen = navigateToDiaryScreen
                )
        }
    }

}

@Composable
fun DisplayDiaries(
    diaries: List<Diary>,
    products: List<Product>,
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
            val product = products.find { it.productId == diary.productId }

            DiaryItem(
                diary = diary,
                product = product!!,
                navigateToDiaryScreen = navigateToDiaryScreen
            )
        }
    }
}

@Composable
fun DiaryItem(
    diary: Diary,
    product: Product,
    navigateToDiaryScreen: (diaryId: Int, productId: Int) -> Unit

) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        color = MaterialTheme.colorScheme.diaryItembackgroudColor,
        shape = RectangleShape,
        tonalElevation = DIARY_ITEM_ELEVATION,
        onClick = {
            navigateToDiaryScreen(diary.diaryId, diary.productId)
        }
    ) {
        Column(
            modifier = Modifier
                .padding(all = MEDIUM_PADDING)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.Center
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = product.name,
                    color = MaterialTheme.colorScheme.diaryItemTextColor,
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1
                )
                Spacer(Modifier.weight(1f))
                val currentDate: LocalDate = LocalDate.ofEpochDay(diary.timeEating)
                val formattedDate: String =
                    currentDate.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"))
                Text(
                    text = formattedDate,
                    color = MaterialTheme.colorScheme.diaryItemTextColor,
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Normal,
                    maxLines = 1,
                    modifier = Modifier.padding(end = 16.dp)
                )
                Box {
                    Canvas(
                        modifier = Modifier
                            .size(EVALUATOIN_INDICATOR_SIZE)
                    ) {
                        drawCircle(
                            color = diary.evaluation.color
                        )
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun DisplayCategories(
    names: List<String>,
    onCategoryClickListener: (Int) -> Unit
) {
    Column(Modifier.fillMaxWidth()) {
        Row(Modifier.fillMaxWidth()) {
            Text(
                text = stringResource(id = R.string.products),
                modifier = Modifier.padding(bottom = 8.dp, start = 16.dp),
                fontSize = MaterialTheme.typography.titleLarge.fontSize,
            )
        }
    }
    Card(
        //shape = MaterialTheme.shapes.medium,
        shape = RoundedCornerShape(8.dp),
        // modifier = modifier.size(280.dp, 240.dp)
        modifier = Modifier.padding(10.dp, 5.dp, 10.dp, 10.dp),
        //set card elevation of the card
        elevation = CardDefaults.cardElevation(
            defaultElevation = 10.dp,
        ),
        colors = CardDefaults.cardColors(
            containerColor = Color.White,
        ),
    ) {
        FlowRow(
            modifier = Modifier
                .fillMaxWidth()
                .padding(SMALL_PADDING),
            horizontalArrangement = Arrangement.Center,
        ) {
            repeat(names.size) { index ->
                PastilleButton(
                    name = names.get(index),
                    index = index,
                    onCategoryClickListener = onCategoryClickListener
                )
            }
        }
    }
}

@Composable
fun PastilleButton(
    name: String,
    index: Int,
    onCategoryClickListener: (Int) -> Unit
) {
    val backgroundColor = MaterialTheme.colorScheme.buttonBackgroumdColor
    val textColor = Color.White
    val outlineColor = MaterialTheme.colorScheme.buttonBackgroumdColor

    Box(
        Modifier
            .padding(VERY_SMALL_PADDING)
    ) {
        OutlinedButton(
            onClick = {
                onCategoryClickListener(index)
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
                containerColor = MaterialTheme.colorScheme.buttonBackgroumdColor,
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

@Composable
fun DisplayAllergens(
    allergens: List<Product>,
    onAllegrenClickListener: (Int) -> Unit
) {
    Card(
        //shape = MaterialTheme.shapes.medium,
        shape = RoundedCornerShape(8.dp),
        // modifier = modifier.size(280.dp, 240.dp)
        modifier = Modifier
            .padding(10.dp, 5.dp, 10.dp, 10.dp)
            .wrapContentHeight(),
        //set card elevation of the card
        elevation = CardDefaults.cardElevation(
            defaultElevation = 10.dp,
        ),
        colors = CardDefaults.cardColors(
            containerColor = Color.White,
        ),
    ) {


        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(SMALL_PADDING)
                .wrapContentHeight()
        ) {
            Text(
                text = stringResource(id = R.string.allergens),
                color = Color.Red,
                modifier = Modifier.padding(bottom = 8.dp, start = 8.dp),
                fontSize = MaterialTheme.typography.titleLarge.fontSize,
                fontWeight = FontWeight.Bold
            )
            LazyHorizontalGrid(
                rows = GridCells.Fixed(3),
                modifier = Modifier
                    .height(LAZY_GRID_HEIGHT)
                    .fillMaxWidth()
            ) {
                items(allergens.size) { index ->
                    Box(
                        modifier = Modifier
                            .padding(8.dp)
                            .height(TOP_APP_BAR_HEIGHT)
                            .clickable { onAllegrenClickListener(allergens[index].productId) }
                    ) {
                        Text(
                            text = allergens[index].name,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            fontSize = MaterialTheme.typography.labelLarge.fontSize,
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }
    }

}


@Composable
@Preview
fun DiaryItemPreview() {
    DiaryItem(
        diary = Diary(
            0,
            54435464234325,
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
        product = Product(0, "Milk", 1, "milk", true),
        navigateToDiaryScreen = { _, _ -> }
    )
}
//
//@Composable
//@Preview
//fun DisplayCategoriesPreview() {
//    DisplayCategories(
//        names = listOf("vegetables", "fruits", "vegetables", "fruits", "vegetables", "fruits"),
//        onCategoryClickListener = {}
//    )
//}

//@Composable
//@Preview
//fun DisplayAllergensPreview() {
//    DisplayAllergens(
//        allergens = listOf(
//            Product(0, "Milk", 1, "milk", true),
//            Product(1, "Mleko kokosowe", 1, "milk", true),
//            Product(1, "Mleko kokosowe", 1, "milk", true),
//            Product(1, "Mleko kokosowe", 1, "milk", true),
//            Product(1, "Mleko kokosowe", 1, "milk", true),
//            Product(2, "Jaja na twardo", 1, "milk", true),
//            Product(2, "Jaja na twardo", 1, "milk", true),
//            Product(2, "Jaja na twardo", 1, "milk", true),
//            Product(2, "Jaja na twardo", 1, "milk", true),
//            Product(2, "Jaja na twardo", 1, "milk", true),
//            Product(3, "Orzeszki ziemne", 1, "milk", true)
//        ), onAllegrenClickListener = {}
//    )
//}