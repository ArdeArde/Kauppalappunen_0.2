package com.example.kauppalappunen_02.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.kauppalappunen_02.db.Recipe
import com.example.kauppalappunen_02.db.RecipeDao

class RecipeViewModel (private val recipeDao: RecipeDao) : ViewModel() {
    val allRecipes: LiveData<List<Recipe>> = recipeDao.getAllRecipesLive()
}