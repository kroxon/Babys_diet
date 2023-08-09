package com.example.babysdiet.components.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.babysdiet.util.Constants.DATABASE_TABLE_DIARY

@Entity(tableName = DATABASE_TABLE_DIARY)
class Diary(
    @PrimaryKey(autoGenerate = true)
    val idDIary: Int = 0,
    val timeEating: Long,
    val food: Food,
    val amountFood: Float,
    val reaction: String,
    val timeReaction: Long,
    val mealEvaluations: Int
)