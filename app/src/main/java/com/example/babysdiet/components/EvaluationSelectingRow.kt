package com.example.babysdiet.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.babysdiet.components.data.models.Evaluation
import com.example.babysdiet.ui.theme.BIG_EVALUATOIN_INDICATOR_SIZE
import com.example.babysdiet.ui.theme.BadEvaluationColor
import com.example.babysdiet.ui.theme.EVALUATOIN_INDICATOR_SIZE
import com.example.babysdiet.ui.theme.ExcellentEvaluationColor
import com.example.babysdiet.ui.theme.FineEvaluationColor
import com.example.babysdiet.ui.theme.NeutralEvaluationColor
import com.example.babysdiet.ui.theme.VeryBadEvaluationColor
import com.example.babysdiet.ui.viewmodels.SharedViewModel

@Composable
fun EvaluationSelectingRow(
    onEvaluationSelected: (Evaluation) -> Unit,
) {
    var selectedColor by remember { mutableStateOf<Color?>(ExcellentEvaluationColor) }
    Column(Modifier.fillMaxWidth()) {
        Row(
            horizontalArrangement = Arrangement.Center,
            modifier = Modifier.fillMaxWidth()
        ) {
            val evaluations =
                listOf(
                    Evaluation.EXCELLENT,
                    Evaluation.FINE,
                    Evaluation.NEUTRAL,
                    Evaluation.BAD,
                    Evaluation.VERY_BAD
                )
            evaluations.forEach { evaluation ->
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .background(Color.Transparent, shape = CircleShape)
                        .clickable {
                            selectedColor = evaluation.color
                            onEvaluationSelected(evaluation)
                        }
                ) {
                    Canvas(
                        modifier = Modifier
                            .fillMaxSize()
                    ) {
                        val outerCircleRadius =
                            size.minDimension / 2 - 4.dp.toPx() // Size of outer circle with gap
                        val innerCircleRadius =
                            size.minDimension / 3 - 1.dp.toPx() // Inner wheel size

                        if (selectedColor == evaluation.color) {
                            drawCircle(
                                color = Color.DarkGray,
                                center = Offset(size.width / 2, size.height / 2),
                                radius = outerCircleRadius,
                                style = Stroke(8f)
                            )
                        }

                        drawCircle(
                            color = evaluation.color,
                            center = Offset(size.width / 2, size.height / 2),
                            radius = innerCircleRadius
                        )
                    }

                }
            }
        }
    }
}

@Composable
@Preview
fun EvaluationSelectingRowPreview() {
    EvaluationSelectingRow(
        onEvaluationSelected = {}
    )
}