package com.example.babysdiet.di

import android.content.Context
import androidx.room.Room
import com.example.babysdiet.components.DietDatabase
import com.example.babysdiet.util.Constants.DATABASE_NAME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModul {
    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context) = Room.databaseBuilder(
        context,
        DietDatabase::class.java,
        DATABASE_NAME
    ).build()

    @Singleton
    @Provides
    fun provideProductDao(database: DietDatabase) = database.productDao()

    @Singleton
    @Provides
    fun provideDiaryDao(database: DietDatabase) = database.diaryDao()
}