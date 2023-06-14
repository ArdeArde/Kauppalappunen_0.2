package com.example.kauppalappunen_02

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.lifecycle.ViewModelProvider
import com.example.kauppalappunen_02.db.Recipe
import com.example.kauppalappunen_02.db.RecipeDatabase
import com.example.kauppalappunen_02.vm.RecipeViewModel
import com.example.kauppalappunen_02.vm.RecipeViewModelFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class CreateRecipeActivity : ComponentActivity(){

    private lateinit var activityScope: CoroutineScope

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_recipe)

        val exitButton = findViewById<Button>(R.id.btnExit)
        val addRecipeButton = findViewById<Button>(R.id.btnConfirmNewRecipe)
        val recipeNameEditText = findViewById<EditText>(R.id.etRecipeName)
        val recipeIngredientEditText = findViewById<EditText>(R.id.etRecipeIngredients)

        val dao = RecipeDatabase.getInstance(application).RecipeDao()
        val factory = RecipeViewModelFactory(dao)
        val viewModel = ViewModelProvider(this,factory).get(RecipeViewModel::class.java)
        val scope = CoroutineScope(Dispatchers.Main)

        var newRecipe = Recipe(0,"","")

        exitButton.setOnClickListener{

            if (newRecipe.name != ""){
                scope.launch {
                    dao.insertRecipe(newRecipe)
                }
            }
            val intent = Intent(this, RecipesActivity::class.java)
            startActivity(intent)
        }

        addRecipeButton.setOnClickListener{

            if (newRecipe.name == ""){
                val newName = recipeNameEditText.text.toString()
                recipeNameEditText.isEnabled = false
                newRecipe.name = newName
            }
            val newIngredient = recipeIngredientEditText.text.toString()
            if (newRecipe.ingredients.isEmpty()){
                newRecipe.ingredients = newIngredient
            }else{
            newRecipe.ingredients += "-$newIngredient"
            }
            Toast.makeText(this, "ingredient added", Toast.LENGTH_SHORT).show()
            recipeIngredientEditText.text.clear()
        }

    }
}