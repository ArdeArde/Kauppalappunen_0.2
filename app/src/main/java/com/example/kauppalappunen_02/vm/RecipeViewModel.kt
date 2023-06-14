package com.example.kauppalappunen_02.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.kauppalappunen_02.db.Recipe
import com.example.kauppalappunen_02.db.RecipeDao
import kotlinx.coroutines.launch

class RecipeViewModel(private val dao: RecipeDao): ViewModel() {

    fun insertRecipe(recipe: Recipe)=viewModelScope.launch{
        dao.insertRecipe(recipe)
    }

    fun udpateRecipe(recipe: Recipe)=viewModelScope.launch{
        dao.updateRecipe(recipe)
    }

    fun deleteRecipe(recipe: Recipe)=viewModelScope.launch{
        dao.deleteRecipe(recipe)
    }

    suspend fun getRecipes(): List<Recipe>{
        return dao.getAllRecipes()
    }

}