package com.example.babysdiet.components.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.babysdiet.util.Constants.DATABASE_TABLE_FOOD

@Entity(tableName = DATABASE_TABLE_FOOD)
class Food(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val category: String,
    val description: String
)