package com.example.babysdiet.components.data.models

import androidx.compose.ui.graphics.Color
import com.example.babysdiet.ui.theme.BadEvaluationColor
import com.example.babysdiet.ui.theme.ExcellentEvaluationColor
import com.example.babysdiet.ui.theme.FineEvaluationColor
import com.example.babysdiet.ui.theme.NeutralEvaluationColor
import com.example.babysdiet.ui.theme.VeryBadEvaluationColor

enum class Evaluation (val color: Color) {
    EXCELLENT(ExcellentEvaluationColor),
    FINE(FineEvaluationColor),
    NEUTRAL(NeutralEvaluationColor),
    BAD(BadEvaluationColor),
    VERY_BAD(VeryBadEvaluationColor)
//    HIGH(HighPriorityColor),
//    LOW(LowPriorityColor),
//    MEDIUM(MediumPriorityColor),
//    NONE(NonePriorityColor)
}