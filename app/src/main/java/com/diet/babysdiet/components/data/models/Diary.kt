package com.diet.babysdiet.components.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.diet.babysdiet.util.Constants.DATABASE_TABLE_DIARY

@Entity(tableName = DATABASE_TABLE_DIARY)
data class Diary(
    @PrimaryKey(autoGenerate = true)
    val diaryId: Int = 0,
    val timeEating: Long,
    val productId: Int,
    val reactionOccurred: Boolean,
    val description: String,
    val evaluation: Evaluation,
    val touched: Boolean,
    val sniffed: Boolean,
    val licked: Boolean,
    val attemptFirst: Boolean,
    val attemptSecond: Boolean,
    val attemptThird: Boolean
)