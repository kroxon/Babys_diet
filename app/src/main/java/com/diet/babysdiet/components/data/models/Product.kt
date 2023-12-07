package com.diet.babysdiet.components.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.diet.babysdiet.util.Constants.DATABASE_TABLE_PRODUCT

@Entity(tableName = DATABASE_TABLE_PRODUCT)
data class Product(
    @PrimaryKey(autoGenerate = true)
    val productId: Int = 0,
    val name: String,
    val categoryId: Int,
    val description: String,
    val isAllergen: Boolean = false
)
