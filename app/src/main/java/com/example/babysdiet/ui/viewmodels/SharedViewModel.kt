package com.example.babysdiet.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.babysdiet.components.data.models.Diary
import com.example.babysdiet.components.data.models.Food
import com.example.babysdiet.components.data.repositories.DiaryRepository
import com.example.babysdiet.components.data.repositories.FoodRepository
import com.example.babysdiet.util.RequestState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SharedViewModel @Inject constructor(
//    private val diaryRepository: DiaryRepository,
    private val foodRepository: FoodRepository
): ViewModel() {

    private val _allFoods = MutableStateFlow<List<Food>>(emptyList())
    val allFoods: StateFlow<List<Food>> = _allFoods

    fun getAllFoods(){
        viewModelScope.launch {
            foodRepository.getAllFoods.collect{
                _allFoods.value = it
            }
        }
    }

}