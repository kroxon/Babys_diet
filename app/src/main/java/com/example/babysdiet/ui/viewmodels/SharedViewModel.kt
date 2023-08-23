package com.example.babysdiet.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.babysdiet.components.data.models.Food
import com.example.babysdiet.components.data.repositories.FoodRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SharedViewModel @Inject constructor(
//    private val diaryRepository: DiaryRepository,
    private val foodRepository: FoodRepository
): ViewModel() {

    private val _allProducts = MutableStateFlow<List<Food>>(emptyList())
    val allProducts: StateFlow<List<Food>> = _allProducts

    fun getAllProducts(){
        viewModelScope.launch {
            foodRepository.getAllProducts.collect{
                _allProducts.value = it
            }
        }
    }

}