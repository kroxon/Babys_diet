package com.diet.babysdiet.components.data.repositories

import com.diet.babysdiet.components.data.daos.DiaryDao
import com.diet.babysdiet.components.data.models.Diary
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@ViewModelScoped
class DiaryRepository @Inject constructor(private val diaryDao: DiaryDao) {
    val getAllDiaryEntries: Flow<List<Diary>> = diaryDao.getAllDiaryEntries()

    fun getSelectedDiary(idDiary: Int): Flow<Diary> {
        return diaryDao.getSelectedDiary(idDiary = idDiary)
    }
    fun getDiariesByProduct(productIds: List<Int>): Flow<List<Diary>> {
        return diaryDao.getDiariesByProducts(productIds = productIds)
    }

    suspend fun addDiaryEntry(diary: Diary) {
        diaryDao.addDiaryEntry(diary = diary)
    }

    suspend fun deleteDiaryEntry(diary: Diary) {
        diaryDao.deleteDiaryEntry(diary = diary)
    }

    suspend fun updateDiaryEntry(diary: Diary) {
        diaryDao.updateDiaryEntry(diary = diary)
    }

    suspend fun deleteAllDiaryEntries() {
        diaryDao.deleteAllDiaryEntries()
    }
}