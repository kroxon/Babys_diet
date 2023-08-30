package com.example.babysdiet.ui.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.babysdiet.components.data.models.Diary
import com.example.babysdiet.components.data.models.Product
import com.example.babysdiet.components.data.repositories.DiaryRepository
import com.example.babysdiet.components.data.repositories.ProductRepository
import com.example.babysdiet.util.RequestState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SharedViewModel @Inject constructor(
    private val diaryRepository: DiaryRepository,
    private val productRepository: ProductRepository
) : ViewModel() {

    // getting all products using RequestState
    private val _allProducts = MutableStateFlow<RequestState<List<Product>>>(RequestState.Idle)
    val allProducts: StateFlow<RequestState<List<Product>>> = _allProducts

    // getting all diaries using RequestState
    private val _allDiaries = MutableStateFlow<RequestState<List<Diary>>>(RequestState.Idle)
    val allDiaries: StateFlow<RequestState<List<Diary>>> = _allDiaries

    //

    fun getAllProducts() {
        _allProducts.value = RequestState.Loading
        try {
            viewModelScope.launch {
                productRepository.getAllProducts.collect {
                    _allProducts.value = RequestState.Success(it)
                }
            }
        } catch (e: Exception) {
            _allProducts.value = RequestState.Error(e)
        }
    }

    fun getAllDiaries() {
        _allDiaries.value = RequestState.Loading
        try {
            viewModelScope.launch {
                diaryRepository.getAllDiaryEntries.collect {
                    _allDiaries.value = RequestState.Success(it)
                }
            }
        } catch (e: Exception) {
            _allDiaries.value = RequestState.Error(e)
        }
    }

    private val _selectedDiary: MutableStateFlow<Diary?> = MutableStateFlow(null)
    val selectedDiary: StateFlow<Diary?> = _selectedDiary

    fun getSelectedDiary(diaryId: Int) {
        viewModelScope.launch {
            diaryRepository.getSelectedDiary(diaryId).collect { diary ->
                _selectedDiary.value = diary
            }
        }
    }

    private val _selectedProduct: MutableStateFlow<Product?> = MutableStateFlow(null)
    val selectedProduct: StateFlow<Product?> = _selectedProduct

    fun getSelectedProduct(productId: Int) {
        viewModelScope.launch {
            productRepository.getSelectedProduct(productId).collect { product ->
                _selectedProduct.value = product
            }
        }
    }

}