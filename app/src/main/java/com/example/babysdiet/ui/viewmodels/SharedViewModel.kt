package com.example.babysdiet.ui.viewmodels

import android.app.Application
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.babysdiet.R
import com.example.babysdiet.components.data.models.Diary
import com.example.babysdiet.components.data.models.Product
import com.example.babysdiet.components.data.repositories.DiaryRepository
import com.example.babysdiet.components.data.repositories.ProductRepository
import com.example.babysdiet.ui.screens.home.DisplayDiaries
import com.example.babysdiet.ui.screens.home.EmptyContent
import com.example.babysdiet.util.RequestState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class SharedViewModel @Inject constructor(
    private val diaryRepository: DiaryRepository,
    private val productRepository: ProductRepository,
    private val application: Application
) : AndroidViewModel(application) {


    // getting all products using RequestState
    private val _allProducts = MutableStateFlow<RequestState<List<Product>>>(RequestState.Idle)
    val allProducts: StateFlow<RequestState<List<Product>>> = _allProducts

    // getting all diaries using RequestState
    private val _allDiaries = MutableStateFlow<RequestState<List<Diary>>>(RequestState.Idle)
    val allDiaries: StateFlow<RequestState<List<Diary>>> = _allDiaries

    // product list
    val vegetables = application.resources.getStringArray(R.array.vegetables)
    private var isProductListEmpty = true

    // initial products
    private var isAllProductsInitialized = false
    init {
            getAllProducts()
    }

    fun getAllProducts() {
        _allProducts.value = RequestState.Loading
        try {
            viewModelScope.launch {
                productRepository.getAllProducts.collect {
                    _allProducts.value = RequestState.Success(it)
                    if (!it.isEmpty() && isAllProductsInitialized == false) {
                        isProductListEmpty = false
                        initProducts()
                        isAllProductsInitialized = true
                    }
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

    fun addProduct(product: Product) {
        viewModelScope.launch {
            productRepository.addProduct(product)
        }
    }

    fun initProducts() {
        Log.d("empty", isProductListEmpty.toString())
    }


}