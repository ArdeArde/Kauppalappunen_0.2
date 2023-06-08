package com.example.kauppalappunen_02

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.ComponentActivity
import kotlinx.coroutines.CoroutineScope

class CreateRecipeActivity : ComponentActivity(){

    private lateinit var activityScope: CoroutineScope

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_recipe)

        val exitButton = findViewById<Button>(R.id.btnExit)
        val addRecipeButton = findViewById<Button>(R.id.btnConfirmNewRecipe)
        val recipeNameEditText = findViewById<EditText>(R.id.etRecipeName)
        val recipeIngredientEditText = findViewById<EditText>(R.id.etRecipeIngredients)

        var newRecipe = Recipe("","")

        exitButton.setOnClickListener{
            val intent = Intent(this, RecipesActivity::class.java)
            startActivity(intent)
        }

        addRecipeButton.setOnClickListener{

            if (newRecipe.name == ""){
                val newName = recipeNameEditText.text.toString()
                newRecipe.name = newName
            }
            val newIngredient = recipeIngredientEditText.text.toString()
            if (newRecipe.ingredients.isEmpty()){
                newRecipe.ingredients = newIngredient
            }else{
            newRecipe.ingredients += "-$newIngredient"
            }

            Toast.makeText(this, newRecipe.ingredients, Toast.LENGTH_LONG).show()
        }



    }
}