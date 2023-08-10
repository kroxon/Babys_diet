package com.example.babysdiet.components

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.babysdiet.components.data.DiaryDao
import com.example.babysdiet.components.data.FoodDao
import com.example.babysdiet.components.data.models.Diary
import com.example.babysdiet.components.data.models.Food

@Database(entities = [Food::class, Diary::class], version = 1, exportSchema = false)
abstract class DietDatabase : RoomDatabase() {
    abstract fun foodDao(): FoodDao
    abstract fun diaryDao(): DiaryDao
}