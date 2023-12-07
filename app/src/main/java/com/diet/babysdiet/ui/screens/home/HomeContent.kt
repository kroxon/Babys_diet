package com.diet.babysdiet.ui.screens.home

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.diet.babysdiet.R
import com.diet.babysdiet.components.data.models.Diary
import com.diet.babysdiet.components.data.models.Product
import com.diet.babysdiet.ui.theme.EVALUATOIN_INDICATOR_SIZE
import com.diet.babysdiet.ui.theme.LARGE_PADDING
import com.diet.babysdiet.ui.theme.LAZY_GRID_HEIGHT
import com.diet.babysdiet.ui.theme.MEDIUM_PADDING
import com.diet.babysdiet.ui.theme.OUTLINEDBUTTON_HEIGHT_SMALL
import com.diet.babysdiet.ui.theme.SMALL_PADDING
import com.diet.babysdiet.ui.theme.VERY_SMALL_PADDING
import com.diet.babysdiet.ui.theme.VeryBadEvaluationColor
import com.diet.babysdiet.ui.theme.buttonBackgroumdColor
import com.diet.babysdiet.ui.theme.diaryItemTextColor
import com.diet.babysdiet.util.Action
import com.diet.babysdiet.util.RequestState
import java.time.LocalDate
import java.time.format.DateTimeFormatter

@Composable
fun HomeContent(
    diaries: RequestState<List<Diary>>,
    products: RequestState<List<Product>>,
    allergens: RequestState<List<Product>>,
    navigateToDiaryScreen: (diaryId: Int, productId: Int) -> Unit,
    navigateToCategoryScreen: (categoryId: Int, productId: Int, action: Action) -> Unit,
    onAllegrenClickListener: (categoryId: Int, productId: Int) -> Unit,
    onSwipeToDelete: (Action, Diary, Product) -> Unit,
    paddings: PaddingValues
) {

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(
//                top = MEDIUM_PADDING,
//                bottom = MEDIUM_PADDING
                paddings
            )
    ) {

//        SpacerTop()

        DisplayCategories(
            names = ((stringArrayResource(id = R.array.categories_array)).toList()).drop(1),
            navigateToCategoryScreen = navigateToCategoryScreen
        )

        if (allergens is RequestState.Success) {
            if (!allergens.data.isEmpty())
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
                    navigateToDiaryScreen = navigateToDiaryScreen,
                    onSwipeToDelete = onSwipeToDelete
                )
        }
    }

}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun DisplayDiaries(
    diaries: List<Diary>,
    products: List<Product>,
    navigateToDiaryScreen: (diaryId: Int, productId: Int) -> Unit,
    onSwipeToDelete: (Action, Diary, Product) -> Unit
) {

    Text(
        text = stringResource(id = R.string.history),
        modifier = Modifier.padding(
            start = 16.dp,
            end = 16.dp,
            bottom = 8.dp
        ),
        fontSize = MaterialTheme.typography.titleLarge.fontSize,
        fontWeight = FontWeight.Bold
    )

    val groupedDiaries = groupDiariesByDate(diaries)

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(
                start = 16.dp,
                end = 16.dp,
                bottom = 12.dp
            )
    ) {
        groupedDiaries.forEach { diaryGroup ->
            stickyHeader {
                DateHeader(diaryGroup.date)
            }
            items(diaryGroup.diaries.size) { index ->
                val diary = diaryGroup.diaries[index]
                val product = products.find { it.productId == diary.productId }

                DiaryCard(
                    diary = diary,
                    product = product!!,
                    navigateToDiaryScreen = navigateToDiaryScreen
                )

//                val dismissState = rememberDismissState()
//                val dismissDirection = dismissState.dismissDirection
//                val isDismissed = dismissState.isDismissed(DismissDirection.EndToStart)
//                if (isDismissed && dismissDirection == DismissDirection.EndToStart) {
//                    LaunchedEffect(key1 = true) {
//                        onSwipeToDelete(Action.DELETE_DIARY, diary, product!!)
//                    }
//                }
//
//                val degrees by animateFloatAsState(
//                    if (dismissState.targetValue == DismissValue.Default)
//                        0f
//                    else
//                        -45f
//                )
//
//                var itemAppeared by remember { mutableStateOf(false) }
//                LaunchedEffect(key1 = true) {
//                    itemAppeared = true
//                }
//
//                AnimatedVisibility(
//                    visible = itemAppeared && !isDismissed,
//                    enter = expandVertically(
//                        animationSpec = tween(
//                            durationMillis = 300
//                        )
//                    ),
//                    exit = shrinkVertically(
//                        animationSpec = tween(
//                            durationMillis = 300
//                        )
//                    )
//                ) {
//                    SwipeToDismiss(
//                        state = dismissState,
//                        directions = setOf(DismissDirection.EndToStart),
//                        background = { RedBackground(degrees = degrees) },
//                        dismissContent = {
//                            DiaryCard(
//                                diary = diary,
//                                product = product!!,
//                                navigateToDiaryScreen = navigateToDiaryScreen
//                            )
//                        }
//                    )
//                }
            }
        }
    }
}

