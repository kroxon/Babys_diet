package com.example.babysdiet.components.data.daos
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.babysdiet.components.data.models.Food
import kotlinx.coroutines.flow.Flow

@Dao
interface FoodDao {

    @Query("SELECT * FROM food_table")
    fun getAllFood(): Flow<List<Food>>

    @Query("SELECT * FROM food_table WHERE category=:category")
    fun getFoodCategory(category: String): Flow<List<Food>>

    @Query("SELECT * FROM food_table WHERE isAllergen = :isAllergenValue")
    fun getAllergens(isAllergenValue: Boolean = true): Flow<List<Food>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addFood(food: Food)

    @Update
    suspend fun updateFood(food: Food)

    @Delete
    suspend fun deleteFood(food: Food)

    @Query("SELECT * FROM food_table WHERE name LIKE '%' || :searchQuery || '%'")
    fun searchFoodByName(searchQuery: String): Flow<List<Food>>
}