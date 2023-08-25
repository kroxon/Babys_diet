package com.example.babysdiet.ui.screens.home

import android.util.Log
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.babysdiet.components.data.models.Diary
import com.example.babysdiet.components.data.models.Evaluation
import com.example.babysdiet.components.data.models.Product
import com.example.babysdiet.ui.theme.DIARY_ITEM_ELEVATION
import com.example.babysdiet.ui.theme.EVALUATOIN_INDICATOR_SIZE
import com.example.babysdiet.ui.theme.LARGE_PADDING
import com.example.babysdiet.ui.theme.TOP_APP_BAR_HEIGHT
import com.example.babysdiet.ui.theme.diaryItemTextColor
import com.example.babysdiet.ui.theme.diaryItembackgroudColor
import com.example.babysdiet.util.RequestState

@Composable
fun HomeContent(
    diaries: RequestState<List<Diary>>,
    products: RequestState<List<Product>>,
    navigateToDiaryScreen: (diaryId: Int) -> Unit
) {
    if (diaries is RequestState.Success && products is RequestState.Success){
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

@Composable
fun DisplayDiaries(
    diaries: List<Diary>,
    products: List<Product>,
    navigateToDiaryScreen: (diaryId: Int) -> Unit
) {
    LazyColumn(
        modifier = Modifier.padding(top = TOP_APP_BAR_HEIGHT)
    ) {

        items(
            items = diaries,
            key = { diary ->
                diary.idDiary
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
    navigateToDiaryScreen: (diaryId: Int) -> Unit
) {
    Surface(
        modifier = Modifier.fillMaxWidth(),
        color = MaterialTheme.colorScheme.diaryItembackgroudColor,
        shape = RectangleShape,
        tonalElevation = DIARY_ITEM_ELEVATION,
        onClick = {
            navigateToDiaryScreen(diary.idDiary)
        }
    ) {
        Column(
            modifier = Modifier
                .padding(all = LARGE_PADDING)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.Center
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = product.name,
                    color = MaterialTheme.colorScheme.diaryItemTextColor,
                    style = MaterialTheme.typography.headlineLarge,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1
                )
                Spacer(Modifier.weight(1f))
                Text(
                    text = diary.timeEating.toString(),
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
        product = Product(0, "Milk", "1", "milk", true),
        navigateToDiaryScreen = {}
    )
}