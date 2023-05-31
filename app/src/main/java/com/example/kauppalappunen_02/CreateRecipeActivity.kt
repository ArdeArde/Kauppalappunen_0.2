package com.example.kauppalappunen_02

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.ComponentActivity
import com.example.kauppalappunen_02.db.Recipe
import com.example.kauppalappunen_02.db.RecipeDao
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext

class CreateRecipeActivity : ComponentActivity(){

    private lateinit var activityScope: CoroutineScope

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_recipe)

        activityScope = CoroutineScope(Dispatchers.IO)
        val nameEditText = findViewById<EditText>(R.id.etRecipeName)
        val ingredientEditText = findViewById<EditText>(R.id.etRecipeIngredients)
        val exitButton = findViewById<Button>(R.id.btnExit)
        val confirmButton = findViewById<Button>(R.id.btnConfirmNewRecipe)

        val recipeDao = MyApp.database.recipeDao()

        exitButton.setOnClickListener{
            intent = Intent(this, RecipesActivity::class.java)
            startActivity(intent)
        }

        confirmButton.setOnClickListener {

            saveRecipeIntoDatabase(
                Recipe(
                    0,
                    nameEditText.text.toString(),
                    ingredientEditText.text.toString()
                ), recipeDao
            )

            CoroutineScope(Dispatchers.Main).launch {
                val allRecipes = readAllRecipesFromDatabase(recipeDao)
                Toast.makeText(applicationContext, allRecipes, Toast.LENGTH_LONG).show()
        }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        activityScope.cancel()
    }

    fun saveRecipeIntoDatabase(recipe: Recipe, recipeDao: RecipeDao){
        activityScope.launch{
            recipeDao.insertRecipe(recipe)
        }
    }

    suspend fun readAllRecipesFromDatabase (recipeDao : RecipeDao) : String {
        return withContext(Dispatchers.IO){
            recipeDao.getAllRecipes().toString()
        }
    }

}