data class DiaryGroup(val date: String, val diaries: List<Diary>)

@Composable
fun groupDiariesByDate(diaries: List<Diary>): List<DiaryGroup> {
    return diaries.groupBy { getLocalDateFromTimestamp(it.timeEating) }
        .map { (date, diariesForDate) ->
            DiaryGroup(date, diariesForDate)
        }
}

@Composable
fun getLocalDateFromTimestamp(timestamp: Long): String {
    val currentDate: LocalDate = LocalDate.ofEpochDay(timestamp)

//            val formattedDate: String =
//                currentDate.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"))

    val today = LocalDate.now()
    val yesterday = LocalDate.now().minusDays(1)
    val tomorrow = LocalDate.now().plusDays(1)

    val formattedDate = when {
        currentDate == today -> stringResource(id = R.string.today)
        currentDate == yesterday -> stringResource(id = R.string.yesterday)
        currentDate == tomorrow -> stringResource(id = R.string.tomorrow)
        else -> currentDate.format(DateTimeFormatter.ofPattern("EEEE"))
    }

    return "$formattedDate, ${currentDate.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"))}"
}

@Composable
fun DateHeader(date: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 12.dp, bottom = 4.dp)
            .drawBehind {
                drawRoundRect(
                    Color(0xFFFFFFFF),
                    cornerRadius = CornerRadius(10.dp.toPx())
                )
            },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(Icons.Default.DateRange, contentDescription = null, modifier = Modifier.size(20.dp))
        Spacer(modifier = Modifier.width(8.dp))
        Text(
            text = date.toString(),
            fontSize = MaterialTheme.typography.titleMedium.fontSize,
//            fontWeight = FontWeight.Bold
        )
    }
}

//@Composable
//fun DiaryItem(
//    diary: Diary,
//    product: Product,
//    navigateToDiaryScreen: (diaryId: Int, productId: Int) -> Unit
//
//) {
//    Surface(
//        modifier = Modifier.fillMaxWidth(),
//        color = MaterialTheme.colorScheme.diaryItembackgroudColor,
//        shape = RectangleShape,
//        tonalElevation = DIARY_ITEM_ELEVATION,
//        onClick = {
//            navigateToDiaryScreen(diary.diaryId, diary.productId)
//        }
//    ) {
//        Column(
//            modifier = Modifier
//                .padding(all = MEDIUM_PADDING)
//                .fillMaxWidth(),
//            verticalArrangement = Arrangement.Center
//        ) {
//            Row(
//                verticalAlignment = Alignment.CenterVertically
//            ) {
//                Text(
//                    text = product.name,
//                    color = MaterialTheme.colorScheme.diaryItemTextColor,
//                    style = MaterialTheme.typography.bodyMedium,
//                    fontWeight = FontWeight.Bold,
//                    maxLines = 1
//                )
//                Spacer(Modifier.weight(1f))
//                val currentDate: LocalDate = LocalDate.ofEpochDay(diary.timeEating)
//                val formattedDate: String =
//                    currentDate.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"))
//                Text(
//                    text = formattedDate,
//                    color = MaterialTheme.colorScheme.diaryItemTextColor,
//                    style = MaterialTheme.typography.bodyMedium,
//                    fontWeight = FontWeight.Normal,
//                    maxLines = 1,
//                    modifier = Modifier.padding(end = 16.dp)
//                )
//                Box {
//                    Canvas(
//                        modifier = Modifier
//                            .size(EVALUATOIN_INDICATOR_SIZE)
//                    ) {
//                        drawCircle(
//                            color = diary.evaluation.color
//                        )
//                    }
//                }
//            }
//        }
//    }
//}

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
//            start = 10.dp,
            top = SMALL_PADDING,
//            bottom = SMALL_PADDING,
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
            if (product.isAllergen) {
                Image(
                    painter = painterResource(id = R.drawable.ic_alert),
                    contentDescription = "allergen alert",
                    modifier = Modifier
                        .padding(
                            start = LARGE_PADDING
                        )
                        .size(EVALUATOIN_INDICATOR_SIZE)
                )
            }
            Spacer(Modifier.weight(1f))


