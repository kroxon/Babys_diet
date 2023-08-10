package com.example.babysdiet.components.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.babysdiet.util.Constants.DATABASE_TABLE_DIARY
import java.sql.Time
import java.sql.Timestamp
import java.util.Date

@Entity(tableName = DATABASE_TABLE_DIARY)
class Diary(
    @PrimaryKey(autoGenerate = true)
    val idDIary: Int = 0,
    val timeEating: Long,
    val food: Food,
    val amountFood: Float,
    val reaction: String,
    val reactionTime: Date,
    val evaluation: Evaluation
)