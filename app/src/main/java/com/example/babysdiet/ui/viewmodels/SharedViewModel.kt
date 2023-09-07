package com.example.babysdiet.ui.viewmodels

import android.app.Application
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.babysdiet.R
import com.example.babysdiet.components.data.models.Diary
import com.example.babysdiet.components.data.models.Evaluation
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

    // getting selected by category products using RequestState
    private val _selectedProducts = MutableStateFlow<RequestState<List<Product>>>(RequestState.Idle)
    val selectedProducts: StateFlow<RequestState<List<Product>>> = _selectedProducts

    // getting all diaries using RequestState
    private val _allDiaries = MutableStateFlow<RequestState<List<Diary>>>(RequestState.Idle)
    val allDiaries: StateFlow<RequestState<List<Diary>>> = _allDiaries

    // initial products
    private var isAllProductsInitialized = false

    // product
    val idProduct: MutableState<Int> = mutableStateOf(0)
    val idCategoryProduct: MutableState<Int> = mutableStateOf(1)
    val nameProduct: MutableState<String> = mutableStateOf("")
    val descriptionProduct: MutableState<String> = mutableStateOf("")
    val isAllergenProduct: MutableState<Boolean> = mutableStateOf(value = false)

    val selectedProduct: MutableState<Product?> = mutableStateOf(null)

    // diary
    val evaluationDiary: MutableState<Evaluation> = mutableStateOf(Evaluation.EXCELLENT)
    val foodActivities: MutableState<List<Boolean>> = mutableStateOf(List(6) { false })


    // selected categories list
    val categorySelection = MutableStateFlow<List<Boolean>>(List(12) { true })

    var categoriesProducts: List<String> =
        application.resources.getStringArray(R.array.categories_array).toList()

    init {
        getAllProducts()
    }

    fun getAllProducts() {
        _allProducts.value = RequestState.Loading
        try {
            viewModelScope.launch {
                productRepository.getAllProducts.collect {
                    _allProducts.value = RequestState.Success(it)
                    if (!it.isEmpty() && isAllProductsInitialized == false)
                        isAllProductsInitialized = true
                    if (it.isEmpty() && isAllProductsInitialized == false) {
                        initProducts()
                        isAllProductsInitialized = true
                    }
                }
            }
        } catch (e: Exception) {
            _allProducts.value = RequestState.Error(e)
        }
    }

    fun getSelectedProducts() {
        try {
            _selectedProducts.value = RequestState.Loading
            val selectedCategoryIds =
                categorySelection.value.mapIndexedNotNull { index, isSelected ->
                    if (index == 0 || !isSelected) null
                    else index
                }
            viewModelScope.launch {
                productRepository.getProductsInCategories(selectedCategoryIds).collect {
                    _selectedProducts.value = RequestState.Success(it)
                    isAllProductsInitialized = true
                }
            }
        } catch (e: Exception) {
            _selectedProducts.value = RequestState.Error(e)
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

//    private val _selectedProduct: MutableStateFlow<Product?> = MutableStateFlow(null)
//    val selectedProduct: StateFlow<Product?> = _selectedProduct
//
//    fun getSelectedProduct(productId: Int) {
//        viewModelScope.launch {
//            productRepository.getSelectedProduct(productId).collect { product ->
//                _selectedProduct.value = product
//            }
//        }
//    }

    fun addProduct(product: Product) {
        viewModelScope.launch {
            productRepository.addProduct(product)
        }
    }

    fun initProducts() {
        // product list
        val vegetables = application.resources.getStringArray(R.array.vegetables)
        val fruits = application.resources.getStringArray(R.array.fruits)
        val dried_fruits =
            application.resources.getStringArray(R.array.dried_fruits)
        val dairy_and_eggs =
            application.resources.getStringArray(R.array.dairy_and_eggs)
        val spieces = application.resources.getStringArray(R.array.spices)
        val legumes = application.resources.getStringArray(R.array.legumes)
        val meats = application.resources.getStringArray(R.array.meat)
        val fishes = application.resources.getStringArray(R.array.fish_and_seafood)
        val grains = application.resources.getStringArray(R.array.cereal_products)
        val mushrooms = application.resources.getStringArray(R.array.mushrooms)
        val others = application.resources.getStringArray(R.array.other)
        for (i in vegetables)
            addProduct(Product(name = i, categoryId = 1, isAllergen = false, description = ""))
        for (i in fruits)
            addProduct(Product(name = i, categoryId = 2, isAllergen = false, description = ""))
        for (i in dried_fruits)
            addProduct(Product(name = i, categoryId = 3, isAllergen = false, description = ""))
        for (i in dairy_and_eggs)
            addProduct(Product(name = i, categoryId = 4, isAllergen = false, description = ""))
        for (i in spieces)
            addProduct(Product(name = i, categoryId = 5, isAllergen = false, description = ""))
        for (i in legumes)
            addProduct(Product(name = i, categoryId = 6, isAllergen = false, description = ""))
        for (i in meats)
            addProduct(Product(name = i, categoryId = 7, isAllergen = false, description = ""))
        for (i in fishes)
            addProduct(Product(name = i, categoryId = 8, isAllergen = false, description = ""))
        for (i in grains)
            addProduct(Product(name = i, categoryId = 9, isAllergen = false, description = ""))
        for (i in mushrooms)
            addProduct(Product(name = i, categoryId = 10, isAllergen = false, description = ""))
        for (i in others)
            addProduct(Product(name = i, categoryId = 11, isAllergen = false, description = ""))
    }


}