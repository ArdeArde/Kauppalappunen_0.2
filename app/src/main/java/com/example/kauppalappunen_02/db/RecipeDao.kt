package com.example.kauppalappunen_02.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface RecipeDao {

    @Insert
    suspend fun insertRecipe(recipe: Recipe)

    @Update
    suspend fun updateRecipe(recipe: Recipe)

    @Delete
    suspend fun deleteRecipe(recipe: Recipe)

    @Query("SELECT * FROM recipe_data_table")
    suspend fun getAllRecipes(): List<Recipe>

    @Query("SELECT * FROM recipe_data_table WHERE recipe_name = :name")
    suspend fun getRecipesByName(name:String): Recipe

    @Query("SELECT * FROM recipe_data_table")
    fun getAllRecipesLive(): LiveData<List<Recipe>>
}