//            val currentDate: LocalDate = LocalDate.ofEpochDay(diary.timeEating)
//
////            val formattedDate: String =
////                currentDate.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"))
//
//            val today = LocalDate.now()
//            val yesterday = LocalDate.now().minusDays(1)
//            val tomorrow = LocalDate.now().plusDays(1)
//
//            val formattedDate = when {
//                currentDate == today -> stringResource(id = R.string.today)
//                currentDate == yesterday -> stringResource(id = R.string.yesterday)
//                currentDate == tomorrow -> stringResource(id = R.string.tomorrow)
//                else -> currentDate.format(DateTimeFormatter.ofPattern("EEEE"))
//            }
//
//            Text(
//                text = "$formattedDate, ${currentDate.format(DateTimeFormatter.ofPattern("dd.MM.yyyy"))}",
////                text = "formattedDate",
//                fontSize = MaterialTheme.typography.bodyLarge.fontSize,
//                color = MaterialTheme.colorScheme.diaryItemTextColor,
//                style = MaterialTheme.typography.bodyMedium,
//                fontWeight = FontWeight.Normal,
//                maxLines = 1,
//                modifier = Modifier.padding(end = ULTRA_LARGE_PADDING)
//            )
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

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun DisplayCategories(
    names: List<String>,
    navigateToCategoryScreen: (categoryId: Int, productId: Int, action: Action) -> Unit
) {

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
        Column(Modifier.fillMaxWidth()) {
            Row(Modifier.fillMaxWidth()) {
                Text(
                    text = stringResource(id = R.string.products),
                    modifier = Modifier.padding(
                        bottom = MEDIUM_PADDING,
                        start = LARGE_PADDING,
                        top = SMALL_PADDING
                    ),
                    fontSize = MaterialTheme.typography.titleLarge.fontSize,
                )
            }
        }

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
                    navigateToCategoryScreen = navigateToCategoryScreen
                )
            }
        }
    }
}

@Composable
fun PastilleButton(
    name: String,
    index: Int,
    navigateToCategoryScreen: (categoryId: Int, productId: Int, action: Action) -> Unit
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
                navigateToCategoryScreen(index + 1, 0, Action.NO_ACTION)
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

@Composable
fun DisplayAllergens(
    allergens: List<Product>,
    onAllegrenClickListener: (categoryId: Int, productId: Int) -> Unit
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
            val cells = when {
                allergens.size == 2 -> 2
                allergens.size == 1 -> 1
                else -> 3
            }
            LazyHorizontalGrid(
                rows = GridCells.Fixed(cells),
                modifier = Modifier
                    .heightIn(0.dp, LAZY_GRID_HEIGHT / 3 * cells)
                    .fillMaxWidth()
                    .wrapContentHeight()
            ) {
                items(allergens.size) { index ->
                    Box(
                        modifier = Modifier
                            .padding(
                                top = SMALL_PADDING,
                                bottom = SMALL_PADDING,
                                start = SMALL_PADDING,
                                end = LARGE_PADDING
                            )
                            .heightIn(0.dp, LAZY_GRID_HEIGHT)
                            .clickable {
                                onAllegrenClickListener(
                                    (-1) * allergens[index].categoryId,
                                    allergens[index].productId
                                )
                            }
                    ) {
                        Text(
                            text = allergens[index].name,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            fontSize = MaterialTheme.typography.titleMedium.fontSize,
//                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun RedBackground(degrees: Float) {
    Box(
        modifier = Modifier
            .fillMaxSize()
//            .background(VeryBadEvaluationColor)
            .padding(horizontal = LARGE_PADDING),
        contentAlignment = Alignment.CenterEnd
    ) {
        Icon(
            modifier = Modifier.rotate(degrees),
            imageVector = Icons.Filled.Delete,
            contentDescription = stringResource(id = R.string.delete_diary),
            tint = VeryBadEvaluationColor
        )
    }
}


//@Composable
//@Preview
//fun DiaryItemPreview() {
//    DiaryItem(
//        diary = Diary(
//            0,
//            54435464234325,
//            2,
//            true,
//            "desc",
//            Evaluation.EXCELLENT,
//            true,
//            true,
//            true,
//            true,
//            true,
//            true
//        ),
//        product = Product(0, "Milk", 1, "milk", true),
//        navigateToDiaryScreen = { _, _ -> }
//    )
//}

//@Composable
//@Preview
//fun DiaryCardPreview() {
//    DiaryCard(
//        diary = Diary(
//            1, 1, 1, true, "desc", Evaluation.VERY_BAD,
//            true, true, true, true, true, true
//        ),
//        product = Product(1, "banana", 1, "description", true),
//        navigateToDiaryScreen = { _, _ -> }
//    )
//}


//@Composable
//@Preview
//fun DisplayCategoriesPreview() {
//    DisplayCategories(
//        names = listOf("vegetables", "fruits", "vegetables", "fruits", "vegetables", "fruits"),
//        navigateToCategoryScreen = { _, _, _ -> }
//    )
//}
//
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
//        ), onAllegrenClickListener = { _, _ -> }
//    )
//}

@Composable
@Preview
fun DateHeaderPreview() {
    DateHeader("Monday, 12.11.2023")
}
