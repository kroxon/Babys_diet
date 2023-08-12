package com.example.babysdiet.components.data.repositories

import com.example.babysdiet.components.data.daos.FoodDao
import com.example.babysdiet.components.data.models.Food
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@ViewModelScoped
class FoodRepository @Inject constructor(private val foodDao: FoodDao) {
    val getAllFoods: Flow<List<Food>> = foodDao.getAllFood()
    val getAllergenFoods: Flow<List<Food>> = foodDao.getAllergens(true)

    suspend fun addFood(food: Food) {
        foodDao.addFood(food = food)
    }

    suspend fun deleteFood(food: Food) {
        foodDao.deleteFood(food = food)
    }

    suspend fun updateFood(food: Food) {
        foodDao.updateFood(food = food)
    }

    fun getFoodCategory(searchQuery: String): Flow<List<Food>> {
        return foodDao.getFoodCategory(category = searchQuery)
    }

    fun searchByFoodName(searchQuery: String): Flow<List<Food>>{
        return foodDao.searchFoodByName(searchQuery = searchQuery)
    }

}