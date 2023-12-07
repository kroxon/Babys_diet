package com.diet.babysdiet.components.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.diet.babysdiet.components.data.daos.DiaryDao
import com.diet.babysdiet.components.data.daos.ProductDao
import com.diet.babysdiet.components.data.models.Diary
import com.diet.babysdiet.components.data.models.Product

@Database(entities = [Product::class, Diary::class], version = 1, exportSchema = false)
abstract class DietDatabase : RoomDatabase() {
    abstract fun productDao(): ProductDao
    abstract fun diaryDao(): DiaryDao
}

