package com.example.babysdiet.components.data.daos
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.babysdiet.components.data.models.Product
import kotlinx.coroutines.flow.Flow

@Dao
interface ProductDao {

    @Query("SELECT * FROM product_table")
    fun getAllProducts(): Flow<List<Product>>

    @Query("SELECT * FROM product_table WHERE categoryId=:category")
    fun getProductCategory(category: String): Flow<List<Product>>

    @Query("SELECT * FROM product_table WHERE isAllergen = :isAllergenValue")
    fun getAllergens(isAllergenValue: Boolean = true): Flow<List<Product>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addProduct(product: Product)

    @Update
    suspend fun updateProduct(product: Product)

    @Delete
    suspend fun deleteProduct(product: Product)

    @Query("SELECT * FROM product_table WHERE name LIKE '%' || :searchQuery || '%'")
    fun searchProductByName(searchQuery: String): Flow<List<Product>>
}