package com.example.kauppalappunen_02.vm

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.kauppalappunen_02.db.RecipeDao

class RecipeViewModelFactory(private val dao: RecipeDao): ViewModelProvider.Factory {

    override fun <T: ViewModel> create(modelClass: Class<T>): T{
        if(modelClass.isAssignableFrom(RecipeViewModel::class.java)){
            return RecipeViewModel(dao) as T
        }
        throw IllegalArgumentException("Unknown View Model Class")
    }
}