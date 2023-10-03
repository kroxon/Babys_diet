package com.example.babysdiet.ui.viewmodels

import android.app.Application
import android.util.Log
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.babysdiet.R
import com.example.babysdiet.components.data.models.Diary
import com.example.babysdiet.components.data.models.Evaluation
import com.example.babysdiet.components.data.models.Product
import com.example.babysdiet.components.data.repositories.DiaryRepository
import com.example.babysdiet.components.data.repositories.ProductRepository
import com.example.babysdiet.util.Action
import com.example.babysdiet.util.RequestState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.time.LocalDate
import javax.inject.Inject


@HiltViewModel
class SharedViewModel @Inject constructor(
    private val diaryRepository: DiaryRepository,
    private val productRepository: ProductRepository,
    private val application: Application
) : AndroidViewModel(application) {

    val action: MutableState<Action> = mutableStateOf(Action.NO_ACTION)

    // getting all products using RequestState
    private val _allProducts = MutableStateFlow<RequestState<List<Product>>>(RequestState.Idle)
    val allProducts: StateFlow<RequestState<List<Product>>> = _allProducts

    // getting selected by category products using RequestState
    private val _selectedProducts = MutableStateFlow<RequestState<List<Product>>>(RequestState.Idle)
    val selectedProducts: StateFlow<RequestState<List<Product>>> = _selectedProducts

    // getting allegrens by using RequestState
    private val _allegrenProducts = MutableStateFlow<RequestState<List<Product>>>(RequestState.Idle)
    val allegrenProducts: StateFlow<RequestState<List<Product>>> = _allegrenProducts

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

    // diary
    val selectedDiaryId: MutableState<Int> = mutableStateOf(0)
    val evaluationDiary: MutableState<Evaluation> = mutableStateOf(Evaluation.EXCELLENT)
    val foodActivities: MutableState<List<Boolean>> = mutableStateOf(List(6) { false })
    val diarySympotomsOccured: MutableState<Boolean> = mutableStateOf(false)
    val diaryDescription: MutableState<String> = mutableStateOf("")
    val selectedDiaryProduct: MutableState<Product?> = mutableStateOf(null)
    val selectedDiaryProductId: MutableState<Int> = mutableStateOf(0)
    val selectedDate: MutableState<Long> = mutableStateOf(LocalDate.now().toEpochDay())
    val saveProductAsAllergen: MutableState<Boolean> = mutableStateOf(false)


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
            val selectedProductsIds =
                categorySelection.value.mapIndexedNotNull { index, isSelected ->
                    if (index == 0 || !isSelected) null
                    else index
                }
            viewModelScope.launch {
                productRepository.getProductsInCategories(selectedProductsIds).collect {
                    _selectedProducts.value = RequestState.Success(it)
                    isAllProductsInitialized = true
                }
            }
        } catch (e: Exception) {
            _selectedProducts.value = RequestState.Error(e)
        }
    }

    fun getAllergenProducts() {
        _allegrenProducts.value = RequestState.Loading
        try {
            viewModelScope.launch {
                productRepository.getAllergenProducts.collect {
                    _allegrenProducts.value = RequestState.Success(it)
                }
            }
        } catch (e: Exception) {
            _allegrenProducts.value = RequestState.Error(e)
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

    private fun addDiary() {
        viewModelScope.launch(Dispatchers.IO) {
            val diary = Diary(
                timeEating = selectedDate.value,
                productId = selectedDiaryProduct.value!!.productId,
                reactionOccurred = diarySympotomsOccured.value,
                description = diaryDescription.value,
                evaluation = evaluationDiary.value,
                touched = foodActivities.value[0],
                sniffed = foodActivities.value[1],
                licked = foodActivities.value[2],
                attemptFirst = foodActivities.value[3],
                attemptSecond = foodActivities.value[4],
                attemptThird = foodActivities.value[5]
            )
            diaryRepository.addDiaryEntry(diary)
            if (saveProductAsAllergen.value) {
                val product = Product(
                    productId = selectedDiaryProduct.value!!.productId,
                    name = selectedDiaryProduct.value!!.name,
                    categoryId = selectedDiaryProduct.value!!.categoryId,
                    description = selectedDiaryProduct.value!!.description,
                    isAllergen = true
                )
                productRepository.updateProduct(product = product)
                saveProductAsAllergen.value = false
            }
        }
    }

    private fun addProduct() {
        viewModelScope.launch(Dispatchers.IO) {
            val product = Product(
                name = nameProduct.value,
                categoryId = idCategoryProduct.value,
                description = descriptionProduct.value,
                isAllergen = isAllergenProduct.value
            )
            productRepository.addProduct(product)
        }
    }

    private fun updateDiary() {
        viewModelScope.launch(Dispatchers.IO) {
            val diary = Diary(
                diaryId = selectedDiaryId.value,
                timeEating = selectedDate.value,
                productId = selectedDiaryProduct.value!!.productId,
                reactionOccurred = diarySympotomsOccured.value,
                description = diaryDescription.value,
                evaluation = evaluationDiary.value,
                touched = foodActivities.value[0],
                sniffed = foodActivities.value[1],
                licked = foodActivities.value[2],
                attemptFirst = foodActivities.value[3],
                attemptSecond = foodActivities.value[4],
                attemptThird = foodActivities.value[5]
            )
            diaryRepository.updateDiaryEntry(diary)
            if (saveProductAsAllergen.value) {
                val product = Product(
                    productId = selectedDiaryProduct.value!!.productId,
                    name = selectedDiaryProduct.value!!.name,
                    categoryId = selectedDiaryProduct.value!!.categoryId,
                    description = selectedDiaryProduct.value!!.description,
                    isAllergen = true
                )
                productRepository.updateProduct(product = product)
                saveProductAsAllergen.value = false
            }
        }
    }

    private fun updateProduct() {
        viewModelScope.launch(Dispatchers.IO) {
            val product = Product(
                productId = idProduct.value,
                name = nameProduct.value,
                categoryId = idCategoryProduct.value,
                description = descriptionProduct.value,
                isAllergen = isAllergenProduct.value
            )
            productRepository.updateProduct(product)
        }
    }

    private fun deleteDiary() {
        viewModelScope.launch(Dispatchers.IO) {
            val diary = Diary(
                diaryId = selectedDiaryId.value,
                timeEating = selectedDate.value,
                productId = selectedDiaryProductId.value,
                reactionOccurred = diarySympotomsOccured.value,
                description = diaryDescription.value,
                evaluation = evaluationDiary.value,
                touched = foodActivities.value[0],
                sniffed = foodActivities.value[1],
                licked = foodActivities.value[2],
                attemptFirst = foodActivities.value[3],
                attemptSecond = foodActivities.value[4],
                attemptThird = foodActivities.value[5]
            )
            diaryRepository.deleteDiaryEntry(diary)
        }
    }

    private fun deleteProduct() {
        viewModelScope.launch(Dispatchers.IO) {
            val product = Product(
                productId = idProduct.value,
                name = nameProduct.value,
                categoryId = idCategoryProduct.value,
                description = descriptionProduct.value,
                isAllergen = isAllergenProduct.value
            )
            productRepository.deleteProduct(product)
        }
    }

    fun handleDatabaseActions(action: Action) {
        when (action) {
            Action.ADD_DIARY -> {
                addDiary()
            }

            Action.DELETE_DIARY -> {
                deleteDiary()
            }

            Action.DELETE_ALL_DIARIES -> {
//                deleteAllTasks()
            }

            Action.UNDO_DIARY -> {
                addDiary()
            }

            Action.UPDATE_DIARY -> {
                updateDiary()
            }

            Action.ADD_PRODUCT -> {
                Log.d("add_product: ", "added")
                addProduct()
            }

            Action.DELETE_PRODUCT -> {
                Log.d("delete_product: ", "deleted")
                deleteProduct()
            }

            Action.DELETE_ALL_PRODUCTS -> {
//                deleteAllTasks()
            }

            Action.UNDO_PRODUCT -> {
//                addTask()
            }

            Action.UPDATE_PRODUCT -> {
                Log.d("updated_product: ", "updated")
                updateProduct()
            }

            else -> {

            }
        }
        this.action.value = Action.NO_ACTION
    }

    fun updateDiaryFields(selectedDiary: Diary?, selectedProduct: Product?) {
        if (selectedDiary != null) {
            selectedDiaryId.value = selectedDiary.diaryId
            selectedDiaryProductId.value = selectedProduct!!.productId
            evaluationDiary.value = selectedDiary.evaluation
            foodActivities.value = listOf(
                selectedDiary.touched,
                selectedDiary.sniffed,
                selectedDiary.licked,
                selectedDiary.attemptFirst,
                selectedDiary.attemptSecond,
                selectedDiary.attemptThird
            )
            diarySympotomsOccured.value = selectedDiary.reactionOccurred
            diaryDescription.value = selectedDiary.description
            selectedDiaryProduct.value = selectedProduct
            selectedDate.value = selectedDiary.timeEating
            saveProductAsAllergen.value = selectedProduct.isAllergen
        } else {
            selectedDiaryId.value = 0
            selectedDiaryProductId.value = 0
            evaluationDiary.value = Evaluation.EXCELLENT
            foodActivities.value = listOf(false, false, false, false, false, false)
            diarySympotomsOccured.value = false
            diaryDescription.value = ""
            selectedDiaryProduct.value = null
            selectedDate.value = LocalDate.now().toEpochDay()
            saveProductAsAllergen.value = false
        }
    }

    fun validateDiaryFields(): Boolean {
        return selectedDiaryProduct.value != null
    }

    fun updateProductFields(selectedProduct: Product?, categoryId: Int) {
        if (selectedProduct != null) {
            idProduct.value = selectedProduct.productId
            idCategoryProduct.value = selectedProduct.categoryId
            nameProduct.value = selectedProduct.name
            descriptionProduct.value = selectedProduct.description
            isAllergenProduct.value = selectedProduct.isAllergen
        } else {
            idProduct.value = 0
            idCategoryProduct.value = categoryId
            nameProduct.value = ""
            descriptionProduct.value = ""
            isAllergenProduct.value = false
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