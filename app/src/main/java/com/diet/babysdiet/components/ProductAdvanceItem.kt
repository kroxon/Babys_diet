package com.diet.babysdiet.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.diet.babysdiet.R
import com.diet.babysdiet.components.data.models.Diary
import com.diet.babysdiet.components.data.models.Evaluation
import com.diet.babysdiet.components.data.models.Product
import com.diet.babysdiet.ui.theme.BIG_EVALUATOIN_INDICATOR_SIZE
import com.diet.babysdiet.ui.theme.EVALUATOIN_INDICATOR_SIZE
import com.diet.babysdiet.ui.theme.OUTLINEDBUTTON_HEIGHT
import com.diet.babysdiet.ui.theme.SMALL_PADDING

@Composable
fun ProductAdvanceItem(
    product: Product,
    diaries: List<Diary>,
    navigateToProductScreen: (productId: Int) -> Unit
) {
    Column(
        Modifier
            .fillMaxWidth()
            .background(Color.White)
            .clickable { navigateToProductScreen(product.productId) }
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start,
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    start = SMALL_PADDING,
                    end = SMALL_PADDING,
                    top = SMALL_PADDING
                )
                .height(BIG_EVALUATOIN_INDICATOR_SIZE)
        ) {
            Text(
//            modifier = Modifier
//                .padding(LARGE_PADDING),
                text = product.name,
                style = MaterialTheme.typography.titleSmall,
                color = MaterialTheme.colorScheme.onSurface,
                fontSize = MaterialTheme.typography.headlineSmall.fontSize
            )
            if (product.isAllergen) {
                Image(
                    painter = painterResource(id = R.drawable.ic_alert),
                    contentDescription = "allergen alert",
                    modifier = Modifier
                        .padding(
                            start = SMALL_PADDING
                        )
                        .size(EVALUATOIN_INDICATOR_SIZE)
                )
            }
            ProductEvaluations(diaries = diaries)
        }
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start,
            modifier = Modifier
                .fillMaxWidth()
                .padding(
                    start = SMALL_PADDING,
                    end = SMALL_PADDING,
                    bottom = SMALL_PADDING
                )
                .background(Color.LightGray)
                .height(1.dp)
        ) {}
    }
}

@Composable
fun ProductEvaluations(diaries: List<Diary>) {
    Row(
        Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.End,
        verticalAlignment = Alignment.CenterVertically
    ) {
        for (i in 0 until diaries.size.coerceAtMost(5)) {
            Box(
                modifier = Modifier
                    .size(OUTLINEDBUTTON_HEIGHT)
                    .padding(start = SMALL_PADDING)
            ) {
                Canvas(
                    modifier = Modifier
                        .fillMaxSize()
                        .shadow(4.dp, shape = CircleShape)
                ) {
                    drawCircle(
                        color = diaries[i].evaluation.color
                    )
                }

            }
        }
    }
}

@Composable
@Preview
fun ProductAdvanceItemPreview() {
    ProductAdvanceItem(
        product = Product(
            productId = 1,
            name = "allergen",
            categoryId = 1,
            description = "",
            isAllergen = true
        ),
        diaries = listOf(
            Diary(
                0, 2, 1, true, "", Evaluation.EXCELLENT,
                true, true, true, true, true, true
            ),
            Diary(
                0, 2, 1, true, "", Evaluation.BAD,
                true, true, true, true, true, true
            ),
            Diary(
                0, 2, 1, true, "", Evaluation.EXCELLENT,
                true, true, true, true, true, true
            ),
            Diary(
                0, 2, 1, true, "", Evaluation.BAD,
                true, true, true, true, true, true
            ),
            Diary(
                0, 2, 1, true, "", Evaluation.VERY_BAD,
                true, true, true, true, true, true
            ),
            Diary(
                0, 2, 1, true, "", Evaluation.NEUTRAL,
                true, true, true, true, true, true
            )
        ),
        navigateToProductScreen = {}
    )
}

//@Composable
//@Preview
//fun ProductEvaluationsPrewiev() {
//    ProductEvaluations(
//        diaries = listOf(
//            Diary(
//                0, 2, 1, true, "", Evaluation.EXCELLENT,
//                true, true, true, true, true, true
//            ),
//            Diary(
//                0, 2, 1, true, "", Evaluation.BAD,
//                true, true, true, true, true, true
//            ),
//            Diary(
//                0, 2, 1, true, "", Evaluation.EXCELLENT,
//                true, true, true, true, true, true
//            ),
//            Diary(
//                0, 2, 1, true, "", Evaluation.BAD,
//                true, true, true, true, true, true
//            ),
//            Diary(
//                0, 2, 1, true, "", Evaluation.VERY_BAD,
//                true, true, true, true, true, true
//            ),
//            Diary(
//                0, 2, 1, true, "", Evaluation.NEUTRAL,
//                true, true, true, true, true, true
//            )
//        )
//    )
//}