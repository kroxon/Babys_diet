package com.example.babysdiet.components.data.daos

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.babysdiet.components.data.models.Diary
import kotlinx.coroutines.flow.Flow

@Dao
interface DiaryDao {

    @Query("SELECT * FROM diary_table ORDER BY reactionTime DESC")
    fun getAllDiaryEntries(): Flow<List<Diary>>

    @Query("SELECT * FROM diary_table WHERE foodId=:foodId  ORDER BY reactionTime DESC")
    fun getDiaryByFood(foodId: Int): Flow<List<Diary>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addDiaryEntry(diary: Diary)

    @Update
    suspend fun updateDiaryEntry(diary: Diary)

    @Delete
    suspend fun deleteDiaryEntry(diary: Diary)

    @Query("DELETE FROM diary_table")
    suspend fun deleteAllDiaryEntries()
}