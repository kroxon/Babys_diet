package com.diet.babysdiet.components.data.models

import androidx.compose.ui.graphics.Color
import com.diet.babysdiet.ui.theme.BadEvaluationColor
import com.diet.babysdiet.ui.theme.ExcellentEvaluationColor
import com.diet.babysdiet.ui.theme.FineEvaluationColor
import com.diet.babysdiet.ui.theme.NeutralEvaluationColor
import com.diet.babysdiet.ui.theme.VeryBadEvaluationColor

enum class Evaluation (val color: Color) {
    EXCELLENT(ExcellentEvaluationColor),
    FINE(FineEvaluationColor),
    NEUTRAL(NeutralEvaluationColor),
    BAD(BadEvaluationColor),
    VERY_BAD(VeryBadEvaluationColor)
}