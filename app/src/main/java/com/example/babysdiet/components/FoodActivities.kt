package com.example.babysdiet.components

import android.widget.GridLayout
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyHorizontalGrid
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.babysdiet.R
import com.example.babysdiet.components.data.models.Evaluation
import com.example.babysdiet.ui.theme.FOOD_ACTIVITIES_HEIGHT
import com.example.babysdiet.ui.theme.TOP_APP_BAR_HEIGHT

@Composable
fun FoodActivities() {
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
                        selectedActivities
                            .toMutableList()
                            .apply {
                                this[index] = !this[index]
                            }
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
@Preview
fun FoodActivitiesPreview() {
    FoodActivities()
}

