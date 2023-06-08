package com.example.kauppalappunen_02

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class RecipesActivity : ComponentActivity(){

    private lateinit var recipeMenu: RecyclerView
    private var recipeList = listOf<Recipe>(
        Recipe("Väärin", "Meni")
    )

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recipes)

        recipeMenu = findViewById<RecyclerView>(R.id.rvRecipeMenu)
        val exitButton = findViewById<Button>(R.id.btnExit)
        val newRecipeButton = findViewById<Button>(R.id.btnAddNewRecipe)

        recipeList = readRecipesFromResources("recipes")
        Log.i("mytag", recipeList.toString())

        initRecyclerView(recipeList)

        exitButton.setOnClickListener{
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        newRecipeButton.setOnClickListener{
            val intent = Intent(this, CreateRecipeActivity::class.java)
            startActivity(intent)
        }
    }

    private fun initRecyclerView(recipeList : List<Recipe>){
        recipeMenu.layoutManager = LinearLayoutManager(this)
        recipeMenu.adapter = RecipeRecyclerViewAdapter(recipeList){selectedItem: Recipe ->
            listItemClicked(selectedItem)
        }

    }
    
    private fun readRecipesFromResources(recipeFileName : String) : MutableList<Recipe>{
        val resourceId = getResources().getIdentifier(recipeFileName, "array", packageName)
        val tempRecipeList : MutableList<Recipe> = mutableListOf()
        val array = resources.getStringArray(resourceId)
        array.forEach{
            val recipeAsArray = it.split(',')
            Log.i("mytag",recipeAsArray.toString())
            val recipe = Recipe(recipeAsArray[0], recipeAsArray[1])
            tempRecipeList.add(recipe)
        }
        Log.i("mytag", tempRecipeList.toString())
        return tempRecipeList
    }

    private fun listItemClicked(recipe : Recipe){
        Toast.makeText(this, "Bruh", Toast.LENGTH_SHORT).show()
    }
        
    
}