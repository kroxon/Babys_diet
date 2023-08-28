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

    @Query("SELECT * FROM diary_table ORDER BY timeEating DESC")
    fun getAllDiaryEntries(): Flow<List<Diary>>

    @Query("SELECT * FROM diary_table WHERE productId=:productId  ORDER BY timeEating DESC")
    fun getDiaryByProduct(productId: Int): Flow<List<Diary>>

    @Query("SELECT * FROM diary_table WHERE diaryId=:idDiary")
    fun getSelectedDiary(idDiary: Int): Flow<Diary>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addDiaryEntry(diary: Diary)

    @Update
    suspend fun updateDiaryEntry(diary: Diary)

    @Delete
    suspend fun deleteDiaryEntry(diary: Diary)

    @Query("DELETE FROM diary_table")
    suspend fun deleteAllDiaryEntries()
}